package cn.upshi.sparkpagerank.util;

import java.util.UUID;

/**
 * CloudPrint cn.edu.zju.cst.cloudprint.util
 * 描述：
 * 时间：2016-12-14 16:12.
 */

public class StringUtil {

    public static String uuid () {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
