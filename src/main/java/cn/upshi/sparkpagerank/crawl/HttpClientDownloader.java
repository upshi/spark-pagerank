package cn.upshi.sparkpagerank.crawl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
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

public class HttpClientDownloader implements IDownloader {

    private HttpClient client;
    private RequestConfig requestConfig;

    public HttpClientDownloader() {
        client = HttpClients.createDefault();
        requestConfig = RequestConfig.custom()
                                    .setSocketTimeout(3000)
                                    .setConnectTimeout(3000)
                                    .setConnectionRequestTimeout(3000)
                                    .build();
    }

    public Document download(String url) {
        try {
            HttpGet get = new HttpGet(url);
            get.setConfig(requestConfig);
            get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
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

    public Document download111(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        try {
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 将源码流保存在一个byte数组当中，因为可能需要两次用到该流，
                    byte[] bytes = EntityUtils.toByteArray(entity);

                    // 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
                    ContentType contentType = ContentType.getOrDefault(entity);

                    System.out.println("In header: " + contentType);
                    // 如果头部中没有，那么我们需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
                    // if (charSet == "") {
                    //     String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
                    //     Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
                    //     Matcher m = p.matcher(new String(bytes));  // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
                    //     boolean result = m.find();
                    //     if (m.groupCount() == 1) {
                    //         charSet = m.group(1);
                    //     } else {
                    //         charSet = "";
                    //     }
                    // }
                    // System.out.println("Last get: " + charSet);
                    // 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）
                    // System.out.println("Encoding string is: " + new String(bytes, charSet));
                }
                Document document = Jsoup.parse(EntityUtils.toString(entity, "UTF-8"));
                return document;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        HttpClientDownloader downloader = new HttpClientDownloader();
        Document document = downloader.download("http://sports.sina.com.cn/nba/allstar2012/");
        System.out.println(document);
    }

}
