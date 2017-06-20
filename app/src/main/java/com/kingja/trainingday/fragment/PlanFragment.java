package com.kingja.trainingday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.kingja.rxbus2.RxBus;
import com.kingja.rxbus2.Subscribe;
import com.kingja.trainingday.R;
import com.kingja.trainingday.activity.AddPlanActivity;
import com.kingja.trainingday.activity.PlanDetailActivity;
import com.kingja.trainingday.adapter.PlanAdapter;
import com.kingja.trainingday.base.BaseFragment;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.event.RefreshEvent;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.inject.commonent.AppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2017/6/2 10:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlanFragment extends BaseFragment {
    @BindView(R.id.aiv_add_plan)
    AppCompatImageView aivAddPlan;
    @BindView(R.id.rv_add_plan)
    RecyclerView rvAddPlan;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private PlanAdapter planAdapter;
    private List<Plan> plans;

    @Override
    protected void initVariable() {
        RxBus.getDefault().register(this);
        plans = DBManager.getInstance().getPlans();
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewAndListener() {

        planAdapter = new PlanAdapter(getActivity(), plans);
        planAdapter.setOnItemClickListener((plan, position) -> {
            PlanDetailActivity.goActivity(getActivity(), plan, position);
        });
        new RecyclerViewHelper.Builder(getActivity())
                .setAdapter(planAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.VERTICAL_LIST)
                .build()
                .attachToRecyclerView(rvAddPlan);

    }

    @Override
    protected void initData() {
        tvEmpty.setText("还没有计划，快去添加吧");
        llEmpty.setVisibility(plans.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_plane;
    }

    @OnClick({R.id.aiv_add_plan})
    public void addPlan(View view) {
        presentActivity(view);

    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(getActivity(), AddPlanActivity.class);
        intent.putExtra(AddPlanActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(AddPlanActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Subscribe
    public void reFreshPlans(RefreshEvent event) {
        plans = DBManager.getInstance().getPlans();
        llEmpty.setVisibility(plans.size() > 0 ? View.GONE : View.VISIBLE);
        planAdapter.setData(plans);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}
