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
    private static List<String> getDates(String fromTime, int days) {
        List<String> dates = new ArrayList<>();
        dates.add(fromTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar selectedDate = Calendar.getInstance();
        try {
            Date fromDate = format.parse(fromTime);
            selectedDate.setTime(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < days - 1; i++) {
            selectedDate.add(Calendar.DAY_OF_MONTH, 1);
            String date = format.format(selectedDate.getTime());
            dates.add(date);
        }
        return dates;
    }
}
