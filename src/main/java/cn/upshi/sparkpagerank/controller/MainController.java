package cn.upshi.sparkpagerank.controller;

import cn.upshi.sparkpagerank.dto.Result;
import cn.upshi.sparkpagerank.model.Task;
import cn.upshi.sparkpagerank.service.api.ICrawlUrlService;
import cn.upshi.sparkpagerank.service.api.IPageRankResultService;
import cn.upshi.sparkpagerank.service.api.ITaskService;
import cn.upshi.sparkpagerank.util.RespUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private IPageRankResultService pageRankResultService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ResponseBody
    @RequestMapping("/start")
    public Map<String, Object> start(String startUrl, int total) {
        Map<String, Object> dataMap = new HashMap();
        Task task = new Task();
        task.setHasHandled(0);
        task.setStartUrl(startUrl);
        task.setTotalUrl(total);
        task.setCreateTime(sdf.format(new Date()));
        task.setStatus(Task.CREAT);
        Integer taskId = taskService.add(task);
        if(taskId == null) {
            return RespUtil.error("新建任务失败!");
        }
        task.setId(taskId);
        crawlUrlService.crawl(task);
        dataMap.put("taskId", task.getId());
        return RespUtil.success(dataMap);
    }


    @ResponseBody
    @RequestMapping("/getTask")
    public Map<String, Object> getTasks(int taskId) {
        Map<String, Object> dataMap = new HashMap();
        Task task = taskService.get(taskId);
        if(task != null) {
            dataMap.put("task", task);
            return RespUtil.success(dataMap);
        } else {
            return RespUtil.error("您输入的ID不存在");
        }
    }

    @ResponseBody
    @RequestMapping("/getTasks")
    public Map<String, Object> getTasks(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> dataMap = new HashMap();
        PageInfo<Task> pageInfo = taskService.search(page, size);
        dataMap.put("tasks", pageInfo.getList());
        dataMap.put("page", page);
        dataMap.put("size", pageInfo.getSize());
        dataMap.put("total", pageInfo.getTotal());

        return RespUtil.success(dataMap);
    }

    @ResponseBody
    @RequestMapping("/getResult")
    public Map<String, Object> getResult(int taskId) {
        Map<String, Object> dataMap = new HashMap();
        Task task = taskService.get(taskId);
        if(task == null) {
            return RespUtil.error("您输入的ID不存在");
        } else if(task.getStatus() != Task.PAGERANK) {
            return RespUtil.error("任务尚未完成");
        }

        List<Result> pageRankResults = pageRankResultService.selectByTaskId(taskId);
        dataMap.put("result", pageRankResults);
        return RespUtil.success(dataMap);
    }

    @ResponseBody
    @RequestMapping("/truncate")
    public Map<String, Object> truncate(String password) {
        if(password != null && "upshi".equals(password)) {
            taskService.truncate();
            return RespUtil.success(null);
        } else {
            return RespUtil.error("您的密码有误!");
        }
    }

    @ResponseBody
    @RequestMapping("/deleteTask")
    public Map<String, Object> deleteTask(int taskId) {
        Task task = taskService.get(taskId);
        if(task == null) {
            return RespUtil.error("您输入的ID不存在!");
        }
        if(task.getStatus() == Task.CRAWLING) {
            return RespUtil.error("任务尚未完成!");
        }
        taskService.delete(taskId);
        return RespUtil.success(null);
    }

}
