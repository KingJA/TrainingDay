package com.kingja.trainingday.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:TODO
 * Create Time:2017/6/13 13:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Entity
public class PlanClock implements Serializable{
    private static final long serialVersionUID = 7463373847714627392L;
    @Id(autoincrement = true)
    private Long clockId;
    private String remindTime;
    private int remindType;
    private String ringName;
    private String ringPath;
    @Generated(hash = 608277457)
    public PlanClock(Long clockId, String remindTime, int remindType,
            String ringName, String ringPath) {
        this.clockId = clockId;
        this.remindTime = remindTime;
        this.remindType = remindType;
        this.ringName = ringName;
        this.ringPath = ringPath;
    }
    @Generated(hash = 1309806151)
    public PlanClock() {
    }
    public Long getClockId() {
        return this.clockId;
    }
    public void setClockId(Long clockId) {
        this.clockId = clockId;
    }
    public String getRemindTime() {
        return this.remindTime;
    }
    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
    public int getRemindType() {
        return this.remindType;
    }
    public void setRemindType(int remindType) {
        this.remindType = remindType;
    }
    public String getRingName() {
        return this.ringName;
    }
    public void setRingName(String ringName) {
        this.ringName = ringName;
    }
    public String getRingPath() {
        return this.ringPath;
    }
    public void setRingPath(String ringPath) {
        this.ringPath = ringPath;
    }
}
