package cn.upshi.sparkpagerank.crawl;

import cn.upshi.sparkpagerank.model.CrawlUrl;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-29 09:38.
 */

public class URLManager {

    private Queue<CrawlUrl> waitUrls;

    private Set<String> doneUrls;

    public URLManager() {
        this.waitUrls = new ConcurrentLinkedQueue<>();
        this.doneUrls = Collections.synchronizedSet(new HashSet<>());
    }

    public boolean hasLink() {
        return !waitUrls.isEmpty();
    }

    public int getDoneSize() {
        return doneUrls.size();
    }

    public CrawlUrl nextLink() {
        CrawlUrl poll = waitUrls.poll();
        return poll;
    }

    public boolean addLink(CrawlUrl link) {
        boolean result = false;
        if(!doneUrls.contains(link.getUrl())) {
            result = waitUrls.offer(link);
        }
        return result;
    }

    public int addLinks(List<CrawlUrl> links) {
        int i = 0;
        for(CrawlUrl c : links) {
            boolean b = addLink(c);
            if(b)
                i++;
        }
        return i;
    }

    public void addDoneUrl(String url) {
        doneUrls.add(url);
    }

}
