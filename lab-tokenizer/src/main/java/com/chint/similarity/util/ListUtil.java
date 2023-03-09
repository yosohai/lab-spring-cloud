package com.chint.similarity.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import com.google.common.collect.Lists;

import java.util.List;

public class ListUtil {

    public static <E> List<Pair<E, E>> concat(List<E> list) {
        List<Pair<E, E>> resultList = Lists.newArrayListWithExpectedSize(list.size() * list.size() / 2);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        if (list.size() >= 2) {
            for (int j = 0; j < list.size(); j++) {
                resultList.addAll(join(list.subList(j, list.size())));
            }

        }
        return resultList;
    }

    private static <E> List<Pair<E, E>> join(List<E> list) {
        List<Pair<E, E>> resultList = Lists.newArrayListWithExpectedSize(list.size() * list.size() / 2);
        for (int j = 1, size = list.size(); j < size; j++) {
            resultList.add(new Pair<E, E>(list.get(0), list.get(j)));
        }
        return resultList;
    }
}
