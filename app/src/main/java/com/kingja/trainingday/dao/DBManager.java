package com.kingja.trainingday.dao;


import android.database.sqlite.SQLiteDatabase;

import com.kingja.trainingday.base.App;
import com.kingja.trainingday.greendao.DaoMaster;
import com.kingja.trainingday.greendao.DaoSession;
import com.kingja.trainingday.greendao.PlanClockDao;
import com.kingja.trainingday.greendao.PlanDao;
import com.kingja.trainingday.greendao.PlanDayDao;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanClock;
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

    private PlanClockDao getPlanClockDao() {
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getPlanClockDao();
    }

    private PlanDayDao getPlanDayDao() {
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getPlanDayDao();
    }

    @Override
    public void addPlan(Plan plan) {
        getPlanDao().insert(plan);
    }

    @Override
    public void addPlanClock(PlanClock planClock) {
        getPlanClockDao().insert(planClock);
    }

    @Override
    public void addPlanDay(PlanDay planDay) {
        getPlanDayDao().insert(planDay);
    }

    @Override
    public void removePlan(String id) {
        getPlanDao().deleteByKey(id);
    }

    @Override
    public void removePlanDay(long id) {
        getPlanDayDao().deleteByKey(id);
    }

    @Override
    public void removePlanClock(long id) {
        getPlanClockDao().deleteByKey(id);
    }

    @Override
    public List<Plan> getPlans() {
        return getPlanDao().queryBuilder().orderDesc(PlanDao.Properties.CreateTime).list();
    }

    @Override
    public List<PlanDay> getPlanDays(String planId) {
        return getPlanDayDao().queryBuilder().where(PlanDayDao.Properties.PlanId.eq(planId)).orderAsc
                (PlanDayDao.Properties.Date).list();
    }

    @Override
    public List<PlanDay> getPlanDays() {
        return getPlanDayDao().loadAll();
    }

    @Override
    public List<PlanClock> getPlanClocks() {
        return getPlanClockDao().loadAll();
    }

    @Override
    public void getPlans(PlanDay planDay) {
        getPlanDayDao().update(planDay);
    }

    @Override
    public void updatePlanDays(PlanDay planDay) {
        getPlanDayDao().update(planDay);
    }
}
