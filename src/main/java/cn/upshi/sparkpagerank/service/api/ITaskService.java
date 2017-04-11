package cn.upshi.sparkpagerank.service.api;

import cn.upshi.sparkpagerank.model.Task;
import com.github.pagehelper.PageInfo;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service.api
 * 描述：
 * 时间：2017-4-10 15:58.
 */

public interface ITaskService {

    public Integer add(Task task);

    PageInfo<Task> search(int page, int size);
}
