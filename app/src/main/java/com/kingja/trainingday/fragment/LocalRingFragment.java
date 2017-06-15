package com.kingja.trainingday.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2017/6/15 10:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LocalRingFragment extends BaseFragment {
    private String[] media_music_info = new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media
            .DATA, MediaStore.Audio.Media._ID, MediaStore.Audio.Media
            .DATA, MediaStore.Audio.Media.DURATION};
    private List<Ring> rings = new ArrayList<>();
    private LocalRingsAdapter mLocalRingsAdapter;
    @BindView(R.id.singleRv)
    RecyclerView singleRv;

    @Override
    protected void initVariable() {
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, media_music_info,
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String displayName = cursor.getString(0);
            String path = cursor.getString(1);
            rings.add(new Ring(displayName, path));
            cursor.moveToNext();
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
            AlarmPlayer.getInstance(App.getContext()).playPath(ring.getPath());
            Sp.getInstance(getActivity()).putData(Constants.RING_PATH, ring.getPath());
        });

    }

    @Override
    protected int getContentId() {
        return R.layout.layout_rv;
    }
}

