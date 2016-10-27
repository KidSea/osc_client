package com.example.oschina_client.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.api.remote.OSChinaApi;
import com.example.oschina_client.bean.Comment;
import com.example.oschina_client.bean.CommentList;
import com.example.oschina_client.bean.Entity;
import com.example.oschina_client.bean.FavoriteList;
import com.example.oschina_client.bean.News;
import com.example.oschina_client.bean.News.Relative;
import com.example.oschina_client.bean.NewsDetail;
import com.example.oschina_client.emoji.OnSendClickListener;
import com.example.oschina_client.empty.EmptyLayout;
import com.example.oschina_client.ui.DetailActivity;
import com.example.oschina_client.utils.StringUtils;
import com.example.oschina_client.utils.TDevice;
import com.example.oschina_client.utils.UIHelper;
import com.example.oschina_client.utils.URLsUtils;
import com.example.oschina_client.utils.XmlUtils;

public class NewsDetailFragment extends BaseDetailFragment implements
		OnSendClickListener {

	protected static final String TAG = NewsDetailFragment.class
			.getSimpleName();
	private static final String NEWS_CACHE_KEY = "news_";
	@InjectView(R.id.tv_title)
	TextView mTvTitle;
	@InjectView(R.id.tv_source)
	TextView mTvSource;
	@InjectView(R.id.tv_time)
	TextView mTvTime;

	private int mNewsId;
	private News mNews;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_news_detail, container,
				false);
		mCommentCount = getActivity().getIntent().getIntExtra("comment_count",
				0);
		mNewsId = getActivity().getIntent().getIntExtra("news_id", 0);
		ButterKnife.inject(this, view);
		initViews(view);
		return view;

	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		mEmptyLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
		// ((DetailActivity) getActivity()).toolFragment
		// .setCommentCount(mCommentCount);
		mWebView = (WebView) view.findViewById(R.id.webview);
		UIHelper.initWebView(mWebView);
	}

	@Override
	protected String getCacheKey() {
		// TODO Auto-generated method stub
		return new StringBuilder(NEWS_CACHE_KEY).append(mNewsId).toString();
	}

	@Override
	protected void sendRequestData() {
		// TODO Auto-generated method stub
		mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
		OSChinaApi.getNewsDetail(mNewsId, mHandler);
	}

	@Override
	protected Entity parseData(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		return XmlUtils.toBean(NewsDetail.class, is).getNews();
	}

	@Override
	protected Entity readData(Serializable seri) {
		// TODO Auto-generated method stub
		return (News) seri;
	}

	@Override
	protected void onCommentChanged(int opt, int id, int catalog,
			boolean isBlog, Comment comment) {
		// TODO Auto-generated method stub
		if (id == mNewsId && catalog == CommentList.CATALOG_NEWS && !isBlog) {
			if (Comment.OPT_ADD == opt && mNews != null) {
				mNews.setCommentCount(mNews.getCommentCount() + 1);
			}
		}
	}

	@Override
	protected void executeOnLoadDataSuccess(Entity result) {
		// TODO Auto-generated method stub
		mNews = (News) result;
		fillUI();
		fillWebViewBody();
		((DetailActivity) getActivity()).setCommentCount(mNews
				.getCommentCount());
	}

	private void fillWebViewBody() {
		// TODO Auto-generated method stub
		StringBuffer body = new StringBuffer();
		body.append(UIHelper.setHtmlCotentSupportImagePreview(mNews.getBody()));

		body.append(UIHelper.WEB_STYLE).append(UIHelper.WEB_LOAD_IMAGES);

		// 更多关于软件的信息
		String softwareName = mNews.getSoftwareName();
		String softwareLink = mNews.getSoftwareLink();
		if (!StringUtils.isEmpty(softwareName)
				&& !StringUtils.isEmpty(softwareLink)) {
			body.append(String
					.format("<div id='oschina_software' style='margin-top:8px;color:#FF0000;font-weight:bold'>更多关于:&nbsp;<a href='%s'>%s</a>&nbsp;的详细信息</div>",
							softwareLink, softwareName));
		}

		// 相关新闻
		// 相关新闻
		if (mNews != null && mNews.getRelatives() != null
				&& mNews.getRelatives().size() > 0) {
			String strRelative = "";
			for (Relative relative : mNews.getRelatives()) {
				strRelative += String.format(
						"<a href='%s' style='text-decoration:none'>%s</a><p/>",
						relative.url, relative.title);
			}
			body.append("<p/><div style=\"height:1px;width:100%;background:#DADADA;margin-bottom:10px;\"/>"
					+ String.format("<br/> <b>相关资讯</b> <div><p/>%s</div>",
							strRelative));
		}
		body.append("<br/>");

		try {
			System.out.println("save begin! :" + mNewsId);
			File file = new File(Environment.getExternalStorageDirectory(),
					String.format("/OSChina/html/%d.html", mNewsId));
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(body.toString().getBytes());
			fos.close();
			System.out.println("save success! :" + file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (mWebView != null) {
			mWebView.loadDataWithBaseURL(null, body.toString(), "text/html",
					"utf-8", null);
		}
	}

	private void fillUI() {
		// TODO Auto-generated method stub
		mTvTitle.setText(mNews.getTitle());
		mTvSource.setText(mNews.getAuthor());
		mTvSource.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		mTvTime.setText(StringUtils.friendly_time(mNews.getPubDate()));
		notifyFavorite(mNews.getFavorite() == 1);
	}

	@Override
	protected void onFavoriteChanged(boolean flag) {
		mNews.setFavorite(flag ? 1 : 0);
		saveCache(mNews);
	}

	@Override
	protected int getFavoriteTargetId() {
		return mNews != null ? mNews.getId() : -1;
	}

	@Override
	protected int getFavoriteTargetType() {
		return mNews != null ? FavoriteList.TYPE_NEWS : -1;
	}

	@Override
	protected String getShareTitle() {
		return mNews != null ? mNews.getTitle()
				: getString(R.string.share_title_news);
	}

	@Override
	protected String getShareContent() {
		return mNews != null ? StringUtils.getSubString(0, 55,
				getFilterHtmlBody(mNews.getBody())) : "";
	}

	@Override
	protected String getShareUrl() {
		return mNews != null ? URLsUtils.URL_MOBILE + "news/" + mNews.getId()
				: null;
	}

	@Override
	public int getCommentCount() {
		// TODO Auto-generated method stub
		return mNews.getCommentCount();
	}

	@Override
	public void onClickSendButton(Editable str) {
		// TODO Auto-generated method stub
		if (!TDevice.hasInternet()) {
			AppContext.showToastShort(R.string.tip_network_error);
			return;
		}
		if (!AppContext.getInstance().isLogin()) {
			// UIHelper.showLoginActivity(getActivity());
			return;
		}
		if (TextUtils.isEmpty(str)) {
			AppContext.showToastShort(R.string.tip_comment_content_empty);
			return;
		}
		// showWaitDialog(R.string.progress_submit);
		OSChinaApi.publicComment(CommentList.CATALOG_NEWS, mNewsId, AppContext
				.getInstance().getLoginUid(), str.toString(), 0,
				mCommentHandler);
	}

	@Override
	public void onClickFlagButton() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onclickWriteComment() {
		super.onclickWriteComment();
		// if (mNews != null)
		// UIHelper.showComment(getActivity(), mNewsId,
		// CommentList.CATALOG_NEWS);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.refresh_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		sendRequestData();
		return super.onOptionsItemSelected(item);
	}

}
