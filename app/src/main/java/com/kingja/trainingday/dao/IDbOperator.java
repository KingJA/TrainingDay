package com.kingja.trainingday.dao;

import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.greendaobean.PlanDay;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/6/3 8:47
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IDbOperator {
    void addPlan(Plan plan);

    void addPlanClock(PlanClock planClock);

    void addPlanDay(PlanDay planDay);

    void removePlan(String id);

    void removePlanDay(long id);

    void removePlanClock(long id);

    void getPlans(PlanDay planDay);

    void updatePlanDays(PlanDay planDay);

    List<Plan> getPlans();

    List<PlanDay> getPlanDays(String planId);

    List<PlanDay> getPlanDays();

    List<PlanClock> getPlanClocks();
}
