package com.kingja.trainingday.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.kingja.rxbus2.RxBus;
import com.kingja.rxbus2.Subscribe;
import com.kingja.trainingday.R;
import com.kingja.trainingday.adapter.LocalRingsAdapter;
import com.kingja.trainingday.base.App;
import com.kingja.trainingday.base.BaseFragment;
import com.kingja.trainingday.bean.Ring;
import com.kingja.trainingday.event.ClearDefaultRing;
import com.kingja.trainingday.event.ClearLocalRing;
import com.kingja.trainingday.event.RefreshRingEvent;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.util.AlarmPlayer;
import com.kingja.trainingday.util.Constants;
import com.kingja.trainingday.util.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2017/6/15 10:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DefalRingFragment extends BaseFragment {
    @BindView(R.id.singleRv)
    RecyclerView singleRv;
    private List<Ring> rings = new ArrayList<>();
    private LocalRingsAdapter mDefaultRingsAdapter;


    @Override
    protected void initVariable() {
        RxBus.getDefault().register(this);
        File ringsFile = new File(getActivity().getFilesDir(), "rings");
        String ringPath = (String) Sp.getInstance(getActivity()).getData(Constants.RING_PATH, "");
        if (ringsFile.exists() && ringsFile.listFiles().length > 0) {
            File[] files = ringsFile.listFiles();
            for (File file : files) {
                rings.add(new Ring(file.getName(), file.getAbsolutePath(),ringPath.equals(file.getAbsolutePath())));
            }
        }

    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewAndListener() {
        mDefaultRingsAdapter = new LocalRingsAdapter(getActivity(), rings);

    }

    @Override
    protected void initData() {

        new RecyclerViewHelper.Builder(getActivity()).setLayoutStyle(LayoutHelper.LayoutStyle.VERTICAL_LIST)
                .setAdapter(mDefaultRingsAdapter).build().attachToRecyclerView
                (singleRv);
        mDefaultRingsAdapter.setOnItemClickListener((ring, position) -> {
            mDefaultRingsAdapter.selectPosition(position);

            RxBus.getDefault().post(new ClearLocalRing());
            RxBus.getDefault().post(new RefreshRingEvent(ring.getRingName()));
            AlarmPlayer.getInstance(App.getContext()).playPath(ring.getPath());
            Sp.getInstance(getActivity()).putData(Constants.RING_PATH, ring.getPath());
        });

    }

    @Override
    protected int getContentId() {
        return R.layout.layout_rv;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: "+hidden );
        super.onHiddenChanged(hidden);
    }

    @Subscribe
    public void clearSelectedStatus(ClearDefaultRing clearLocalRing) {
        mDefaultRingsAdapter.clearSelectedStatus();
    }
    @Override
    public void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
