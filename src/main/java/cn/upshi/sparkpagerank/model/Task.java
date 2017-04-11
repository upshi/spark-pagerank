package cn.upshi.sparkpagerank.model;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-4-10 15:03.
 */

public class Task {

    public static final int CREAT = 0;
    public static final int CRAWLING = 1;
    public static final int CRAWLEND = 2;
    public static final int EXPORT = 3;
    public static final int PAGERANK = 4;
    public static final int INTERRUPTED = 5;

    private Integer id;
    private String startUrl;
    private Integer hasHandled;
    private Integer totalUrl;
    private Integer status;

    private String createTime;
    private String crawlStartTime;
    private String crawlEndTime;
    private String exportTime;
    private String pageRankTime;

    public Task() {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCrawlStartTime() {
        return crawlStartTime;
    }

    public void setCrawlStartTime(String crawlStartTime) {
        this.crawlStartTime = crawlStartTime;
    }

    public String getCrawlEndTime() {
        return crawlEndTime;
    }

    public void setCrawlEndTime(String crawlEndTime) {
        this.crawlEndTime = crawlEndTime;
    }

    public String getExportTime() {
        return exportTime;
    }

    public void setExportTime(String exportTime) {
        this.exportTime = exportTime;
    }

    public String getPageRankTime() {
        return pageRankTime;
    }

    public void setPageRankTime(String pageRankTime) {
        this.pageRankTime = pageRankTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", startUrl='" + startUrl + '\'' +
                ", hasHandled=" + hasHandled +
                ", totalUrl=" + totalUrl +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", crawlStartTime='" + crawlStartTime + '\'' +
                ", crawlEndTime='" + crawlEndTime + '\'' +
                ", exportTime='" + exportTime + '\'' +
                ", pageRankTime='" + pageRankTime + '\'' +
                '}';
    }
}
