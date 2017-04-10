package cn.upshi.sparkpagerank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * LabelManagement com.hcloud.apm.label.util
 * 描述：
 *
 * 时间：2016-11-22 15:02.
 */

public class RespUtil {

    public static Map<String, Object> error(String cause) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("error", cause);
        return map;
    }

    public static Map<String, Object> success(Map<String, Object> dataMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if(dataMap != null) {
            map.put("data", dataMap);
        }
        return map;
    }

}
