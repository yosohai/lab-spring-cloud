package org.lab.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lzqing
 * @Description: 容器单例类
 * @Date: Created in 2021/6/6
 */
public class Singleton {
    private static final Map<String, Object> instanceMap = new HashMap<String, Object>();

    private Singleton() {
    }

    public static void addInstance(String key, Object instance) {
        if (!instanceMap.containsKey(key)) {
            instanceMap.put(key, instance);
        }
    }

    public static Object getInstance(String key) {
        return instanceMap.get(key);
    }
}