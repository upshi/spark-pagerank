package cn.upshi.sparkpagerank.model;

import cn.upshi.sparkpagerank.crawl.Downloader;
import cn.upshi.sparkpagerank.crawl.Scheduler;
import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-3-29 12:57.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class TestCrawl {

    @Autowired
    CrawlUrlDao dao;

    @Autowired
    Scheduler scheduler;

    @Test
    public void testTruncate() {
        dao.truncate();
    }

    @Test
    public void testDownloader() {
        Downloader downloader = new Downloader();
        Document document = downloader.download("http://www.sina.com.cn");
        System.out.println("-----------------" + document.title());
    }

    @Test
    public void testInsert() {
        CrawlUrl crawlUrl = new CrawlUrl();
        crawlUrl.setUrl("sad");
        crawlUrl.setTitle("新浪首页");
        dao.insert(crawlUrl);
    }

    @Test
    public void testSpider() {
        scheduler.init(16, "http://sports.sina.com.cn");
        scheduler.run();
    }


}
