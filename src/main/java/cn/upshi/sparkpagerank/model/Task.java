package cn.upshi.sparkpagerank.model;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-4-10 15:03.
 */

public class Task {

    public static final int CREAT = 0;
    public static final int CRAWL = 1;
    public static final int EXPORT = 2;
    public static final int PAGERANK = 3;
    public static final int FINISH = 4;

    private Integer id;
    private String startUrl;
    private Integer hasHandled;
    private Integer maxHandled;
    private Integer totalUrl;
    private Integer status;

    public Task() {
    }

    public Task(Integer id, String startUrl, Integer hasHandled, Integer maxHandled, Integer totalUrl, Integer status) {
        this.id = id;
        this.startUrl = startUrl;
        this.hasHandled = hasHandled;
        this.maxHandled = maxHandled;
        this.totalUrl = totalUrl;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public Integer getHasHandled() {
        return hasHandled;
    }

    public void setHasHandled(Integer hasHandled) {
        this.hasHandled = hasHandled;
    }

    public Integer getMaxHandled() {
        return maxHandled;
    }

    public void setMaxHandled(Integer maxHandled) {
        this.maxHandled = maxHandled;
    }

    public Integer getTotalUrl() {
        return totalUrl;
    }

    public void setTotalUrl(Integer totalUrl) {
        this.totalUrl = totalUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", startUrl='" + startUrl + '\'' +
                ", hasHandled=" + hasHandled +
                ", maxHandled=" + maxHandled +
                ", totalUrl=" + totalUrl +
                ", status=" + status +
                '}';
    }
}
