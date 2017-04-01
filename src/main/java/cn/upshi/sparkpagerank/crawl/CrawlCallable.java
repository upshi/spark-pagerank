package cn.upshi.sparkpagerank.crawl;

import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.model.CrawlUrl;
import cn.upshi.sparkpagerank.model.PageLink;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-29 10:57.
 */

public class CrawlCallable implements Callable<List<CrawlUrl>> {

    Logger logger = Logger.getLogger(CrawlCallable.class);

    private Downloader downloader;

    private CrawlUrl crawlURL;

    private CrawlUrlDao crawlUrlDao;

    private PageLinkDao pageLinkDao;

    public CrawlCallable(Downloader downloader, CrawlUrl crawlURL, CrawlUrlDao crawlUrlDao, PageLinkDao pageLinkDao) {
        this.downloader = downloader;
        this.crawlURL = crawlURL;
        this.crawlUrlDao = crawlUrlDao;
        this.pageLinkDao = pageLinkDao;
    }

    @Override
    public List<CrawlUrl> call() throws Exception {
        Document document = downloader.download(crawlURL.getUrl());

        List<CrawlUrl> list = new ArrayList<>();

        if(document != null) {
            crawlURL.setTitle(document.title());
            try{
                crawlUrlDao.updateTitle(crawlURL);
            } catch (Exception e) {

            }

            Elements links = document.select("a[href]");
            Iterator<Element> iterator = links.iterator();
            Element link = null;
            CrawlUrl tempUrl = null;
            PageLink pageLink = null;
            while (iterator.hasNext()) {
                link = iterator.next();
                //检查url格式，是否以http:// 或者 https://开头, 是否是sina.com.cn
                String href = checkFormat(link.attr("href"));
                if(href != null) {
                    tempUrl = crawlUrlDao.selectByUrl(href);
                    try {
                        //判断是否已经存在,如果不存在就插入
                        if(tempUrl == null) {
                            //插入这个不存在的链接
                            tempUrl = new CrawlUrl();
                            tempUrl.setUrl(href);
                            crawlUrlDao.insert(tempUrl);

                            logger.info("新增URL:" + tempUrl);

                            list.add(tempUrl);
                        }

                        //插入PageLink关系
                        pageLink = new PageLink();
                        pageLink.setFromId(crawlURL.getId());
                        pageLink.setToId(tempUrl.getId());
                        pageLinkDao.insert(pageLink);

                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }

        return list;
    }

    private static String checkFormat(String url) {
        if(url != null && url.indexOf("sports.sina.com.cn") > 0) {
            if(url.startsWith("//")) {
                return "http:" + url;
            } else if(url.startsWith("http://") || url.startsWith("https://")) {
                return url;
            }
        }
        return null;
    }
}
