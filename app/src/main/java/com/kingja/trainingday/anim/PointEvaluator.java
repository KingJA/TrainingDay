package com.kingja.trainingday.anim;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Description:TODO
 * Create Time:2017/6/3 17:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
