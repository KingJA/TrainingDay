package com.kingja.trainingday.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.TimeUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Description：人员申报
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlanAdapter extends BaseRvAdaper<Plan> {

    public PlanAdapter(Context context, List<Plan> list) {
        super(context, list);
    }

    @Override
    protected BaseRvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_plan;
    }

    @Override
    protected void bindHolder(BaseRvAdaper.ViewHolder baseHolder, Plan bean,
                              final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tv_date.setText(bean.getStartDate() + "-" + bean.getEndDate() );
        holder.tv_planDays.setText(bean.getPlanDays()+"");
        holder.tv_planContent.setText(bean.getPlanContent());
        holder.tv_planStatus.setText(CheckUtil.isBigger(TimeUtil.getNowDate(),bean.getEndDate())?"已结束":"进行中");
    }


    class ViewHolder extends BaseRvAdaper.ViewHolder {
        public TextView tv_date;
        public TextView tv_planDays;
        public TextView tv_planContent;
        public TextView tv_planStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_planDays = (TextView) itemView.findViewById(R.id.tv_planDays);
            tv_planContent = (TextView) itemView.findViewById(R.id.tv_planContent);
            tv_planStatus = (TextView) itemView.findViewById(R.id.tv_planStatus);
        }
    }
}
