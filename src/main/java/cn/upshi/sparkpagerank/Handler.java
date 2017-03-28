package cn.upshi.sparkpagerank;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-28 23:47.
 */

public class Handler {

    public static void main(String[] args) {
        Document document = Downloader.download("https://www.jd.com");

        Elements links = document.select("a[href]");
        Iterator<Element> iterator = links.iterator();
        Element link = null;
        while (iterator.hasNext()) {
            link = iterator.next();
            System.out.println(link.attr("href"));
        }
    }

}
