package com.example.oschina_client.viewpagerfragment;

import com.example.oschina_client.R;
import com.example.oschina_client.adapter.ViewPagerFragmentAdapter;
import com.example.oschina_client.base.BaseViewPagerFragment;
import com.example.oschina_client.fragment.DefaultFragment;


public class ExplorePagerFragment extends BaseViewPagerFragment {

	@Override
	protected void addPagetoAdapter(ViewPagerFragmentAdapter fragmentAdapter) {
		// TODO Auto-generated method stub
		String[] strings = getResources().getStringArray(R.array.choose_picture);
		
		fragmentAdapter.addPager(strings[0], DefaultFragment.class, null);
		fragmentAdapter.addPager(strings[1], DefaultFragment.class, null);
	}

}
