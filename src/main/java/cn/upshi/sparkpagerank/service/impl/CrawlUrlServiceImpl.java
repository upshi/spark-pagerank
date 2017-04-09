package cn.upshi.sparkpagerank.service.impl;

import cn.upshi.sparkpagerank.crawl.CrawlRunnable;
import cn.upshi.sparkpagerank.crawl.Downloader;
import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.model.CrawlUrl;
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

    @Override
    public void crawl(String startUrl, int total) {
        CrawlRunnable runnable = new CrawlRunnable(startUrl, total, crawlUrlDao, pageLinkDao);
        new Thread(runnable).start();
    }

    @Override
    public void title() {
        Downloader downloader = new Downloader();
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
