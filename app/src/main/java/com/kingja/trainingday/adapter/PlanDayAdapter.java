package com.kingja.trainingday.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.ui.SquaredImageView;
import com.kingja.trainingday.util.TimeUtil;

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
        holder.tv_detail_data.setText(TimeUtil.getDayTime(bean.getDate()));
        locationMap.put(position, holder.siv_star);
        holder.siv_star.setColorFilter(bean.getStatus() == 1 ? context.getResources().getColor(R.color.k_yellow) :
                context.getResources().getColor(R.color.k_grey));
        holder.cv_planDay.setCardBackgroundColor(bean.getStatus() == 1 ? context.getResources().getColor(R.color.k_yellow_light) :
                context.getResources().getColor(R.color.k_white));
    }


    class ViewHolder extends BaseRvAdaper.ViewHolder {
        public TextView tv_detail_data;
        public SquaredImageView siv_star;
        public CardView cv_planDay;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_detail_data = (TextView) itemView.findViewById(R.id.tv_detail_data);
            siv_star = (SquaredImageView) itemView.findViewById(R.id.siv_star);
            cv_planDay = (CardView) itemView.findViewById(R.id.cv_planDay);
        }
    }

    public int[] getLocation() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDate().equals(TimeUtil.getNowDate())) {
                int[] location = new int[2];
                locationMap.get(i).getLocationInWindow(location);
                updatePlanDay(list.get(i));
                return location;
            }
        }
        return null;
    }

    private void updatePlanDay(PlanDay planDay) {
        planDay.setStatus(1);
        DBManager.getInstance().updatePlanDays(planDay);
    }

    private boolean isToday(PlanDay planDay) {
        return planDay.getDate().equals(TimeUtil.getNowDate());
    }

}
