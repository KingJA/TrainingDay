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
public class Plan implements Serializable{
    private static final long serialVersionUID = -8858604263240302853L;
    @Id
    private String planId;
    private String startDate;
    private String endDate;
    private int planDays;
    private String planContent;
    private String gift;
    private String createTime;
    @Generated(hash = 1194263838)
    public Plan(String planId, String startDate, String endDate, int planDays,
            String planContent, String gift, String createTime) {
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.planDays = planDays;
        this.planContent = planContent;
        this.gift = gift;
        this.createTime = createTime;
    }
    @Generated(hash = 592612124)
    public Plan() {
    }
    public String getPlanId() {
        return this.planId;
    }
    public void setPlanId(String planId) {
        this.planId = planId;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getPlanDays() {
        return this.planDays;
    }
    public void setPlanDays(int planDays) {
        this.planDays = planDays;
    }
    public String getPlanContent() {
        return this.planContent;
    }
    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }
    public String getGift() {
        return this.gift;
    }
    public void setGift(String gift) {
        this.gift = gift;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
