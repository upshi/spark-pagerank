package cn.upshi.sparkpagerank.crawl;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * spark-pagerank cn.upshi.sparkpagerank.crawl
 * 描述：
 * 时间：2017-4-9 14:59.
 */

public class OkHttp {

    public static void main(String[] args) throws IOException {

        OkHttpDownloader client = new OkHttpDownloader();
        Document document = client.download("http://www.sohu.com/");
        System.out.println(document);

    }

}
