package com.kingja.trainingday.bean;

import android.net.Uri;

/**
 * Description:TODO
 * Create Time:2017/6/15 14:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Ring {
    private String ringName;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Ring(String ringName, String path) {
        this.ringName = ringName;
        this.path = path;
    }

    public Ring(String ringName, String path, boolean selected) {
        this.ringName = ringName;
        this.path = path;
        this.selected = selected;
    }

    private String path = "-1";
    private Uri uri;
    private boolean selected;

    public String getRingName() {
        return ringName;
    }

    public void setRingName(String ringName) {
        this.ringName = ringName;
    }

    public Ring(String ringName, Uri uri) {
        this.ringName = ringName;
        this.uri = uri;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Ring(String ringName) {
        this.ringName = ringName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
