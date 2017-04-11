package cn.upshi.sparkpagerank.service.impl;

import cn.upshi.sparkpagerank.dao.PageRankResultDao;
import cn.upshi.sparkpagerank.dto.Result;
import cn.upshi.sparkpagerank.service.api.IPageRankResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service.impl
 * 描述：
 * 时间：2017-4-11 14:09.
 */

@Service
public class PageRankResultServiceImpl implements IPageRankResultService {

    @Autowired
    PageRankResultDao pageRankResultDao;

    @Override
    public List<Result> selectByTaskId(int taskId) {
        return pageRankResultDao.selectByTaskId(taskId);
    }
}
