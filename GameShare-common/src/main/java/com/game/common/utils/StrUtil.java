package com.game.common.utils;

public class StrUtil {
    public static boolean isSpaceOrNull(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public static boolean isNull(String str)
    {
    	return isSpaceOrNull(str);
    }

    public static boolean isNotNull(String str)
    {
    	return !isNull(str);
    }

    /**
     * 如果为空，则返回指定的默认值
     */
    public static String nvl(String value, String defaultValue) {
        return isNull(value) ? defaultValue : value;
    }

}
