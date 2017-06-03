package com.kingja.trainingday.fragment;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
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
import com.kingja.trainingday.util.GoUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
            PlanDetailActivity.goActivity(getActivity(),plan,position);
        });
        new RecyclerViewHelper.Builder(getActivity())
                .setAdapter(planAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.VERTICAL_LIST)
                .build()
                .attachToRecyclerView(rvAddPlan);

    }

    @Override
    protected void initNet() {



    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_plane;
    }

    @OnClick({R.id.aiv_add_plan})
    public void addPlan() {
        GoUtil.goActivity(getActivity(), AddPlanActivity.class);
    }

    @Subscribe
    public void reFreshPlans(RefreshEvent event) {
      plans = DBManager.getInstance().getPlans();
        planAdapter.setData(plans);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}
