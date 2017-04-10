package cn.upshi.sparkpagerank.crawl;

import org.jsoup.nodes.Document;

/**
 * spark-pagerank cn.upshi.sparkpagerank.crawl
 * 描述：
 * 时间：2017-4-9 15:21.
 */

public interface IDownloader {

    public Document download(String url);

}
