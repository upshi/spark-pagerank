package cn.upshi.sparkpagerank.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * LabelManagement com.hcloud.apm.label.util
 * 描述：
 *
 * 时间：2016-11-17 13:09.
 */

/**
 * 加载配置文件
 */
public class PropUtil {

    private static final Log logger = LogFactory.getLog(PropUtil.class);

    private static Properties props = new Properties();

    static {
        String path ="/config.properties";
        try {
            props.load(PropUtil.class.getResourceAsStream(path));
        } catch (IOException e) {
            if(logger.isErrorEnabled()) {
                logger.error("PropUtil 配置文件加载失败");
            }
            e.printStackTrace();
        }
    }

    /**
     * 获取配置文件中的值
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return (String)props.get(key);
    }

    public static Integer getInt(String key) {
        Integer i = null;
        try {
            i = Integer.parseInt((String)props.get(key));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return i;
    }

}
