package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.CrawlUrl;

public interface CrawlUrlDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CrawlUrl record);

    int insertSelective(CrawlUrl record);

    CrawlUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrawlUrl record);

    int updateByPrimaryKey(CrawlUrl record);

    int updateTitle(CrawlUrl crawlURL);

    CrawlUrl selectByUrl(String url);

    void truncate();
}