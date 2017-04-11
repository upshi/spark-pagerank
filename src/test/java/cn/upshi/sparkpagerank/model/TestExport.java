package cn.upshi.sparkpagerank.model;

import cn.upshi.sparkpagerank.GraphxPageRank;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.dao.PageRankResultDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-3-29 12:57.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class TestExport {

    @Autowired
    PageLinkDao dao;

    @Autowired
    PageRankResultDao pageRankResultDao;

    @Autowired
    GraphxPageRank graphxPageRank;

    @Test
    public void testHttp() throws IOException {
       dao.exportLinkFile("d:/links/1.txt", 2);
    }

    @Test
    public void testScala() throws IOException {
        List<PageRankResult> list = graphxPageRank.pageRank(1);
        for (PageRankResult p : list) {
            System.out.println(p);
        }
    }

    @Test
    public void testInsertBatch() throws IOException {
        ArrayList<PageRankResult> list = graphxPageRank.pageRank(2);
        pageRankResultDao.insertBatch(list);
    }


}

