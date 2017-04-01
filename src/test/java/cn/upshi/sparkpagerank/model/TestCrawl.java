package cn.upshi.sparkpagerank.model;

import cn.upshi.sparkpagerank.crawl.Downloader;
import cn.upshi.sparkpagerank.crawl.Scheduler;
import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import com.sun.xml.internal.xsom.impl.Ref;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

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
    public void testHttp() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://slide.sports.sina.com.cn/g_pl/slide_2_57057_123745.html");
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);
        System.out.println(contentType);

    }

    @Test
    public void testSpider() {
        scheduler.init(16, "http://sports.sina.com.cn/");
        scheduler.run();
    }


}
