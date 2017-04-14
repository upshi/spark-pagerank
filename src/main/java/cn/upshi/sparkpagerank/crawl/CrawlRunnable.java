package cn.upshi.sparkpagerank.crawl;

import cn.upshi.sparkpagerank.GraphxPageRank;
import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.dao.PageRankResultDao;
import cn.upshi.sparkpagerank.dao.TaskDao;
import cn.upshi.sparkpagerank.model.CrawlUrl;
import cn.upshi.sparkpagerank.model.PageLink;
import cn.upshi.sparkpagerank.model.PageRankResult;
import cn.upshi.sparkpagerank.model.Task;
import cn.upshi.sparkpagerank.util.FileUtil;
import cn.upshi.sparkpagerank.util.StringUtil;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private PageRankResultDao pageRankResultDao;

    private GraphxPageRank graphxPageRank;

    private URLManager urlManager = new URLManager();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    // private IDownloader downloader = new HttpClientDownloader();
    private IDownloader downloader = new OkHttpDownloader();

    public CrawlRunnable(Task task, CrawlUrlDao crawlUrlDao, PageLinkDao pageLinkDao, TaskDao taskDao, PageRankResultDao pageRankResultDao, GraphxPageRank graphxPageRank) {
        this.task = task;
        this.crawlUrlDao = crawlUrlDao;
        this.pageLinkDao = pageLinkDao;
        this.taskDao = taskDao;
        this.pageRankResultDao = pageRankResultDao;
        this.graphxPageRank = graphxPageRank;

        //插入第一个链接
        CrawlUrl crawlUrl = new CrawlUrl();
        crawlUrl.setUrl(task.getStartUrl());
        crawlUrl.setTaskId(task.getId());
        crawlUrlDao.insert(crawlUrl);

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

        Integer totalUrl = task.getTotalUrl();

        Integer taskId = task.getId();

        //设置状态
        task.setStatus(Task.CRAWLING);
        task.setCrawlStartTime(sdf.format(new Date()));
        taskDao.updateByPrimaryKey(task);

        int total = 1;


        // 当已完成的链接的个数小于等于total并且url管理器中还有链接时,继续爬取
        while (total < totalUrl && urlManager.hasLink()) {
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
                e.printStackTrace();
            }

            // 获取当前网页上的所有链接
            links = document.select("a[href]");
            iterator = links.iterator();

            while (iterator.hasNext() && total < totalUrl) {
                link = iterator.next();
                //检查url格式，是否以http:// 或者 https://开头
                String href = checkFormat(link.attr("href"));
                if (href != null) {
                    // 查询该网页是否已经存在
                    tempUrl = crawlUrlDao.selectByUrlAndTaskId(href, taskId);
                    try {
                        //判断是否已经存在,如果不存在就插入
                        if (tempUrl == null) {
                            //插入这个不存在的链接
                            tempUrl = new CrawlUrl();
                            tempUrl.setUrl(href);
                            tempUrl.setTaskId(taskId);
                            crawlUrlDao.insert(tempUrl);
                            logger.info("新增URL:" + tempUrl);
                            urlList.add(tempUrl);
                            total++;

                            // 避免每次都插入数据库
                            if(total % 20 == 0) {
                                //设置爬取连接总数
                                task.setHasHandled(total);
                                taskDao.updateByPrimaryKey(task);
                            }
                        }

                        //插入PageLink关系
                        pageLink = new PageLink();
                        pageLink.setFromId(crawlUrl.getId());
                        pageLink.setToId(tempUrl.getId());
                        pageLink.setTaskId(taskId);
                        pageLinkDao.insert(pageLink);

                    } catch (Exception e) {
                        e.printStackTrace();
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

        //设置状态 已爬取完毕
        task.setStatus(Task.CRAWLEND);
        task.setCrawlEndTime(sdf.format(new Date()));
        //最终再设置爬取连接总数
        task.setHasHandled(total);
        taskDao.updateByPrimaryKey(task);

        //删除link文件
        FileUtil.removeOldFile(taskId);
        //获取文件名
        String fileName = FileUtil.linkFileName(taskId);
        //生成link文件
        pageLinkDao.exportLinkFile(fileName, taskId);

        //设置状态 已导出文件
        task.setStatus(Task.EXPORT);
        task.setExportTime(sdf.format(new Date()));
        taskDao.updateByPrimaryKey(task);

        //计算pagerank生成结果
        ArrayList<PageRankResult> list = graphxPageRank.pageRank(taskId);
        pageRankResultDao.insertBatch(list);

        // 查询title为空的crawlUrl，下载并且设置title
        for (PageRankResult prr : list) {
            CrawlUrl url = crawlUrlDao.selectByPrimaryKey(prr.getUrlId());
            if(StringUtil.isEmpty(url.getTitle())) {
                Document doc = downloader.download(url.getUrl());
                try {
                    if(doc == null) {
                        continue;
                    }
                    url.setTitle(doc.title());
                    crawlUrlDao.updateTitle(url);
                    logger.info("设置Title:" + url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //设置状态 已计算结果
        task.setStatus(Task.PAGERANK);
        task.setPageRankTime(sdf.format(new Date()));
        taskDao.updateByPrimaryKey(task);

    }

    private static String checkFormat(String url) {
        if (url != null) {
            if (url.startsWith("//")) {
                return "http:" + url;
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                return url;
            }
        }
        return null;
    }
}
