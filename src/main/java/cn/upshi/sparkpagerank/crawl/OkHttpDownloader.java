package cn.upshi.sparkpagerank.crawl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.TimeUnit;

/**
 * spark-pagerank cn.upshi.sparkpagerank.crawl
 * 描述：
 * 时间：2017-4-9 15:24.
 */

public class OkHttpDownloader implements IDownloader {

    private OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                                .readTimeout(2000, TimeUnit.MILLISECONDS)
                                .writeTimeout(2000, TimeUnit.MILLISECONDS)
                                .build();

    private Cpdetector cpdetector = new Cpdetector();

    @Override
    public Document download(String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String encode = cpdetector.getUrlEncode(url);
                String body = null;
                if(encode != null) {
                    byte[] bytes = responseBody.bytes();
                    body = new String(bytes, encode);
                } else {
                    body = responseBody.string();
                }
                Document document = Jsoup.parse(body);
                return document;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
