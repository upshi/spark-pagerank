package cn.upshi.sparkpagerank.model;

/**
 * spark-pagerank cn.upshi.sparkpagerank
 * 描述：
 * 时间：2017-3-29 10:36.
 */

public class CrawlUrl {

    private Integer id;

    private String url;

    private String title = "";

    private Integer taskId;

    public CrawlUrl() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "CrawlURL{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title=" + title +
                ", taskId=" + taskId +
                '}';
    }
}
