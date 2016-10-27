package com.example.oschina_client.fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.internal.widget.ListPopupWindow;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.base.BaseFragment;
import com.example.oschina_client.bean.Comment;
import com.example.oschina_client.bean.Entity;
import com.example.oschina_client.bean.Result;
import com.example.oschina_client.bean.ResultBean;
import com.example.oschina_client.cache.CacheManager;
import com.example.oschina_client.empty.EmptyLayout;
import com.example.oschina_client.utils.HTMLUtil;
import com.example.oschina_client.utils.TDevice;
import com.example.oschina_client.utils.XmlUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

public abstract class BaseDetailFragment extends BaseFragment implements
		OnItemClickListener {
	public static final String INTENT_ACTION_COMMENT_CHANGED = "INTENT_ACTION_COMMENT_CHAGED";

	private MenuAdapter mMenuAdapter;
	private ListPopupWindow mMenuWindow;
	private AsyncTask<String, Void, Entity> mCacheTask;
	private boolean mIsFavorited;
	
	public int mCommentCount = 0;
	protected WebView mWebView;

	protected EmptyLayout mEmptyLayout;

	protected AsyncHttpResponseHandler mCommentHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			// TODO Auto-generated method stub
			try {
				ResultBean rsb = XmlUtils.toBean(ResultBean.class,
						new ByteArrayInputStream(arg2));
				Result res = rsb.getResult();
				if (res.OK()) {
					AppContext.showToastShort(R.string.comment_publish_success);

					commentPubSuccess(rsb.getComment());
				} else {
					AppContext.showToastShort(res.getErrorMessage());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				onFailure(arg0, arg1, arg2, e);
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub
			AppContext.showToastShort(R.string.comment_publish_faile);
		}

		public void onFinish() {
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMenuAdapter = new MenuAdapter();
		setHasOptionsMenu(true);
	}

	protected void commentPubSuccess(Comment comment) {
		// TODO Auto-generated method stub

	}

	protected boolean hasReportMenu() {
		return false;
	}

	protected void onCommentChanged(int opt, int id, int catalog,
			boolean isBlog, Comment comment) {
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		recycleWebView();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		cancelReadCache();
		recycleWebView();
		super.onDestroy();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		requestData(false);
	}

	protected Entity parseData(InputStream is) throws Exception {
		return null;
	}

	protected void requestData(boolean refresh) {
		// TODO Auto-generated method stub
		String key = getCacheKey();
		if (TDevice.hasInternet()
				&& (!CacheManager.isExistDataCache(getActivity(), key) || refresh)) {
			sendRequestData();
		} else {
			readCacheData(key);
		}
	}

	// 刷新数据
	protected void sendRefresh() {
		sendRequestData();
	}

	private void readCacheData(String key) {
		// TODO Auto-generated method stub
		cancelReadCache();
		mCacheTask = new CacheTask(getActivity()).execute(key);
	}

	protected void sendRequestData() {
		// TODO Auto-generated method stub

	}

	protected String getCacheKey() {
		// TODO Auto-generated method stub
		return null;
	}

	private void cancelReadCache() {
		// TODO Auto-generated method stub
		if (mCacheTask != null) {
			mCacheTask.cancel(true);
			mCacheTask = null;
		}
	}

	protected void recycleWebView() {
		// TODO Auto-generated method stub
		if (mWebView != null) {
			mWebView.setVisibility(View.GONE);
			mWebView.removeAllViews();
			mWebView.destroy();
			mWebView = null;
		}
	}

	protected Entity readData(Serializable seri) {
		// TODO Auto-generated method stub
		return null;
	}

	private class CacheTask extends AsyncTask<String, Void, Entity> {
		private final WeakReference<Context> mContext;

		private CacheTask(Context context) {
			// TODO Auto-generated constructor stub
			mContext = new WeakReference<Context>(context);
		}

		@Override
		protected Entity doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (mContext.get() != null) {
				Serializable seri = CacheManager.readObject(mContext.get(),
						params[0]);
				if (seri == null) {
					return null;
				} else {
					return readData(seri);
				}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Entity result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				executeOnLoadDataSuccess(result);
			} else {
				executeOnLoadDataError(null);
			}
			executeOnLoadFinish();
		}
	}

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
			CacheManager.saveObject(mContext.get(), seri, key);
			return null;
		}
	}

	protected AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			try {
				Entity entity = parseData(new ByteArrayInputStream(arg2));
				if (entity != null) {
					mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
					executeOnLoadDataSuccess(entity);
					saveCache(entity);
				} else {
					throw new RuntimeException("load detail error");
				}
			} catch (Exception e) {
				e.printStackTrace();
				onFailure(arg0, arg1, arg2, e);
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// executeOnLoadDataError(arg3.getMessage());
			readCacheData(getCacheKey());
		}
	};

	@SuppressLint("ViewHolder")
	private static class MenuAdapter extends BaseAdapter {

		public void setFavorite(boolean favorite) {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater.from(parent.getContext()).inflate(
					R.layout.list_cell_popup_menu, null);
			TextView name = (TextView) convertView.findViewById(R.id.tv_name);

			int iconResId = 0;
			if (position == 0) {
				name.setText(parent.getResources().getString(
						R.string.detail_menu_for_share));
				iconResId = R.drawable.abc_ic_menu_moreoverflow_normal_holo_dark;
			} else if (position == 1) {
				name.setText(parent.getResources().getString(
						R.string.detail_menu_for_report));
				iconResId = R.drawable.abc_ic_menu_moreoverflow_normal_holo_dark;
			}
			Drawable drawable = AppContext.resources().getDrawable(iconResId);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			name.setCompoundDrawables(drawable, null, null, null);
			return convertView;
		}

	}

	protected void executeOnLoadDataSuccess(Entity result) {
		// TODO Auto-generated method stub

	}

	protected void saveCache(Entity entity) {
		// TODO Auto-generated method stub
		new SaveCacheTask(getActivity(), entity, getCacheKey()).execute();
	}

	protected void executeOnLoadFinish() {
		// TODO Auto-generated method stub

	}

	protected void executeOnLoadDataError(String object) {
		// TODO Auto-generated method stub
		mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
		mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mState = STATE_REFRESH;
				mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
				requestData(true);
			}
		});
	}

	protected void onFavoriteChanged(boolean flag) {
	}

	protected int getFavoriteTargetId() {
		return -1;
	}

	protected int getFavoriteTargetType() {
		return -1;
	}

	protected String getShareUrl() {
		return "";
	}

	protected String getShareTitle() {
		return getString(R.string.share_title);
	}

	protected String getShareContent() {
		return "";
	}

	/**
	 * 获取去除html标签的body
	 * 
	 * @param body
	 * @return
	 */
	protected String getFilterHtmlBody(String body) {
		if (body == null)
			return "";
		return HTMLUtil.delHTMLTag(body.trim());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (position == 0) {
			handleFavoriteOrNot();
			handleShare();
		} else if (position == 1) {
			onReportMenuClick();
		} else if (position == 2) {

		}
		if (mMenuWindow != null) {
			mMenuWindow.dismiss();
			mMenuWindow = null;
		}
	}

	/**
	 * 收藏
	 */
	public void handleFavoriteOrNot() {
		if (!TDevice.hasInternet()) {
			AppContext.showToastShort(R.string.tip_no_internet);
			return;
		}
	}

	/**
	 * 分享
	 */
	public void handleShare() {
	}

	public void onReportMenuClick() {
	}

	private final AsyncHttpResponseHandler mResponseHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2) {
			// TODO Auto-generated method stub
			if (TextUtils.isEmpty(arg2)) {
				AppContext.showToastShort(R.string.tip_report_success);
			} else {
				AppContext.showToastShort(new String(arg2));
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub
			AppContext.showToastShort(R.string.tip_report_faile);
		}

		public void onFinish() {
		};
	};

	protected String getRepotrUrl() {
		return "";
	}

	protected int getRepotrId() {
		return 0;
	}

	@SuppressWarnings("deprecation")
	private void shareToWeiChatCircle() {
	}

	@SuppressWarnings("deprecation")
	private void shareToWeiChat() {
		// 添加微信平台
	}

	private void shareToSinaWeibo() {
		// 设置新浪微博SSO handler
	}

	protected void notifyFavorite(boolean favorite) {
		mIsFavorited = favorite;
		FragmentActivity aty = getActivity();
		if (aty != null) {
			aty.supportInvalidateOptionsMenu();
		}
		if (mMenuAdapter != null) {
			mMenuAdapter.setFavorite(favorite);
		}
		onFavoriteChanged(favorite);
	}

	public abstract int getCommentCount();

	@Override
	public void onClick(View v) {
	}

	@Override
	public void initView(View view) {
	}

	public void onclickWriteComment() {
	}

	@Override
	public void initData() {
	}

}
