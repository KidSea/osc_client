package com.example.oschina_client.viewpagerfragment;

import android.os.Bundle;

import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ViewPagerFragmentAdapter;
import com.example.oschina_client.base.BaseListFragment;
import com.example.oschina_client.base.BaseViewPagerFragment;
import com.example.oschina_client.bean.TweetsList;
import com.example.oschina_client.fragment.DefaultFragment;



public class TweetPagerFragment extends BaseViewPagerFragment {

	@Override
	protected void addPagetoAdapter(ViewPagerFragmentAdapter fragmentAdapter) {
		// TODO Auto-generated method stub
		String[] titles = getActivity().getResources().getStringArray(
				R.array.tweets_viewpage_arrays);
		//添加page,并给fragment传入对应的bundle参数，在请求接口的时候用
		fragmentAdapter.addTab(titles[0], "",DefaultFragment.class, getBundle(TweetsList.CATALOG_LATEST));
		fragmentAdapter.addTab(titles[1], "",DefaultFragment.class, getBundle(TweetsList.CATALOG_HOT));
		fragmentAdapter.addTab(titles[2], "",DefaultFragment.class, getBundle(TweetsList.CATALOG_ME));
	}
	private Bundle getBundle(int newType) {
		Bundle bundle = new Bundle();
		bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, newType);
		bundle.putString("key", "我是动弹里的: " + newType);
		return bundle;
	}
}
