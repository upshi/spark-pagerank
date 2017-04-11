package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.CrawlUrl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrawlUrlDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CrawlUrl record);

    CrawlUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrawlUrl record);

    int updateByPrimaryKey(CrawlUrl record);

    int updateTitle(CrawlUrl crawlURL);

    CrawlUrl selectByUrlAndTaskId(@Param("url") String url, @Param("taskId") Integer taskId);

    void truncate();

    List<CrawlUrl> selectAllEmptyTitle();

    Integer selectTotalUrlByTaskId(Integer taskId);
}