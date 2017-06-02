package com.kingja.trainingday.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:TODO
 * Create Time:2017/6/2 10:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Entity
public class PlanDay {
    @Id
    private String dayId;
    private String planId;
    private String date;
    private int status;
    @Generated(hash = 548893936)
    public PlanDay(String dayId, String planId, String date, int status) {
        this.dayId = dayId;
        this.planId = planId;
        this.date = date;
        this.status = status;
    }
    @Generated(hash = 1560388873)
    public PlanDay() {
    }
    public String getDayId() {
        return this.dayId;
    }
    public void setDayId(String dayId) {
        this.dayId = dayId;
    }
    public String getPlanId() {
        return this.planId;
    }
    public void setPlanId(String planId) {
        this.planId = planId;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
