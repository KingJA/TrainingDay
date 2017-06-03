package com.kingja.trainingday.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.ui.SquaredImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlanDayAdapter extends BaseRvAdaper<PlanDay> {
    private Map<Integer, SquaredImageView> locationMap = new HashMap<>();

    public PlanDayAdapter(Context context, List<PlanDay> list) {
        super(context, list);
    }

    @Override
    protected BaseRvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_day;
    }

    @Override
    protected void bindHolder(BaseRvAdaper.ViewHolder baseHolder, PlanDay bean,
                              final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tv_detail_data.setText(bean.getDate());
        locationMap.put(position, holder.siv_star);
    }


    class ViewHolder extends BaseRvAdaper.ViewHolder {
        public TextView tv_detail_data;
        public SquaredImageView siv_star;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_detail_data = (TextView) itemView.findViewById(R.id.tv_detail_data);
            siv_star = (SquaredImageView) itemView.findViewById(R.id.siv_star);
        }
    }

    public int[] getLocation(int position) {
        int[] location = new int[2];
        locationMap.get(position).getLocationInWindow(location);
        return location;
    }


}
