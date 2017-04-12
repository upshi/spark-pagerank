package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.PageLink;
import org.apache.ibatis.annotations.Param;

public interface PageLinkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PageLink record);

    PageLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PageLink record);

    int updateByPrimaryKey(PageLink record);

    void truncate();

    void exportLinkFile(@Param("fileName") String fileName, @Param("taskId") Integer taskId);

    void deleteByTaskId(Integer taskId);
}