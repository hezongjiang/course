package com.course.miniapp.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

    public static int dateOfWeek() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        // 将DayOfWeek枚举转换为数字，1表示周一，7表示周日
        return dayOfWeek.getValue();
    }

    public static int currentHour() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH"); // HH表示24小时制
        String currentHour = sdf.format(date);
        return Integer.parseInt(currentHour);
    }

    /**
     * 检查两个LocalTime对象之间的时间差是否小于1分钟
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 时间差是否小于1分钟
     */
    public static boolean isDifferenceLessThanOneMinute(LocalTime time1, LocalTime time2) {
        Duration duration = Duration.between(time1, time2).abs(); // 获取绝对值
        return duration.compareTo(Duration.ofMinutes(1)) < 0;
    }

    public static void main(String[] args) {
        // 给定的时间
        String givenTimeStr = "17:15";
        LocalTime givenTime = LocalTime.parse(givenTimeStr, DateTimeFormatter.ofPattern("HH:mm"));

        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 计算给定时间减去10分钟
        LocalTime tenMinutesAgo = givenTime.minusMinutes(10);

        // 判断当前时间是否比给定时间早10分钟
        boolean isEarlierThanTenMinutes = now.isBefore(tenMinutesAgo);

        System.out.println("当前时间: " + now);
        System.out.println("给定时间减去10分钟: " + tenMinutesAgo);
        System.out.println("当前时间是否比给定时间早10分钟: " + isEarlierThanTenMinutes);
    }
}