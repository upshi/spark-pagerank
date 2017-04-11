package cn.upshi.sparkpagerank.model;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-4-10 15:21.
 */

public class PageRankResult {

    private Integer id;
    private Integer taskId;
    private Integer urlId;
    private String rank;

    public PageRankResult() {
    }

    public PageRankResult(Integer taskId, Integer urlId, String rank) {
        this.taskId = taskId;
        this.urlId = urlId;
        this.rank = rank;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "PageRankResult{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", urlId=" + urlId +
                ", rank=" + rank +
                '}';
    }
}
