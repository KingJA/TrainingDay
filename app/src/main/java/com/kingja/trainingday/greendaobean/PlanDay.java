package com.kingja.trainingday.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/6/2 10:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Entity
public class PlanDay implements Serializable{
    private static final long serialVersionUID = 9139730078696056149L;
    @Id(autoincrement = true)
    private Long dayId;
    private String planId;
    private String date;
    private int status;
    @Generated(hash = 1672821741)
    public PlanDay(Long dayId, String planId, String date, int status) {
        this.dayId = dayId;
        this.planId = planId;
        this.date = date;
        this.status = status;
    }
    @Generated(hash = 1560388873)
    public PlanDay() {
    }
    public Long getDayId() {
        return this.dayId;
    }
    public void setDayId(Long dayId) {
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
