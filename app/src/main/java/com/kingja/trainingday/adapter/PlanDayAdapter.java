package com.kingja.trainingday.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;

import java.util.List;

/**
 * Description：
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlanDayAdapter extends BaseRvAdaper<PlanDay> {

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
    }


    class ViewHolder extends BaseRvAdaper.ViewHolder {
        public TextView tv_detail_data;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_detail_data = (TextView) itemView.findViewById(R.id.tv_detail_data);
        }
    }
}
