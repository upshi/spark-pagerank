package cn.upshi.sparkpagerank.service.api;

import cn.upshi.sparkpagerank.model.PageRankResult;

import java.util.List;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service.api
 * 描述：
 * 时间：2017-4-11 14:08.
 */

public interface IPageRankResultService {

    List<PageRankResult> selectByTaskId(int taskId);

}
