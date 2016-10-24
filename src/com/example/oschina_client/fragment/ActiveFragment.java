package com.example.oschina_client.fragment;

import java.io.InputStream;
import java.io.Serializable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ActiveAdapter;
import com.example.oschina_client.api.remote.OSChinaApi;
import com.example.oschina_client.base.BaseListFragment;
import com.example.oschina_client.base.ListBaseAdapter;
import com.example.oschina_client.bean.Active;
import com.example.oschina_client.bean.ActiveList;
import com.example.oschina_client.bean.Constants;
import com.example.oschina_client.bean.ListEntity;
import com.example.oschina_client.empty.EmptyLayout;
import com.example.oschina_client.utils.HTMLUtil;
import com.example.oschina_client.utils.TDevice;
import com.example.oschina_client.utils.XmlUtils;



public class ActiveFragment extends BaseListFragment<Active> implements OnItemLongClickListener {

	protected static final String TAG = ActiveFragment.class.getSimpleName();
	private static final String CACHE_KEY_PREFIX = "active_list";
	private boolean mIsWatingLogin;

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (mErrorLayout != null) {
				mIsWatingLogin = true;
				mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
				mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));

			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_LOGOUT);
		getActivity().registerReceiver(mReceiver, filter);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (mIsWatingLogin) {
			mCurrentPage = 0;
			mState = STATE_REFRESH;
			requestData(false);
		}
		refreshNotice();
		super.onResume();
	}

	/**
	 * 开始刷新请求
	 */
	private void refreshNotice() {
		// TODO Auto-generated method stub
		// Notice notice = MainActivity.mNotice;
		// if (notice == null) {
		// return;
		// }
		// if (notice.getAtmeCount() > 0 && mCatalog == ActiveList.CATALOG_ATME)
		// {
		// onRefresh();
		// } else if (notice.getReviewCount() > 0
		// && mCatalog == ActiveList.CATALOG_COMMENT) {
		// onRefresh();
		// }
	}

	@Override
	protected ActiveAdapter getListAdapter() {
		// TODO Auto-generated method stub
		return new ActiveAdapter();
	}

	@Override
	protected String getCacheKeyPrefix() {
		// TODO Auto-generated method stub
		return new StringBuffer(CACHE_KEY_PREFIX + mCatalog).append(
				AppContext.getInstance().getLoginUid()).toString();
	}
	
	@Override
	protected ActiveList parseList(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		ActiveList list = XmlUtils.toBean(ActiveList.class, is);
		return list;
	}
	
	@Override
	protected ListEntity<Active> readList(Serializable seri) {
		// TODO Auto-generated method stub
		return	((ActiveList) seri);
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
        if (mCatalog == ActiveList.CATALOG_LASTEST) {
            setHasOptionsMenu(true);
        }
		super.initView();
        mListView.setOnItemLongClickListener(this);
        mListView.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getInstance().isLogin()) {
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    requestData(false);
                } else {
//                    UIHelper.showLoginActivity(getActivity());
                }
            }
        });
        if (AppContext.getInstance().isLogin()) {
//            UIHelper.sendBroadcastForNotice(getActivity());
        }
	}

    @Override
    protected void requestData(boolean refresh) {
        if (AppContext.getInstance().isLogin()) {
            mIsWatingLogin = false;
            super.requestData(refresh);
        } else {
            mIsWatingLogin = true;
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));
        }
    }
    
    @Override
    protected void sendRequestData() {
        OSChinaApi.getActiveList(AppContext.getInstance().getLoginUid(),
                mCatalog, mCurrentPage, mHandler);
    }
    
    @Override
    protected void onRefreshNetworkSuccess() {
//        if (AppContext.getInstance().isLogin()) {
//            if (0 == NoticeViewPagerFragment.sCurrentPage) {
//                NoticeUtils.clearNotice(Notice.TYPE_ATME);
//            } else if (1 == NoticeViewPagerFragment.sCurrentPage
//                    || NoticeViewPagerFragment.sShowCount[1] > 0) { // 如果当前显示的是评论页，则发送评论页已被查看的Http请求
//                NoticeUtils.clearNotice(Notice.TYPE_COMMENT);
//            } else {
//                NoticeUtils.clearNotice(Notice.TYPE_ATME);
//            }
//            UIHelper.sendBroadcastForNotice(getActivity());
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Active active = mAdapter.getItem(position);
//        if (active != null)
//            UIHelper.showActiveRedirect(view.getContext(), active);
    }  
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
        final Active active = mAdapter.getItem(position);
        if (active == null)
            return false;
//        String[] items = new String[] { getResources().getString(R.string.copy) };
//        final CommonDialog dialog = DialogHelper
//                .getPinterestDialogCancelable(getActivity());
//        dialog.setNegativeButton(R.string.cancle, null);
//        dialog.setItemsWithoutChk(items, new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                    int position, long id) {
//                dialog.dismiss();
                TDevice.copyTextToBoard(HTMLUtil.delHTMLTag(active.getMessage()));
//            }
//        });
//        dialog.show();
        return true;
	}
	
	@Override
	protected long getAutoRefreshTime() {
		// TODO Auto-generated method stub
        // 最新动态，即是好友圈
        if (mCatalog == ActiveList.CATALOG_LASTEST) {
            return 5 * 60;
        }
        return super.getAutoRefreshTime();
	}
}
