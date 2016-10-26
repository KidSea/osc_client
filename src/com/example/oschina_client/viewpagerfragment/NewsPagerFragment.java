package com.example.oschina_client.viewpagerfragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.oschina_client.R;


import com.example.oschina_client.adapter.ViewPageFragmentAdapter;
import com.example.oschina_client.base.BaseListFragment;
import com.example.oschina_client.base.BaseViewPagerFragment;
import com.example.oschina_client.bean.BlogList;
import com.example.oschina_client.bean.NewsList;
import com.example.oschina_client.fragment.DefaultFragment;
import com.example.oschina_client.fragment.NewsFragment;

/**
 * 资讯Viewpager页面
 * 
 * @author yuxuehai
 * 
 */
public class NewsPagerFragment extends BaseViewPagerFragment {

	// 重写了父类的 设置Tab选项及内容 方法, 根据当前页面的需求,
	// 通过ViewPageFragmentAdapter执行添加Tab操作, 添加一个Tab, 导航条就多一个选项,
	// 点击就可以跳转到指定Fragment
	@Override
	protected void addPagetoAdapter(ViewPageFragmentAdapter fragmentAdapter) {
		// TODO Auto-generated method stub
		String[] titles = getActivity().getResources().getStringArray(
				R.array.news_viewpage_arrays);

		// 参数介绍: (此处的添加条目的方式完全可以由自己决定, 以修改,拓展,更新方便为准)
		// title[0] 导航条的标题
		// "news" 普通的标签字符串, 这里没什么作用
		// NewsFragment.class 当前Tab对应的Fragment内容
		// getBundle(NewsList.CATALOG_ALL) : 通过getBundle方法构建一个Bundle对象,
		// 用于传输给新打开的Fragment
		// NewsList.CATALOG_ALL 表示在新界面里需要加载的是[资讯]数据, 因为打开的都是NewsFragment对象,
		// 只是数据不同
		// NewsList.CATALOG_WEEK 表示在新界面里需要加载的是[热点]数据, 因为打开的都是NewsFragment对象,
		// 只是数据不同
		// 作用类似于打开新的Activity时, intent.putExtra设置参数, 在新界面通过getIntent获取参数.

		// 添加page,并给fragment传入对应的bundle参数，在请求接口的时候用

		// 资讯
		fragmentAdapter.addTab(titles[0], "news", NewsFragment.class,
				getBundle(NewsList.CATALOG_ALL));
		// 热点
		fragmentAdapter.addTab(titles[1], "news_week", NewsFragment.class,
				getBundle(NewsList.CATALOG_WEEK));
		// 博客
		fragmentAdapter.addTab(titles[2], "latest_blog", DefaultFragment.class,
				getBundle(BlogList.CATALOG_LATEST));
		// 推荐
		fragmentAdapter.addTab(titles[3], "recommend_blog",
				DefaultFragment.class, getBundle(BlogList.CATALOG_RECOMMEND));
	}

	private Bundle getBundle(int newType) {
		Bundle bundle = new Bundle();
		bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, newType);
		bundle.putString("key", "我是综合里的: " + newType);
		return bundle;
	}

	@Override
	protected void setScreenPageLimit(ViewPager mViewPager) {
		// TODO Auto-generated method stub
		mViewPager.setOffscreenPageLimit(3);
	}
	/**
	 * 基类会根据不同的catalog展示相应的数据
	 * 
	 * @param catalog
	 *            要显示的数据类别
	 * @return
	 */
	private Bundle getBundle(String catalog) {
		Bundle bundle = new Bundle();
		// bundle.putString(BlogFragment.BUNDLE_BLOG_TYPE, catalog);
		bundle.putString("key", "我是综合里的: " + catalog);
		return bundle;
	}
}
