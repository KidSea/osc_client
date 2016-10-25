package com.example.oschina_client.ui;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.oschina_client.R;
import com.example.oschina_client.base.BaseActivity;
import com.example.oschina_client.base.BaseFragment;
import com.example.oschina_client.bean.SimpleBackPage;
import com.example.oschina_client.emoji.OnSendClickListener;

public class SimpleBackActivity extends BaseActivity implements
		OnSendClickListener {

	public static final String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
	public static final String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
	private static final String TAG = "FLAG_TAG";
	protected int mPageValue = -1;

	protected WeakReference<Fragment> mFragment;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_simple_fragment;
	}

	@Override
	protected boolean hasBackButton() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.init(savedInstanceState);
		if (mPageValue == -1) {
			mPageValue = getIntent().getIntExtra(BUNDLE_KEY_PAGE, 0);
		}
		initFromIntent(mPageValue, getIntent());
	}

	protected void initFromIntent(int pageValue, Intent data) {
		// TODO Auto-generated method stub
		if (data == null) {
			throw new RuntimeException(
					"you must provide a page info to display");
		}
		SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
		if (page == null) {
			throw new IllegalArgumentException("can not find page by value:"
					+ pageValue);
		}

		setActionBarTitle(page.getTitle());

		try {
			Fragment fragment = (Fragment) page.getClz().newInstance();

			Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);

			if (args != null) {
				fragment.setArguments(args);
			}

			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.replace(R.id.container, fragment, TAG);
			trans.commitAllowingStateLoss();

			mFragment = new WeakReference<Fragment>(fragment);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IllegalArgumentException(
					"generate fragment error. by value:" + pageValue);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// if (mFragment.get() instanceof TweetsFragment) {
		// setActionBarTitle("话题");
		// }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		// case R.id.public_menu_send:
		// if (mFragment.get() instanceof TweetsFragment) {
		// sendTopic();
		// } else {
		// return super.onOptionsItemSelected(item);
		// }
		// break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// if (mFragment.get() instanceof TweetsFragment) {
		// getMenuInflater().inflate(R.menu.pub_topic_menu, menu);
		// }
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 发送话题
	 */
	private void sendTopic() {
		// Bundle bundle = new Bundle();
		// bundle.putInt(TweetPubFragment.ACTION_TYPE,
		// TweetPubFragment.ACTION_TYPE_TOPIC);
		// bundle.putString("tweet_topic", "#"
		// + ((TweetsFragment) mFragment.get()).getTopic() + "# ");
		// UIHelper.showTweetActivity(this, SimpleBackPage.TWEET_PUB, bundle);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mFragment != null && mFragment.get() != null
				&& mFragment.get() instanceof BaseFragment) {
			BaseFragment bf = (BaseFragment) mFragment.get();
			if (!bf.onBackPressed()) {
				super.onBackPressed();
			}
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.ACTION_DOWN
				&& mFragment.get() instanceof BaseFragment) {
			((BaseFragment) mFragment.get()).onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}
	
    @Override
    public void initView() {}

    @Override
    public void initData() {}
    
	@Override
	public void onClickSendButton(Editable str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClickFlagButton() {
		// TODO Auto-generated method stub
		
	}
}
