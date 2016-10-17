package com.example.oschina_client.base;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ViewPagerFragmentAdapter;
import com.example.oschina_client.widget.PagerSlidingTab;

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
	protected PagerSlidingTab mTabStrip; // ViewPager顶部的导航条

	@InjectView(R.id.pager)
	protected ViewPager mViewPager;
	protected ViewPagerFragmentAdapter mTabsAdapter; // 封装了数据集合的ViewPager适配器

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
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 1.封装adapter, 注意这里是继承的FragmentPagerAdapter,
		// 并且传入的是getChildFragmentManager()
		mTabsAdapter = new ViewPagerFragmentAdapter(getActivity(), getChildFragmentManager());
		
		//2.添加page页
		addPagetoAdapter(mTabsAdapter);
		
		//3.设置adapter
		mViewPager.setAdapter(mTabsAdapter);
		//4.绑定指针跟viewpager
		mTabStrip.setViewPager(mViewPager);
		
		setScreenPageLimit(mViewPager);
	}

	// 设置viewpafer能够缓存的页数
	protected void setScreenPageLimit(ViewPager mViewPager) {
		mViewPager.setOffscreenPageLimit(this.mViewPager.getAdapter()
				.getCount() - 1);
	}

	// 往adapter添加页数
	protected abstract void addPagetoAdapter(
			ViewPagerFragmentAdapter fragmentAdapter);
}
