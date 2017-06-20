package com.kingja.trainingday.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.bean.Ring;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.Constants;
import com.kingja.trainingday.util.Sp;
import com.kingja.trainingday.util.TimeUtil;

import java.util.List;

/**
 * Description：人员申报
 * Create Time：2016/8/16 13:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LocalRingsAdapter extends BaseRvAdaper<Ring> {

    private final String savedRingPath;

    public LocalRingsAdapter(Context context, List<Ring> list) {
        super(context, list);
        savedRingPath = (String) Sp.getInstance(context).getData
                (Constants.RING_PATH, "");
    }

    @Override
    protected BaseRvAdaper.ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_ring;
    }

    @Override
    protected void bindHolder(BaseRvAdaper.ViewHolder baseHolder, Ring ring,
                              final int position) {
        final ViewHolder holder = (ViewHolder) baseHolder;
        holder.tv_ringName.setText(ring.getRingName());

        Sp.getInstance(context).getData(Constants.RING_PATH, "");
        boolean selected = ring.isSelected();
        holder.tv_ringName.setTextColor(selected ? context.getResources().getColor(R.color.colorAccent) :
                context.getResources().getColor(R.color.k_font_6));
        holder.rb_selected.setChecked(selected);

    }


    class ViewHolder extends BaseRvAdaper.ViewHolder {
        public TextView tv_ringName;
        public AppCompatRadioButton rb_selected;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_ringName = (TextView) itemView.findViewById(R.id.tv_ringName);
            rb_selected = (AppCompatRadioButton) itemView.findViewById(R.id.rb_selected);
        }
    }

    public void selectPosition(int position) {
        for (Ring ring : list) {
            ring.setSelected(false);
        }
        list.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    public void clearSelectedStatus() {
        for (Ring ring : list) {
            ring.setSelected(false);
        }
        notifyDataSetChanged();
    }
}
