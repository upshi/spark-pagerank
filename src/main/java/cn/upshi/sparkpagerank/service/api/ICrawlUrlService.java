package cn.upshi.sparkpagerank.service.api;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service
 * 描述：
 * 时间：2017-4-7 23:28.
 */

public interface ICrawlUrlService {

    public void crawl(String startUrl, int total);

    public void title();

}
