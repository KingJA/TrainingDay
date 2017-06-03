package com.kingja.trainingday.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/6/2 17:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TimeUtil {
    public static List<String> getDates(String fromTime, int days) {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd");
        Calendar selectedDate = Calendar.getInstance();
        try {
            Date fromDate = fromFormat.parse(fromTime);
            selectedDate.setTime(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < days; i++) {
            selectedDate.add(Calendar.DAY_OF_MONTH, i == 0 ? 0 : 1);
            String date = toFormat.format(selectedDate.getTime());
            dates.add(date);
        }
        return dates;
    }

    public static String getEndDate(String startDate, int days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar selectedDate = Calendar.getInstance();
        try {
            Date fromDate = format.parse(startDate);
            selectedDate.setTime(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        selectedDate.add(Calendar.DAY_OF_MONTH, days - 1);
        String date = format.format(selectedDate.getTime());
        return date;
    }

    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
