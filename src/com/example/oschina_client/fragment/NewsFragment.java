package com.example.oschina_client.fragment;

import java.io.InputStream;
import java.io.Serializable;

import android.view.View;
import android.widget.AdapterView;

import com.example.oschina_client.adapter.NewsAdapter;
import com.example.oschina_client.api.remote.OSChinaApi;
import com.example.oschina_client.base.BaseFragment;
import com.example.oschina_client.base.BaseListFragment;
import com.example.oschina_client.base.ListBaseAdapter;
import com.example.oschina_client.bean.ListEntity;
import com.example.oschina_client.bean.News;
import com.example.oschina_client.bean.NewsList;
import com.example.oschina_client.utils.XmlUtils;

public class NewsFragment extends BaseListFragment<News> {

	
	protected static final String TAG = NewsFragment.class.getSimpleName();
	private static final String CACHE_KEY_PREFIX = "newslist_";
	
	@Override
	protected NewsAdapter getListAdapter() {
		// TODO Auto-generated method stub
		return new NewsAdapter();
	}
	
	@Override
	protected String getCacheKeyPrefix() {
		// TODO Auto-generated method stub
		return CACHE_KEY_PREFIX + mCatalog;
	}
	
	@Override
	protected NewsList parseList(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		NewsList list = null;
		try {
			list = XmlUtils.toBean(NewsList.class, is);
		} catch (NullPointerException e) {
			// TODO: handle exception
			list = new NewsList();
		}
		
		return list;
	}
	
	@Override
	protected NewsList readList(Serializable seri) {
		// TODO Auto-generated method stub
		return ((NewsList) seri);
	}
	
	@Override
	protected void sendRequestData() {
		// TODO Auto-generated method stub
		/*    	// 方式一: 每次new AsyncHttpClient, 消耗大
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get("http://192.168.18.93:8080/oschina/news_list/page0.xml", new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, org.apache.http.Header[] arg1,
					byte[] arg2, Throwable arg3) {
			}

			@Override
			public void onSuccess(int arg0, org.apache.http.Header[] arg1,
					byte[] arg2) {
			}
		});
        
        // 方式二: 直接用封装了AsyncHttpClient的ApiHttpClient静态方法, 封装路径和拼接参数, 麻烦
        ApiHttpClient.getLocal("oschina/news_list/page2.xml", mHandler);*/
    	
    	// 方式三: (推荐)主线程 mCatalog = 1 资讯, mCatalog = 4 热点
        OSChinaApi.getNewsList(mCatalog, mCurrentPage, mHandler);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		News news = mAdapter.getItem(position);
		if(news != null){
			
		}
		super.onItemClick(parent, view, position, id);
	}
	
}
