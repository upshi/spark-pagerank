package cn.upshi.sparkpagerank.model;

import cn.upshi.sparkpagerank.crawl.HttpClientDownloader;
import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.dao.PageRankResultDao;
import cn.upshi.sparkpagerank.dao.TaskDao;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
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
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class TestCrawl {

    @Autowired
    CrawlUrlDao crawlUrlDao;

    @Autowired
    private PageLinkDao pageLinkDao;

    @Autowired
    TaskDao taskDao;

    @Autowired
    PageRankResultDao pageRankResultDao;

    @Test
    public void testTruncate() {
        crawlUrlDao.truncate();
        pageLinkDao.truncate();
        taskDao.truncate();
        pageRankResultDao.truncate();
    }


}

