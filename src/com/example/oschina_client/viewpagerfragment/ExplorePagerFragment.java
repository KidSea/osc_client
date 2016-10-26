package com.example.oschina_client.viewpagerfragment;

import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ViewPageFragmentAdapter;

import com.example.oschina_client.base.BaseViewPagerFragment;

import com.example.oschina_client.fragment.DefaultFragment;


public class ExplorePagerFragment extends BaseViewPagerFragment {

	@Override
	protected void addPagetoAdapter(ViewPageFragmentAdapter fragmentAdapter) {
		// TODO Auto-generated method stub
		String[] strings = getResources().getStringArray(R.array.choose_picture);
		
		fragmentAdapter.addTab(strings[0], "",DefaultFragment.class, null);
		fragmentAdapter.addTab(strings[1], "",DefaultFragment.class, null);
	}



}
