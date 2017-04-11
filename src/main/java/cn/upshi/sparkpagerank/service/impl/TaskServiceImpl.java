package cn.upshi.sparkpagerank.service.impl;

import cn.upshi.sparkpagerank.dao.TaskDao;
import cn.upshi.sparkpagerank.model.Task;
import cn.upshi.sparkpagerank.service.api.ITaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service.impl
 * 描述：
 * 时间：2017-4-10 15:58.
 */

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public Integer add(Task task) {
        int i = taskDao.insert(task);
        Integer taskId = null;
        if(i == 1) {
            taskId = task.getId();
        }
        return taskId;
    }

    @Override
    public PageInfo<Task> search(int page, int size) {
        PageHelper.startPage(page, size);
        List<Task> tasks = taskDao.search();
        PageInfo<Task> pageInfo = new PageInfo<Task>(tasks);
        return pageInfo;
    }
}
