package com.example.oschina_client.base;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.bean.Entity;
import com.example.oschina_client.bean.ListEntity;
import com.example.oschina_client.bean.Result;
import com.example.oschina_client.bean.ResultBean;
import com.example.oschina_client.cache.CacheManager;
import com.example.oschina_client.empty.EmptyLayout;
import com.example.oschina_client.utils.StringUtils;
import com.example.oschina_client.utils.TDevice;
import com.example.oschina_client.utils.XmlUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
public abstract class BaseListFragment<T extends Entity> extends BaseFragment
		implements OnRefreshListener, OnItemClickListener, OnScrollListener {
	public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

	protected int mCurrentPage;
	// 错误信息
	protected Result mResult;

	protected int mStoreEmptyState = -1;

	protected int mCatalog = 1;

	protected ListBaseAdapter<T> mAdapter;

	private AsyncTask<String, Void, ListEntity<T>> mCacheTask;
	private ParserTask mParserTask;

	@InjectView(R.id.swiperefreshlayout)
	protected SwipeRefreshLayout mSwipeRefreshLayout;

	@InjectView(R.id.listview)
	protected ListView mListView;

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
		Bundle args = getArguments();
		if (args != null) {
			mCatalog = args.getInt(BUNDLE_KEY_CATALOG, 0);
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (onTimeRefresh()) {
			onRefresh();
		}
	}

	private boolean onTimeRefresh() {
		// TODO Auto-generated method stub
		String lastRefreshTime = AppContext.getLastRefreshTime(getCacheKey());
		String currTime = StringUtils.getCurTimeStr();
		long diff = StringUtils.calDateDifferent(lastRefreshTime, currTime);
		return needAutoRefresh() && diff > getAutoRefreshTime();
	}

	/**
	 * 自动刷新的时间设定 默认：自动刷新的时间为半天时间
	 * 
	 * @return
	 */
	protected long getAutoRefreshTime() {
		// TODO Auto-generated method stub
		return 12 * 60 * 60;// 12小时更新一次
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

		mListView.setOnItemClickListener(this);
		mListView.setOnScrollListener(this);

		if (mAdapter != null) {
			mListView.setAdapter(mAdapter);
			mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
		} else {
			mAdapter = getListAdapter();
			mListView.setAdapter(mAdapter);
		}

		if (requestDataIfViewCreated()) {
			mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
			mState = STATE_NONE;
			requestData(false);
		} else {
			mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
		}

		if (mStoreEmptyState != -1) {
			mErrorLayout.setErrorType(mStoreEmptyState);
		}
	}

	protected boolean requestDataIfViewCreated() {
		// TODO Auto-generated method stub
		return true;
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

	// 是否需要自动刷新
	protected boolean needAutoRefresh() {
		return true;
	}

	private void readCacheData(String key) {
		// TODO Auto-generated method stub
		cancelReadCacheTask();
		mCacheTask = new CacheTask(getActivity()).execute(key);

	}

	private void cancelReadCacheTask() {
		// TODO Auto-generated method stub
		if (mCacheTask != null) {
			mCacheTask.cancel(true);
			mCacheTask = null;
		}
	}

	// 处理缓存任务
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

	// 存储缓存任务
	private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
		private final WeakReference<Context> mContext;
		private final Serializable seri;
		private final String key;

		private SaveCacheTask(Context context, Serializable seri, String key) {
			mContext = new WeakReference<Context>(context);
			this.seri = seri;
			this.key = key;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CacheManager.saveObject(mContext.get(), seri, key);
			return null;
		}

	}

	// 处理解析任务
	private class ParserTask extends AsyncTask<Void, Void, String> {
		private final byte[] reponseData;
		private boolean parserError;
		private List<T> list;

		public ParserTask(byte[] data) {
			this.reponseData = data;
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				ListEntity<T> data = parserList(new ByteArrayInputStream(
						reponseData));
				new SaveCacheTask(getActivity(), data, getCacheKey()).execute();
				list = data.getList();
				if (list == null) {
					ResultBean resultBean = XmlUtils.toBean(ResultBean.class,
							reponseData);
					if (resultBean != null) {
						mResult = resultBean.getResult();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				parserError = true;
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (parserError) {
				readCacheData(getCacheKey());
			} else {
				executeOnLoadDataSuccess(list);
				executeOnLoadFinish();
			}
		}

	}

	protected AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBytes) {
			// TODO Auto-generated method stub
			if (mCurrentPage == 0 && needAutoRefresh()) {
				AppContext.putToLastRefreshTime(getCacheKey(),
						StringUtils.getCurTimeStr());
			}
			if (isAdded()) {
				if (mState == STATE_REFRESH) {
					onRefreshNetworkSuccess();
				}
				executeParserTask(responseBytes);

			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub
			if (isAdded()) {
				readCacheData(getCacheKey());
			}
		}
	};

	protected void onRefreshNetworkSuccess() {
	}

	private void executeParserTask(byte[] responseBytes) {
		// TODO Auto-generated method stub
		cancerParserTask();
		mParserTask = new ParserTask(responseBytes);
		mParserTask.execute();
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

	protected ListEntity<T> parserList(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected ListEntity<T> parseList(InputStream is) throws Exception {
		return null;
	}

	public void executeOnLoadDataSuccess(List<T> data) {
		// TODO Auto-generated method stub
		if (data == null) {
			data = new ArrayList<T>();
		}
		if (mResult != null && !mResult.OK()) {
			AppContext.showToast(mResult.getErrorMessage());
			// 注销登陆，密码已经修改，cookie，失效了
			AppContext.getInstance().Logout();
		}

		mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
		if (mCurrentPage == 0) {
			mAdapter.clear();
		}
		// 去掉次重复的数据
		for (int i = 0; i < data.size(); i++) {
			if (compareTo(mAdapter.getData(), data.get(i))) {
				data.remove(i);
				i--;
			}
		}
		int adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
		if (mAdapter.getCount() + data.size() == 0) {
			adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
		} else if ((data.size() == 0)
				|| (data.size() < getPageSize() && mCurrentPage == 0)) {
			adapterState = ListBaseAdapter.STATE_NO_MORE;
			mAdapter.notifyDataSetChanged();
		} else {
			adapterState = ListBaseAdapter.STATE_LOAD_MORE;
		}
		mAdapter.setState(adapterState);
		mAdapter.addData(data);
		// 判断等于是因为最后一项是listview的状态
		if (mAdapter.getCount() == 1) {
			if (needShowEmptyNoData()) {
				mErrorLayout.setErrorType(EmptyLayout.NODATA);
			} else {
				mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
				mAdapter.notifyDataSetChanged();

			}
		}

	}

	/**
	 * 是否需要隐藏listview，显示无数据状态
	 * 
	 * @return
	 */
	protected boolean needShowEmptyNoData() {
		// TODO Auto-generated method stub
		return true;
	}

	protected int getPageSize() {
		// TODO Auto-generated method stub
		return AppContext.PAGE_SIZE;
	}

	protected boolean compareTo(List<? extends Entity> data, Entity entity) {
		// TODO Auto-generated method stub
		int s = data.size();
		if (entity != null) {
			for (int i = 0; i < s; i++) {
				if (entity.getId() == data.get(i).getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public void executeOnLoadDataError(String error) {
		// TODO Auto-generated method stub
		if (mCurrentPage == 0
				&& !CacheManager.isExistDataCache(getActivity(), getCacheKey())) {
			mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
		} else {
			mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
			mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
			mAdapter.notifyDataSetChanged();
		}
	}

	public void executeOnLoadFinish() {
		// TODO Auto-generated method stub
		setSwipeRefreshLoadedState();

	}

	/** 设置顶部正在加载的状态 */
	private void setSwipeRefreshLoadingState() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(true);
			// 防止多次重复刷新
			mSwipeRefreshLayout.setEnabled(false);
		}
	}

	/** 设置顶部加载完毕的状态 */
	private void setSwipeRefreshLoadedState() {
		// TODO Auto-generated method stub
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(false);
			mSwipeRefreshLayout.setEnabled(true);
		}
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

	protected String getCacheKeyPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		mStoreEmptyState = mErrorLayout.getErrorState();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		cancelReadCacheTask();// 取消读取缓存
		cancerParserTask();// 取消解析内容
		super.onDestroy();
	}

	private void cancerParserTask() {
		// TODO Auto-generated method stub
		if (mParserTask != null) {
			mParserTask.cancel(true);
			mParserTask = null;
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (mState == STATE_REFRESH) {
			return;
		}
		// 设置顶部正在刷新
		mListView.setSelection(0);
		setSwipeRefreshLoadedState();
		mCurrentPage = 0;
		mState = STATE_REFRESH;
		requestData(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		// 没有数据
		if (mAdapter == null || mAdapter.getCount() == 0) {
			return;
		}
		// 数据已经全部加载,或者数据为空时，或正在加载，不处理滚动事件
		if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
			return;
		}
		// 判断是否滚动到底部
		boolean scrollEnd = false;
		try {
			if (view.getPositionForView(mAdapter.getFooterView()) == view
					.getLastVisiblePosition())
				scrollEnd = true;
		} catch (Exception e) {
			// TODO: handle exception
			scrollEnd = false;
		}
		if (mState == STATE_NONE && scrollEnd) {
			if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
					|| mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
				mCurrentPage++;
				mState = STATE_LOADMORE;
				requestData(false);
				mAdapter.setFooterViewLoading();
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        // if (mState == STATE_NOMORE || mState == STATE_LOADMORE
        // || mState == STATE_REFRESH) {
        // return;
        // }
        // if (mAdapter != null
        // && mAdapter.getDataSize() > 0
        // && mListView.getLastVisiblePosition() == (mListView.getCount() - 1))
        // {
        // if (mState == STATE_NONE
        // && mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE) {
        // mState = STATE_LOADMORE;
        // mCurrentPage++;
        // requestData(true);
        // }
        // }		
	}
	
    /**
     * 保存已读的文章列表
     * 
     * @param view
     * @param prefFileName
     * @param key
     */
    protected void saveToReadedList(final View view, final String prefFileName,
            final String key) {
        // 放入已读列表
        AppContext.putReadedPostList(prefFileName, key, "true");
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setTextColor(0xff9a9a9a);
        }
    }
	
	protected abstract ListBaseAdapter<T> getListAdapter();
}
