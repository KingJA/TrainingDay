package com.kingja.trainingday.dao;


import android.database.sqlite.SQLiteDatabase;

import com.kingja.trainingday.base.App;
import com.kingja.trainingday.greendao.DaoMaster;
import com.kingja.trainingday.greendao.DaoSession;
import com.kingja.trainingday.greendao.PlanDao;
import com.kingja.trainingday.greendao.PlanDayDao;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;

import java.util.List;

public class DBManager implements IDbOperator {
    private final String DB_NAME = "TrainningDay";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private final SQLiteDatabase db;

    private DBManager() {
        openHelper = new DaoMaster.DevOpenHelper(App.getContext(), DB_NAME, null);
        db = getWritableDatabase();
    }

    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }

    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(App.getContext(), DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(App.getContext(), DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    private PlanDao getPlanDao() {
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getPlanDao();
    }

    private PlanDayDao getPlanDayDao() {
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getPlanDayDao();
    }

    @Override
    public void addPlan(Plan plan) {
        PlanDao bleInfoDao = getPlanDao();
        bleInfoDao.insert(plan);
    }

    @Override
    public void addPlanDay(PlanDay planDay) {
        PlanDayDao bleInfoDao = getPlanDayDao();
        bleInfoDao.insert(planDay);
    }

    @Override
    public void removePlan(String id) {
        PlanDao planDao = getPlanDao();
        planDao.deleteByKey(id);
    }

    @Override
    public void removePlanDay(String id) {
        PlanDayDao planDao = getPlanDayDao();
        planDao.deleteByKey(id);
    }

    @Override
    public List<Plan> getPlans() {
        PlanDao planDao = getPlanDao();
        List<Plan> plans = planDao.queryBuilder().orderDesc(PlanDao.Properties.CreateTime).list();
        return plans;
    }

    @Override
    public List<PlanDay> getPlanDays(String planId) {
        PlanDayDao planDayDao = getPlanDayDao();
        List<PlanDay> planDays = planDayDao.queryBuilder().where(PlanDayDao.Properties.PlanId.eq(planId)).orderAsc
                (PlanDayDao.Properties.Date).list();
        return planDays;
    }
}
