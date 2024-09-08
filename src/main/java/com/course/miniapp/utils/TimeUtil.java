package com.course.miniapp.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public static void main(String[] args) {
        currentHour();
    }
}