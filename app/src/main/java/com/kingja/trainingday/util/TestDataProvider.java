package com.kingja.trainingday.util;

import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;

import java.util.List;
import java.util.Random;

/**
 * Description:TODO
 * Create Time:2017/6/6 14:53
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TestDataProvider {
    private static String[] planContents = {"每天深蹲100个", "每天一篇英语文章", "每天一篇博客", "每天跑1000m", "每天锻炼1小时"};
    private static String[] gifts = {"买一只颂拓手表", "apple设备一台", "游戏机一台", "国内游3天", "睡个懒觉"};

    public static void setNowPlanDay() {
        List<String> dates = TimeUtil.getTestDates(7, 4);
        Plan plan = new Plan();
        String planId = StringUtil.getUUID();
        plan.setPlanId(planId);
        plan.setCreateTime(TimeUtil.getNowTime());
        plan.setGift(gifts[new Random().nextInt(gifts.length)]);
        plan.setPlanDays(7);
        plan.setPlanContent(planContents[new Random().nextInt(planContents.length)]);
        plan.setStartDate(dates.get(0));
        plan.setEndDate(dates.get(dates.size() - 1));
        DBManager.getInstance().addPlan(plan);
        for (int i = 0; i < dates.size(); i++) {
            PlanDay planDay = new PlanDay();
            planDay.setDate(dates.get(i));
            planDay.setDayId(StringUtil.getUUID());
            planDay.setPlanId(planId);

            if (i == 0 || i == 3) {
                planDay.setStatus(-1);
            }
            if (i == 1 || i == 2) {
                planDay.setStatus(1);
            }
            DBManager.getInstance().addPlanDay(planDay);
        }
    }

    public static void setFinishedPlanDay(int finished,int before) {
        List<String> dates = TimeUtil.getTestDates(7, before);
        Plan plan = new Plan();
        String planId = StringUtil.getUUID();
        plan.setPlanId(planId);
        plan.setCreateTime(TimeUtil.getNowTime());
        plan.setGift(gifts[new Random().nextInt(gifts.length)]);
        plan.setPlanDays(7);
        plan.setPlanContent(planContents[new Random().nextInt(planContents.length)]);
        plan.setStartDate(dates.get(0));
        plan.setEndDate(dates.get(dates.size() - 1));
        DBManager.getInstance().addPlan(plan);
        for (int i = 0; i < dates.size(); i++) {
            PlanDay planDay = new PlanDay();
            planDay.setDate(dates.get(i));
            planDay.setDayId(StringUtil.getUUID());
            planDay.setPlanId(planId);
            planDay.setStatus(finished);
            DBManager.getInstance().addPlanDay(planDay);
        }
    }
}
