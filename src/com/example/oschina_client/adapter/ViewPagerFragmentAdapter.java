package com.example.oschina_client.adapter;

import java.net.ContentHandler;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 页面适配器
 * 
 * @author yuxuehai
 * 
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

	private Context context;

	private ArrayList<String> mTabTitles;
	private ArrayList<FragmentInfo> mFrgmentInfo;

	public ViewPagerFragmentAdapter(Context context, FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.context = context;
		mTabTitles = new ArrayList<String>();
		mFrgmentInfo = new ArrayList<ViewPagerFragmentAdapter.FragmentInfo>();
	}
	
	/**
	 * 增加一页
	 * @param title
	 * @param clazz
	 * @param bundle
	 */
	public void addPager(String title,Class<?> clazz,Bundle bundle) {
		mTabTitles.add(title);
		mFrgmentInfo.add(new FragmentInfo(clazz, bundle));
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		FragmentInfo info = mFrgmentInfo.get(position);
		
		return Fragment.instantiate(context, info.getClazz().getName(),info.getBundle());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTabTitles.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mTabTitles.get(position);
	}

	// frgmentinfo 封装每个内容信息

	public class FragmentInfo {

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Bundle getBundle() {
			return bundle;
		}

		public void setBundle(Bundle bundle) {
			this.bundle = bundle;
		}

		private Class<?> clazz;
		private Bundle bundle;

		public FragmentInfo(Class<?> clazz, Bundle bundle) {
			super();
			this.clazz = clazz;
			this.bundle = bundle;
		}
	}

}
