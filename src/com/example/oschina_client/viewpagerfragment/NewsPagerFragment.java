package com.example.oschina_client.viewpagerfragment;

import android.os.Bundle;

import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ViewPagerFragmentAdapter;
import com.example.oschina_client.base.BaseListFragment;
import com.example.oschina_client.base.BaseViewPagerFragment;
import com.example.oschina_client.bean.BlogList;
import com.example.oschina_client.bean.NewsList;
import com.example.oschina_client.fragment.DefaultFragment;


public class NewsPagerFragment extends BaseViewPagerFragment {

	@Override
	protected void addPagetoAdapter(ViewPagerFragmentAdapter fragmentAdapter) {
		// TODO Auto-generated method stub
		String[] titles = getActivity().getResources().getStringArray(
				R.array.news_viewpage_arrays);
		//添加page,并给fragment传入对应的bundle参数，在请求接口的时候用
		fragmentAdapter.addPager(titles[0], DefaultFragment.class, getBundle(NewsList.CATALOG_ALL));
		fragmentAdapter.addPager(titles[1], DefaultFragment.class, getBundle(NewsList.CATALOG_WEEK));
		fragmentAdapter.addPager(titles[2], DefaultFragment.class, getBundle(BlogList.CATALOG_LATEST));
		fragmentAdapter.addPager(titles[3], DefaultFragment.class, getBundle(BlogList.CATALOG_RECOMMEND));
	}
	
	private Bundle getBundle(int newType) {
		Bundle bundle = new Bundle();
		bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, newType);
		bundle.putString("key", "我是综合里的: " + newType);
		return bundle;
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
