package cn.upshi.sparkpagerank.dto;

/**
 * spark-pagerank cn.upshi.sparkpagerank.dto
 * 描述：
 * 时间：2017-4-11 21:50.
 */

public class Result {

    private String url;
    private String title;
    private String rank;

    public Result() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Result{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
