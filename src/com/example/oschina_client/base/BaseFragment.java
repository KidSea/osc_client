package com.example.oschina_client.base;

import java.util.zip.Inflater;

import com.example.oschina_client.interf.BaseFragmentInterface;
import com.example.oschina_client.interf.BaseViewInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Choreographer.FrameCallback;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 碎片基类
 * 
 * @author yuxuehai
 * 
 */
public class BaseFragment extends Fragment implements OnClickListener,
		BaseFragmentInterface {
	public static final int STATE_NONE = 0;
	public static final int STATE_REFRESH = 1;
	public static final int STATE_LOADMORE = 2;
	public static final int STATE_NOMORE = 3;
	public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
	public static int mState = STATE_NONE;

	protected LayoutInflater mInflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.mInflater = inflater;
		View view = super.onCreateView(inflater, container, savedInstanceState);

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	protected int getLayoutId() {
		return 0;
	}

	public View inflateView(int resId) {
		return this.mInflater.inflate(resId, null);
	}

	public boolean onBackPressed() {
		return false;
	}
}
