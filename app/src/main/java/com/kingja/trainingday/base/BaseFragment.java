package com.kingja.trainingday.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kingja.trainingday.inject.commonent.AppComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/16/16
 * Time: 12:14 AM
 * Desc: BaseFragment
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG=getClass().getSimpleName();
    private ProgressDialog mDialogProgress;
    protected Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCommon();
        initVariable();
        initComponent(App.getContext().getAppComponent());
        initViewAndListener();
        initData();
    }

    private void initCommon() {
        mDialogProgress = new ProgressDialog(getActivity());
    }
    /*设置圆形进度条*/
    protected void setProgressShow(boolean ifShow) {
        if (ifShow) {
            mDialogProgress.show(getActivity(), "", "加载中", true, true);
        } else {
            mDialogProgress.dismiss();
        }
    }


    protected abstract void initVariable();
    protected abstract void initComponent(AppComponent appComponent);
    protected abstract void initViewAndListener();
    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getContentId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    protected abstract int getContentId();


}
