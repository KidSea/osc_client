package com.example.oschina_client.adapter;

import java.net.ContentHandler;
import java.util.ArrayList;

import com.example.oschina_client.R;
import com.example.oschina_client.widget.PagerSlidingTabStrip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 页面适配器
 * 
 * @author yuxuehai
 * 
 */
@SuppressLint("Recycle")
public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

	private final Context mContext;
	protected PagerSlidingTabStrip mPagerStrip;

	private final ViewPager mViewPager;
	private final ArrayList<ViewPageInfo> mTabs = new ArrayList<ViewPageInfo>();

	public ViewPagerFragmentAdapter(FragmentManager fm,
			PagerSlidingTabStrip pageStrip, ViewPager pager) {
		super(fm);
		// TODO Auto-generated constructor stub
		mContext = pager.getContext();
		mPagerStrip = pageStrip;
		mViewPager = pager;
		mViewPager.setAdapter(this);
		mPagerStrip.setViewPager(mViewPager);
	}

	/**
	 * 增加一页
	 * 
	 * @param title
	 * @param clazz
	 * @param bundle
	 */
	public void addTab(String title, String tag, Class<?> clazz, Bundle bundle) {
		ViewPageInfo info = new ViewPageInfo(title, tag, clazz, bundle);
		addFragment(info);
	}

	public void addAlltab(ArrayList<ViewPageInfo> mTabs){
		for(ViewPageInfo viewPageInfo : mTabs){
			addFragment(viewPageInfo);
		}
	}
	
	private void addFragment(ViewPageInfo info) {
		// TODO Auto-generated method stub
		if (info == null) {
			return;
		}
		// tab title
		View v = LayoutInflater.from(mContext).inflate(
				R.layout.base_viewpage_fragment_tab_item, null,false);
		TextView title = (TextView) v.findViewById(R.id.tab_title);
		title.setText(info.title);
		mPagerStrip.addTab(v);
		
		mTabs.add(info);
		notifyDataSetChanged();
	}

	/**
	 * 移除所有的tab
	 */
	public void removeAll(){
		if(mTabs.isEmpty()){
			return;
		}
		mPagerStrip.removeAllTab();
		mTabs.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		ViewPageInfo info = mTabs.get(position);

		return Fragment.instantiate(mContext, info.clss.getName(),
				info.args);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTabs.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mTabs.get(position).title;
	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return PagerAdapter.POSITION_NONE;
	}


}
