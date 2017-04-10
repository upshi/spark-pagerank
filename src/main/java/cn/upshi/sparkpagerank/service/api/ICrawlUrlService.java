package cn.upshi.sparkpagerank.service.api;

import cn.upshi.sparkpagerank.model.Task;

/**
 * spark-pagerank cn.upshi.sparkpagerank.service
 * 描述：
 * 时间：2017-4-7 23:28.
 */

public interface ICrawlUrlService {

    public void crawl(Task task);

    public void title();

}
