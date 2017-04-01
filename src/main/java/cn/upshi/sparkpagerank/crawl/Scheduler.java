package cn.upshi.sparkpagerank.crawl;

import cn.upshi.sparkpagerank.dao.CrawlUrlDao;
import cn.upshi.sparkpagerank.dao.PageLinkDao;
import cn.upshi.sparkpagerank.model.CrawlUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-28 23:45.
 */

@Scope("prototype")
@Component()
public class Scheduler {

    private String startUrl;

    private ExecutorService pool;

    private URLManager urlManager;

    private Downloader downloader;

    @Autowired
    private CrawlUrlDao crawlUrlDao;

    @Autowired
    private PageLinkDao pageLinkDao;

    public Scheduler() {}

    public void init(int poolSize, String startUrl) {

        if(poolSize <= 0) {
            poolSize = 1;
        }
        this.pool = Executors.newFixedThreadPool(poolSize);

        this.startUrl = startUrl;
        urlManager = new URLManager();
        downloader = new Downloader();

        crawlUrlDao.truncate();
        pageLinkDao.truncate();
        CrawlUrl crawlUrl = new CrawlUrl();
        crawlUrl.setUrl(startUrl);
        crawlUrlDao.insert(crawlUrl);
        urlManager.addLink(crawlUrl);
    }

    public void run() {
        while (urlManager.hasLink() && urlManager.getDoneSize() <= 5000) {
            CrawlUrl crawlUrl = urlManager.nextLink();
            CrawlCallable callable = new CrawlCallable(downloader, crawlUrl, crawlUrlDao, pageLinkDao);
            Future<List<CrawlUrl>> future = pool.submit(callable);
            try {
                List<CrawlUrl> crawlUrls = future.get();
                urlManager.addLinks(crawlUrls);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(urlManager);
    }
}
