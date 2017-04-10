package cn.upshi.sparkpagerank.crawl;

import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.dao.TaskDao;
import cn.upshi.sparkpagerank.model.CrawlUrl;
import cn.upshi.sparkpagerank.model.PageLink;
import cn.upshi.sparkpagerank.model.Task;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-29 10:57.
 */

public class CrawlRunnable implements Runnable {

    Logger logger = Logger.getLogger(CrawlRunnable.class);

    private Task task;

    private CrawlUrlDao crawlUrlDao;

    private PageLinkDao pageLinkDao;

    private TaskDao taskDao;

    private URLManager urlManager = new URLManager();

    // private IDownloader downloader = new HttpClientDownloader();
    private IDownloader downloader = new OkHttpDownloader();

    public CrawlRunnable(Task task, CrawlUrlDao crawlUrlDao, PageLinkDao pageLinkDao, TaskDao taskDao) {
        this.task = task;
        this.crawlUrlDao = crawlUrlDao;
        this.pageLinkDao = pageLinkDao;
        this.taskDao = taskDao;

        //插入第一个链接
        CrawlUrl crawlUrl = new CrawlUrl();
        crawlUrl.setUrl(task.getStartUrl());
        crawlUrlDao.insert(crawlUrl);
        crawlUrl.setTaskId(task.getId());
        //将第一个链接加入URL管理器
        urlManager.addLink(crawlUrl);
    }

    @Override
    public void run() {

        List<CrawlUrl> urlList = new ArrayList<>();
        // 当前爬取的URL
        CrawlUrl crawlUrl = null;
        // 当前爬取的URL的文档对象
        Document document = null;

        // 当前爬取的文档对象里的所有链接
        Elements links = null;
        // 当前爬取的所有链接的迭代器
        Iterator<Element> iterator = null;
        // 单个链接
        Element link = null;
        // 临时URL
        CrawlUrl tempUrl = null;
        // 页面指向关系
        PageLink pageLink = null;

        Integer maxHandled = task.getMaxHandled();

        // 当已完成的链接的个数小于等于total并且url管理器中还有链接时,继续爬取
        while (urlManager.getDoneSize() <= maxHandled && urlManager.hasLink()) {
            // 获取下一个链接
            crawlUrl = urlManager.nextLink();
            // 下载该网页
            document = downloader.download(crawlUrl.getUrl());
            //网页可能已经不存在
            if (document == null) {
                crawlUrlDao.deleteByPrimaryKey(crawlUrl.getId());
                continue;
            }

            // 设置该网页的标题
            crawlUrl.setTitle(document.title());
            try {
                crawlUrlDao.updateTitle(crawlUrl);
            } catch (Exception e) {

            }

            // 获取当前网页上的所有链接
            links = document.select("a[href]");
            iterator = links.iterator();

            while (iterator.hasNext()) {
                link = iterator.next();
                //检查url格式，是否以http:// 或者 https://开头, 是否是sina.com.cn
                String href = checkFormat(link.attr("href"));
                if (href != null) {
                    // 查询该网页是否已经存在
                    tempUrl = crawlUrlDao.selectByUrl(href);
                    try {
                        //判断是否已经存在,如果不存在就插入
                        if (tempUrl == null) {
                            //插入这个不存在的链接
                            tempUrl = new CrawlUrl();
                            tempUrl.setUrl(href);
                            crawlUrlDao.insert(tempUrl);
                            logger.info("新增URL:" + tempUrl);
                            urlList.add(tempUrl);
                        }

                        //插入PageLink关系
                        pageLink = new PageLink();
                        pageLink.setFromId(crawlUrl.getId());
                        pageLink.setToId(tempUrl.getId());
                        pageLinkDao.insert(pageLink);

                    } catch (Exception e) {
                        break;
                    }
                }
            }

            //向URL管理器中加入新的url
            urlManager.addLinks(urlList);
            //清空url集合
            urlList.clear();
            // 将该链接加入已完成链接的集合中
            urlManager.addDoneUrl(crawlUrl.getUrl());
        }

        // 查询所有title为空的crawlUrl，下载并且设置title
        List<CrawlUrl> emptyTitles = crawlUrlDao.selectAllEmptyTitle();
        for (CrawlUrl cu : emptyTitles) {
            Document doc = downloader.download(cu.getUrl());
            try {
                if(doc == null) {
                    crawlUrlDao.deleteByPrimaryKey(cu.getId());
                    continue;
                }
                cu.setTitle(doc.title());
                crawlUrlDao.updateTitle(cu);
                logger.info("设置Title:" + cu);
            } catch (Exception e) {

            }
        }
    }

    private static String checkFormat(String url) {
        if (url != null && url.indexOf("sports.sina.com.cn") > 0) {
            if (url.startsWith("//")) {
                return "http:" + url;
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                return url;
            }
        }
        return null;
    }
}
