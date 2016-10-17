package com.example.oschina_client.base;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

import com.example.oschina_client.R;
import com.example.oschina_client.empty.EmptyLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author yuxuehai
 * 
 *         包含ListView列表的Fragment基类
 * 
 *         实现接口: 刷新监听, 条目点击监听, ListView滚动监听
 * 
 *         实现功能: 1. 从网络或本地缓存获取到的 数据的解析 (得到数据对象集合) 2. 给ListView设置数据适配器. 数据适配器
 *         通过抽象方法getListAdapter()由子类实现创建Adapter. 3. 下拉刷新, 上拉加载更多功能的ListView
 * 
 * @param <T>
 */

@SuppressLint("NewApi")
public class BaseListFragment extends BaseFragment implements OnRefreshListener {
	public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

	private int mCurrentPage;
	
	@InjectView(R.id.swiperefreshlayout)
	protected SwipeRefreshLayout mSwipeRefreshLayout;

	@InjectView(R.id.listview)
	protected ListView listView;

	@InjectView(R.id.empty)
	protected EmptyLayout mErrorLayout;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_pull_refresh_listview;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(getLayoutId(), container, false);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.inject(this, view);
		initView(view);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeResources(
				R.color.swiperefresh_color1, R.color.swiperefresh_color2,
				R.color.swiperefresh_color3, R.color.swiperefresh_color4);
		
		mErrorLayout.setOnLayoutClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCurrentPage = 0;
				mState = STATE_LOADMORE;
				mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
				requestData(true);
			}
		});
	}
	/**
	 * 
	 * @param refresh
	 */
	protected void requestData(boolean refresh) {
		// TODO Auto-generated method stub
		String key = getCacheKey();
		if(isReadCacheData(refresh)){
			
			readCacheData(key);
		}else {
			//取新的数据
			sendRequestData();
		}
	}

	private void sendRequestData() {
		// TODO Auto-generated method stub
		
	}

	private void readCacheData(String key) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 判断是否有缓存
	 * @param refresh
	 * @return 
	 */
	private boolean isReadCacheData(boolean refresh) {
		// TODO Auto-generated method stub
		String key = getCacheKey();
		//if(!TDevice)
		
		return false;
	}

	private String getCacheKey() {
		// TODO Auto-generated method stub
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mCurrentPage).toString();
	}

	private String getCacheKeyPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}
}
