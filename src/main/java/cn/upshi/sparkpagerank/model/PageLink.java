package cn.upshi.sparkpagerank.model;

/**
 * spark-pagerank cn.upshi.sparkpagerank.model
 * 描述：
 * 时间：2017-3-29 12:45.
 */

public class PageLink {

    private Integer id;
    private Integer fromId;
    private Integer toId;

    public PageLink() {
    }

    public PageLink(Integer id, Integer fromId, Integer toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    @Override
    public String toString() {
        return "PageLink{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                '}';
    }
}
