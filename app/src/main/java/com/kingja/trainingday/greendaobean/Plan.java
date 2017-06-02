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
public class Plan {
    @Id
    private String planId;
    private String startDate;
    private String endDate;
    private String planDays;
    private String planContent;
    @Generated(hash = 89545549)
    public Plan(String planId, String startDate, String endDate, String planDays,
            String planContent) {
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.planDays = planDays;
        this.planContent = planContent;
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
    public String getPlanDays() {
        return this.planDays;
    }
    public void setPlanDays(String planDays) {
        this.planDays = planDays;
    }
    public String getPlanContent() {
        return this.planContent;
    }
    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }
}
