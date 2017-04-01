package cn.upshi.sparkpagerank.crawl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-28 23:30.
 */

public class Downloader {

    private HttpClient client = HttpClients.createDefault();

    public Document download(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        try {
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200) {
                HttpEntity entity = response.getEntity();
                Document document = Jsoup.parse(EntityUtils.toString(entity, "UTF-8"));
                return document;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
