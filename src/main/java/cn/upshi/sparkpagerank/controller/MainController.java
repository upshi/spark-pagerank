package cn.upshi.sparkpagerank.controller;

import cn.upshi.sparkpagerank.service.api.ICrawlUrlService;
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

    @ResponseBody
    @RequestMapping("/start")
    public Map<String, Object> start(String startUrl, int total) {
        Map<String, Object> map = new HashMap();
        crawlUrlService.crawl(startUrl, total);
        return map;
    }

    @ResponseBody
    @RequestMapping("/title")
    public Map<String, Object> title() {
        Map<String, Object> map = new HashMap();
        crawlUrlService.title();
        return map;
    }


}
