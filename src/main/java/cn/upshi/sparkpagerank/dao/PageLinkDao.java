package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.PageLink;

public interface PageLinkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PageLink record);

    int insertSelective(PageLink record);

    PageLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PageLink record);

    int updateByPrimaryKey(PageLink record);

    void truncate();
}