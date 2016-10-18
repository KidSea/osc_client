package com.example.oschina_client.base;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.bean.Entity;
import com.example.oschina_client.bean.ListEntity;
import com.example.oschina_client.bean.Result;
import com.example.oschina_client.cache.CacheManager;
import com.example.oschina_client.empty.EmptyLayout;
import com.example.oschina_client.utils.TDevice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
 * @param <T>
 * 
 * @param <T>
 */

@SuppressLint("NewApi")
public class BaseListFragment<T extends Entity> extends BaseFragment implements
		OnRefreshListener {
	public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

	protected int mCurrentPage;
	//错误信息
	protected Result mResult;
	
	protected ListBaseAdapter<T> mAdapter;
	
	private AsyncTask<String, Void, ListEntity<T>> mCacheTask;

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
		if (isReadCacheData(refresh)) {

			readCacheData(key);
		} else {
			// 取新的数据
			sendRequestData();
		}
	}

	// 子类实现
	protected void sendRequestData() {
		// TODO Auto-generated method stub

	}

	private void readCacheData(String key) {
		// TODO Auto-generated method stub
		cancelReadCacheTask();
		mCacheTask = new CacheTask(getActivity()).execute(key);
		
	}

	private void cancelReadCacheTask() {
		// TODO Auto-generated method stub

	}

	private class CacheTask extends AsyncTask<String, Void, ListEntity<T>> {
		private final WeakReference<Context> mContext;

		public CacheTask(Context context) {
			// TODO Auto-generated constructor stub
			mContext = new WeakReference<Context>(context);
		}

		@Override
		protected ListEntity<T> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Serializable seri = CacheManager.readObject(mContext.get(),
					params[0]);
			if (seri == null) {
				return null;
			} else {
				return readList(seri);
			}
		}

		protected void onPreExecute(ListEntity<T> list) {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (list != null) {
				executeOnLoadDataSuccess(list.getList());
			} else {
				executeOnLoadDataError(null);
			}
			executeOnLoadFinish();
		}

	}

	/**
	 * 判断是否有缓存
	 * 
	 * @param refresh
	 * @return
	 */
	private boolean isReadCacheData(boolean refresh) {
		// TODO Auto-generated method stub
		String key = getCacheKey();
		if (!TDevice.hasInternet()) {
			return true;
		}

		// 第一页若不是主动刷新，缓存存在，优先取缓存
		if (CacheManager.isExistDataCache(getActivity(), key) && !refresh
				&& mCurrentPage == 0) {
			return true;
		}

		// 其他页数的，缓存存在以及还没有失效，优先取缓存的
		if (CacheManager.isExistDataCache(getActivity(), key)
				&& !CacheManager.isCacheDataFailure(getActivity(), key)
				&& mCurrentPage != 0) {
			return true;
		}
		return false;
	}

	public void executeOnLoadDataSuccess(List<T> data) {
		// TODO Auto-generated method stub
		if (data == null) {
			data = new ArrayList<T>();
		}
		if(mResult != null && !mResult.OK()){
			AppContext.showToast(mResult.getErrorMessage());
			// 注销登陆，密码已经修改，cookie，失效了
			AppContext.getInstance().Logout();
		}
		
		mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
		if(mCurrentPage == 0){
			
		}
	}

	public void executeOnLoadDataError(String error) {
		// TODO Auto-generated method stub

	}

	public void executeOnLoadFinish() {
		// TODO Auto-generated method stub

	}

	protected ListEntity<T> readList(Serializable seri) {
		// TODO Auto-generated method stub
		return null;
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
