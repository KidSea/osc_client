package com.example.oschina_client.base;

import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.ButterKnife;

import com.example.oschina_client.AppManager;
import com.example.oschina_client.R;
import com.example.oschina_client.dialog.CommonToast;
import com.example.oschina_client.interf.BaseViewInterface;
import com.example.oschina_client.utils.TDevice;

/**
 * BaseActionBar Activity基类
 * 
 * @author yuxuehai
 * 
 */
public class BaseActivity extends ActionBarActivity implements
		BaseViewInterface, OnClickListener {
	public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";

	private boolean _isVisible;

	protected LayoutInflater mInflater;
	protected ActionBar mActionBar;
	private TextView mTvActionTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		if (!hasActionBar()) {

		}
		onBeforeSetContentLayout();
		if (getLayoutID() != 0) {
			setContentView(getLayoutID());
		}
		mActionBar = getSupportActionBar();
		mInflater = getLayoutInflater();

		if (hasActionBar()) {
			initActionBar(mActionBar);
		}

		// 通过注解绑定控件
		ButterKnife.inject(this);

		init(savedInstanceState);
		initView();
		initData();
		_isVisible = true;

	}

	protected void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	protected void initActionBar(ActionBar actionBar) {
		// TODO Auto-generated method stub
		if (actionBar == null) {
			return;
		}
		if (hasBackButton()) {
			// 让ActionBar自定义内容
			mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			int layoutRes = getAtionBarCustomView();

			// ------------------------------------------------- 创建自定义布局 ↓
			View view = inflateView(layoutRes == 0 ? R.layout.actionbar_custom_backtitle
					: layoutRes);
			View back = view.findViewById(R.id.btn_back);
			if (back == null) {
				throw new IllegalArgumentException(
						"can not find R.id.btn_back in customView");
			}

			back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TDevice.hideSoftKeyboard(getCurrentFocus());// 隐藏软键盘
					onBackPressed();// 按下了返回键
				}
			});

			mTvActionTitle = (TextView) view
					.findViewById(R.id.tv_actionbar_title);

			if (mTvActionTitle == null) {
				throw new IllegalArgumentException(
						"can not find R.id.tv_actionbar_title in customView");
			}
			int titleRes = getActionBarTitle();
			if (titleRes != 0) {
				mTvActionTitle.setText(titleRes);
			}
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			// ------------------------------------------------- 创建自定义布局 ↑
			// 设置自定义内容
			actionBar.setCustomView(view, params);
			View spinner = actionBar.getCustomView().findViewById(R.id.spinner);
			if (haveSpinner()) {
				spinner.setVisibility(View.VISIBLE);
			} else {
				spinner.setVisibility(View.GONE);
			}

		} else {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
			actionBar.setDisplayUseLogoEnabled(false);
			int titleRes = getActionBarTitle();
			if (titleRes != 0) {
				actionBar.setTitle(titleRes);
			}
		}
	}

	protected Spinner getSpinner() {
		return (Spinner) mActionBar.getCustomView().findViewById(R.id.spinner);
	}

	public void setActionBarTitle(int resId) {
		if (resId != 0) {
			setActionBarTitle(getString(resId));
		}
	}

	public void setActionBarTitle(String title) {
		if (StringUtils.isEmpty(title)) {
			title = getString(R.string.app_name);
		}
		if (hasActionBar() && mActionBar != null) {
			if (mTvActionTitle != null) {
				mTvActionTitle.setText(title);
			}
			mActionBar.setTitle(title);
		}
	}

	protected boolean haveSpinner() {
		// TODO Auto-generated method stub
		return false;
	}

	protected int getActionBarTitle() {
		// TODO Auto-generated method stub
		return R.string.app_name;
	}

	protected View inflateView(int resId) {
		return mInflater.inflate(resId, null);
	}

	protected int getAtionBarCustomView() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected boolean hasBackButton() {
		// TODO Auto-generated method stub
		return false;
	}

	protected int getLayoutID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		TDevice.hideSoftKeyboard(getCurrentFocus());// 隐藏键盘
		ButterKnife.reset(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	protected boolean hasActionBar() {
		// TODO Auto-generated method stub
		return true;
	}

	protected void onBeforeSetContentLayout() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
    public void showToast(int msgResid, int icon, int gravity) {
        showToast(getString(msgResid), icon, gravity);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        // setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
