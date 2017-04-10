package cn.upshi.sparkpagerank.crawl;

import okhttp3.*;

import java.io.IOException;

/**
 * spark-pagerank cn.upshi.sparkpagerank.crawl
 * 描述：
 * 时间：2017-4-9 14:59.
 */

public class OkHttp {

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://slide.sports.sina.com.cn/k/slide_2_786_124066.html/d/1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        ResponseBody body = response.body();
        MediaType mediaType = body.contentType();
        String name = mediaType.charset().name();
        System.out.println(name);
    }

}
