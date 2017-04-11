package cn.upshi.sparkpagerank.controller;

import cn.upshi.sparkpagerank.model.Task;
import cn.upshi.sparkpagerank.service.api.ICrawlUrlService;
import cn.upshi.sparkpagerank.service.api.ITaskService;
import cn.upshi.sparkpagerank.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * spark-pagerank cn.upshi.sparkpagerank.controller
 * 描述：
 * 时间：2017-4-7 23:26.
 */

@Controller
public class MainController {

    @Autowired
    private ICrawlUrlService crawlUrlService;

    @Autowired
    private ITaskService taskService;

    @ResponseBody
    @RequestMapping("/start")
    public Map<String, Object> start(String startUrl, int total) {
        Map<String, Object> dataMap = new HashMap();
        Task task = new Task();
        task.setHasHandled(0);
        task.setStartUrl(startUrl);
        task.setMaxHandled(total);
        task.setTotalUrl(0);
        task.setStatus(Task.CREAT);
        Integer taskId = taskService.add(task);
        if(taskId == null) {
            return RespUtil.error("新建任务失败!");
        }
        task.setId(taskId);
        crawlUrlService.crawl(task);
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/title")
    public Map<String, Object> title() {
        Map<String, Object> map = new HashMap();
        crawlUrlService.title();
        return map;
    }


}
