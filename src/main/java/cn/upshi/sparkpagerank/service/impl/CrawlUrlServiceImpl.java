package cn.upshi.sparkpagerank.service.impl;

import cn.upshi.sparkpagerank.crawl.CrawlRunnable;
import cn.upshi.sparkpagerank.crawl.HttpClientDownloader;
import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.dao.TaskDao;
import cn.upshi.sparkpagerank.model.CrawlUrl;
import cn.upshi.sparkpagerank.model.Task;
import cn.upshi.sparkpagerank.service.api.ICrawlUrlService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service.impl
 * 描述：
 * 时间：2017-4-7 23:28.
 */

@Service
public class CrawlUrlServiceImpl implements ICrawlUrlService {

    @Autowired
    private CrawlUrlDao crawlUrlDao;

    @Autowired
    private PageLinkDao pageLinkDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public void crawl(Task task) {
        CrawlRunnable runnable = new CrawlRunnable(task, crawlUrlDao, pageLinkDao, taskDao);
        new Thread(runnable).start();
    }

    @Override
    public void title() {
        HttpClientDownloader downloader = new HttpClientDownloader();
        List<CrawlUrl> emptyTitles = crawlUrlDao.selectAllEmptyTitle();
        for (CrawlUrl cu : emptyTitles) {
            Document doc = downloader.download(cu.getUrl());
            if(doc == null) {
                crawlUrlDao.deleteByPrimaryKey(cu.getId());
                continue;
            }
            cu.setTitle(doc.title());
            try {
                crawlUrlDao.updateTitle(cu);
            } catch (Exception e) {

            }
        }
    }
}
