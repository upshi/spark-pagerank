package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.PageRankResult;

public interface PageRankResultDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PageRankResult record);

    int insertSelective(PageRankResult record);

    PageRankResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PageRankResult record);

    int updateByPrimaryKey(PageRankResult record);
}