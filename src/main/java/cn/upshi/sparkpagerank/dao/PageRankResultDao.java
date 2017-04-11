package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.PageRankResult;

import java.util.ArrayList;
import java.util.List;

public interface PageRankResultDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PageRankResult record);

    int insertBatch(ArrayList<PageRankResult> list);

    int insertSelective(PageRankResult record);

    PageRankResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PageRankResult record);

    int updateByPrimaryKey(PageRankResult record);

    void truncate();

    List<PageRankResult> selectByTaskId(int taskId);
}