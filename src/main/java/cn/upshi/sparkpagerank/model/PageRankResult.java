package cn.upshi.sparkpagerank.model;

import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-4-10 15:21.
 */

public class PageRankResult {

    private Integer id;
    private Integer taskId;
    private Integer urlId;
    private List<CrawlUrl> crawlUrls;

    public PageRankResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public List<CrawlUrl> getCrawlUrls() {
        return crawlUrls;
    }

    public void setCrawlUrls(List<CrawlUrl> crawlUrls) {
        this.crawlUrls = crawlUrls;
    }

    @Override
    public String toString() {
        return "PageRankResult{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", urlId=" + urlId +
                ", crawlUrls=" + crawlUrls +
                '}';
    }
}
