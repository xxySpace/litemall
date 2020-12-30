package org.linlinjava.litemall.core.util;

import java.util.List;
import java.util.Map;

/**
 * 校空类
 */
public class ArrayUtil {
    private ArrayUtil() {

    }

    /**
     * 判断map为空
     *
     * @param map
     * @return 空返回true 否则返回false
     */
    public static boolean isMapEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param array
     * @return 空返回true 否则返回false
     */
    public static boolean isArrayEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断List是否为空
     *
     * @param list
     * @return 空返回true 否则返回false
     */
    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }
}
