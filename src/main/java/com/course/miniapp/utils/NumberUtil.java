package com.course.miniapp.utils;

import java.util.ArrayList;
import java.util.List;

public final class NumberUtil {

    /**
     * 生成数字列表，前后都闭
     * @param from 起始
     * @param end 结束
     */
    public static List<Integer> fromToEnd(Integer from, Integer end) {
        if (from > end) {
            Integer tmp = from;
            from = end;
            end = tmp;
        }
        List<Integer> res = new ArrayList<>(end - from + 1);
        for (int i = from; i <= end; i++) {
            res.add(i);
        }
        return res;
    }
}
