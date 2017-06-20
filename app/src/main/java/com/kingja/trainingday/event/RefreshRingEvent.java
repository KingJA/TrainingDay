package com.kingja.trainingday.event;

/**
 * Description:TODO
 * Create Time:2017/6/20 13:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RefreshRingEvent {
    private String ringName;

    public RefreshRingEvent(String ringName) {
        this.ringName = ringName;
    }

    public String getRingName() {
        return ringName;
    }
}
