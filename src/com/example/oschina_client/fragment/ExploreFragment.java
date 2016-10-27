package com.example.oschina_client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.oschina_client.R;
import com.example.oschina_client.base.BaseFragment;
import com.example.oschina_client.utils.UIHelper;

/**
 * 发现页面
 * @author yuxuehai
 *
 */
public class ExploreFragment extends BaseFragment {

    @InjectView(R.id.rl_active)
    View mRlActive;

    @InjectView(R.id.rl_find_osc)
    View mFindOSCer;

    @InjectView(R.id.rl_city)
    View mCity;

    @InjectView(R.id.rl_activities)
    View mActivities;

    @InjectView(R.id.rl_scan)
    View mScan;

    @InjectView(R.id.rl_shake)
    View mShake;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_explore, null);
        ButterKnife.inject(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.rl_active:
            UIHelper.showMyActive(getActivity());
            break;
        case R.id.rl_find_osc:
//            UIHelper.showSimpleBack(getActivity(), SimpleBackPage.FIND_USER);
            break;
        case R.id.rl_city:
//            UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SAME_CITY);
            break;
        case R.id.rl_activities:
//            UIHelper.showSimpleBack(getActivity(), SimpleBackPage.EVENT_LIST);
            break;
        case R.id.rl_scan:
//            UIHelper.showScanActivity(getActivity());
            break;
        case R.id.rl_shake:
//            UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SHAKE);
            break;
        default:
            break;
        }
    }

    @Override
    public void initView(View view) {
        mRlActive.setOnClickListener(this);

        mFindOSCer.setOnClickListener(this);
        mCity.setOnClickListener(this);
        mActivities.setOnClickListener(this);
        mScan.setOnClickListener(this);
        mShake.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }	
}