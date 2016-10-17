package com.example.oschina_client.ui;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.oschina_client.R;

import com.example.oschina_client.bean.Notice;
import com.example.oschina_client.interf.BaseViewInterface;
import com.example.oschina_client.utils.ToastUtil;
import com.example.oschina_client.widget.MyFragmentTabHost;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost.TabContentFactory;

@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,
		OnTabChangeListener, BaseViewInterface, View.OnClickListener,
		OnTouchListener {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@InjectView(android.R.id.tabhost)
	public MyFragmentTabHost mTabHost;

	public static Notice mNotice;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@InjectView(R.id.quick_option_iv)
	View mAddBt;

	// @OnClick({R.id.quick_option_iv,R.id.text})
	// public void onMyClick(View view){
	//
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		initView();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	public void initView() {
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// 初始化底部FragmentTabHost
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			mTabHost.getTabWidget().setShowDividers(0);
		}

		initTabs();

		// 中间按键图片触发
		mAddBt.setOnClickListener(this);
		mAddBt.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});

		mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);



	}

	private void initTabs() {
		MainTab[] tabs = MainTab.values();
		final int size = tabs.length;
		for (int i = 0; i < size; i++) {
			// 找到每一个枚举的Fragment对象
			MainTab mainTab = tabs[i];

			// 1. 创建一个新的选项卡
			TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));

			// ------------------------------------------------- 自定义选项卡 ↓
			View indicator = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.tab_indicator, null);
			TextView title = (TextView) indicator.findViewById(R.id.tab_title);
			Drawable drawable = this.getResources().getDrawable(
					mainTab.getResIcon());
			
			//给textview设置左上右下
			title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
					null);
			
			if (i == 2) {
				indicator.setVisibility(View.INVISIBLE);
				mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
			}
			title.setText(getString(mainTab.getResName()));
			// 自定义选项卡
			tab.setIndicator(indicator);
			tab.setContent(new TabContentFactory() {

				@Override
				public View createTabContent(String tag) {
					// TODO Auto-generated method stub
					return new View(MainActivity.this);
				}
			});
			// ------------------------------------------------- 以上 ↑

			Bundle bundle = new Bundle();
			bundle.putString("key",
					"content: " + getString(mainTab.getResName()));
			// 2. 把新的选项卡添加到TabHost中
			mTabHost.addTab(tab, mainTab.getClz(), bundle);

			mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
		}
	}

	@Override
	public void initData() {

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		// 设置显示为标准模式, 还有NAVIGATION_MODE_LIST列表模式, NAVIGATION_MODE_TABS选项卡模式.
		// 参见ApiDemos
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// 设置显示标题
		actionBar.setDisplayShowTitleEnabled(true);
		// 设置标题
		actionBar.setTitle(mTitle);

		// 1.通过设置自定义内容VIew
		// actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_CUSTOM);
		// actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setDisplayHomeAsUpEnabled(true);
		// View view = View.inflate(this, R.layout.layout_actionbar, null);
		// actionBar.setCustomView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.search:
			ToastUtil.showToast(this, "Search");
			break;
		case R.id.share:
			ToastUtil.showToast(this, "share");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// 弹出对话框
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub

	}
}
