package com.example.oschina_client.base;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ViewPageFragmentAdapter;

import com.example.oschina_client.empty.EmptyLayout;
import com.example.oschina_client.widget.PagerSlidingTab;
import com.example.oschina_client.widget.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带有指针的碎片基类
 * 
 * @author yuxuehai
 * 
 */
public abstract class BaseViewPagerFragment extends Fragment {

	@InjectView(R.id.pager_tabstrip)
	protected PagerSlidingTabStrip mTabStrip; // ViewPager顶部的导航条

	@InjectView(R.id.pager)
	protected ViewPager mViewPager; // 展示内容用的滚动布局ViewPager
	protected ViewPageFragmentAdapter mTabsAdapter; // 封装了数据集合的ViewPager适配器
	@InjectView(R.id.error_layout)
	protected EmptyLayout mEmptyLayout;// 布局加载异常时, 显示的空布局.

	// 初始化布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 填充并返回一个公共的包含导航条和ViewPager的界面
		View view = inflater.inflate(R.layout.base_viewpage_fragment, null);
		ButterKnife.inject(this, view);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		// 封装adapter, 注意这里是继承的FragmentStatePagerAdapter,
		// 并且传入的是getChildFragmentManager()
		// 此处封装了PagerSlidingTabStrip, ViewPager, 在Adapter内部进行一系列的初始化.
		mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
				mTabStrip, mViewPager);

		setScreenPageLimit(mViewPager);

		// 通过ViewPageFragmentAdapter设置Tab选项及内容, 抽象方法, 由子类重写进行实现.
		addPagetoAdapter(mTabsAdapter);
	}



	// 设置viewpafer能够缓存的页数
	protected void setScreenPageLimit(ViewPager mViewPager) {

	}

	// 往adapter添加页数
	protected abstract void addPagetoAdapter(
			ViewPageFragmentAdapter fragmentAdapter);
}
