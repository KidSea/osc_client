package com.example.oschina_client.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.View;

import com.example.oschina_client.R;
import com.example.oschina_client.base.BaseActivity;
import com.example.oschina_client.base.BaseFragment;
import com.example.oschina_client.emoji.OnSendClickListener;
import com.example.oschina_client.fragment.NewsDetailFragment;

/**
 * 详情页 (包括:资讯、博客、软件、回答、动弹)
 * 
 * @author yuxuehai
 * 
 */
public class DetailActivity extends BaseActivity implements OnSendClickListener {

	public static final int DISPLAY_NEWS = 0;
	public static final int DISPLAY_BLOG = 1;
	public static final int DISPLAY_SOFTWARE = 2;
	public static final int DISPLAY_POST = 3;
	public static final int DISPLAY_TWEET = 4;
	public static final int DISPLAY_EVENT = 5;
	public static final int DISPLAY_TEAM_ISSUE_DETAIL = 6;
	public static final int DISPLAY_TEAM_DISCUSS_DETAIL = 7;
	public static final int DISPLAY_TEAM_TWEET_DETAIL = 8;
	public static final int DISPLAY_TEAM_DIAPY = 9;

	public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_detail;
	}

	@Override
	protected boolean hasBackButton() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.actionbar_title_detail;
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.init(savedInstanceState);
		int displayType = getIntent().getIntExtra(BUNDLE_KEY_DISPLAY_TYPE,
				DISPLAY_NEWS);
		BaseFragment fragment = null;
		int actionBarTitle = 0;
		switch (displayType) {
		case DISPLAY_NEWS:
			actionBarTitle = R.string.actionbar_title_news;
			fragment = new NewsDetailFragment();
			break;
		case DISPLAY_BLOG:
			actionBarTitle = R.string.actionbar_title_blog;
			break;
		case DISPLAY_SOFTWARE:
			break;
		case DISPLAY_POST:
			break;
		case DISPLAY_TWEET:
			break;
		case DISPLAY_EVENT:
			break;
		case DISPLAY_TEAM_ISSUE_DETAIL:
			break;
		case DISPLAY_TEAM_DISCUSS_DETAIL:
			break;
		case DISPLAY_TEAM_TWEET_DETAIL:
			break;
		case DISPLAY_TEAM_DIAPY:
			break;
		default:
			break;
		}

		setActionBarTitle(actionBarTitle);
		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.container, fragment);
		trans.commitAllowingStateLoss();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}

	@Override
	public void onClickSendButton(Editable str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClickFlagButton() {
		// TODO Auto-generated method stub

	}

}
