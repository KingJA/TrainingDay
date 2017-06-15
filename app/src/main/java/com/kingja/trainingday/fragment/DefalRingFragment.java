package com.kingja.trainingday.fragment;

import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;

import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.adapter.LocalRingsAdapter;
import com.kingja.trainingday.base.App;
import com.kingja.trainingday.base.BaseFragment;
import com.kingja.trainingday.bean.Ring;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.util.AlarmPlayer;
import com.kingja.trainingday.util.Constants;
import com.kingja.trainingday.util.Sp;

import java.io.IOException;
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
    private LocalRingsAdapter mLocalRingsAdapter;


    @Override
    protected void initVariable() {
        AssetManager assets = getActivity().getAssets();
        try {
            String[] ringArr = assets.list("rings");
            String path = "file:///android_asset/rings/";
            for (String ring : ringArr) {
                rings.add(new Ring(ring,path+ring));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewAndListener() {
        mLocalRingsAdapter = new LocalRingsAdapter(getActivity(), rings);

    }

    @Override
    protected void initData() {

        new RecyclerViewHelper.Builder(getActivity()).setLayoutStyle(LayoutHelper.LayoutStyle.VERTICAL_LIST)
                .setAdapter(mLocalRingsAdapter).build().attachToRecyclerView
                (singleRv);
        mLocalRingsAdapter.setOnItemClickListener((ring, position) -> {
            mLocalRingsAdapter.selectPosition(position);

//            AlarmPlayer.getInstance(App.getContext()).playAssets("rings/"+ring.getRingName());
            AlarmPlayer.getInstance(App.getContext()).playPath(ring.getPath());
            Sp.getInstance(getActivity()).putData(Constants.RING_PATH, ring.getPath());
        });

    }

    @Override
    protected int getContentId() {
        return R.layout.layout_rv;
    }

}
