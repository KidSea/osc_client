package com.example.oschina_client.ui;

import com.example.oschina_client.R;
import com.example.oschina_client.fragment.DefaultFragment;
import com.example.oschina_client.fragment.ExploreFragment;
import com.example.oschina_client.viewpagerfragment.ExplorePagerFragment;
import com.example.oschina_client.viewpagerfragment.NewsPagerFragment;
import com.example.oschina_client.viewpagerfragment.TweetPagerFragment;


/**
 * 主界面枚举类
 * @author yuxuehai
 *
 */
public enum MainTab {
	NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
			NewsPagerFragment.class),

	TWEET(1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
			TweetPagerFragment.class),

	QUICK(2, R.string.main_tab_name_quick, R.drawable.tab_icon_new,
			null),

	EXPLORE(3, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
			ExploreFragment.class),
			
	ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
			DefaultFragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
