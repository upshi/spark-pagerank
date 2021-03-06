package cn.upshi.sparkpagerank.dao;

import cn.upshi.sparkpagerank.model.Task;

import java.util.List;

public interface TaskDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    int selectAndSetTotalUrl(Integer taskId);

    void truncate();

    List<Task> search();

}