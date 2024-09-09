package com.course.miniapp.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    private static final String regex = "\\((.*?)小节\\)";
    private static final Pattern pattern = Pattern.compile(regex);

    /**
     * 提取第几节课
     */
    public static List<Integer> extractNth(String input) {
        if (StringUtils.isBlank(input)) {
            return Collections.emptyList();
        }
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String subSections = matcher.group(1);  // 提取括号内的内容
            String[] subSectionArray = subSections.split(",");
            List<Integer> res = new ArrayList<>(subSectionArray.length);
            for (String sub : subSectionArray) {
                res.add(Integer.parseInt(sub.trim()));
            }
            return res;
        } else {
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        String input1 = "第12节\n(01,02小节)\n08:30-09:55";
        String input2 = "第5节\n(05小节)\n11:45-12:25";

        List<Integer> integers = extractNth(input1);
        List<Integer> integers1 = extractNth(input2);
        System.out.println(integers);
        System.out.println(integers1);
    }
}
