package com.example.oschina_client.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.base.ListBaseAdapter;
import com.example.oschina_client.bean.News;
import com.example.oschina_client.bean.NewsList;
import com.example.oschina_client.utils.StringUtils;

public class NewsAdapter extends ListBaseAdapter<News> {

	@SuppressLint("InflateParams")
	@Override
	protected View getRealView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null || convertView.getTag() == null) {
			convertView = getLayoutInflater(parent.getContext()).inflate(
					R.layout.list_cell_news, null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		// 1.标题部分
		News news = mDatas.get(position);
		vh.title.setText(news.getTitle());

		// 判断是否被浏览过
		if (AppContext.isOnReadedPostList(NewsList.PREF_READED_NEWS_LIST,
				news.getId() + "")) {
			vh.title.setTextColor(parent.getContext().getResources()
					.getColor(R.color.main_gray));
		} else {
			vh.title.setTextColor(parent.getContext().getResources()
					.getColor(R.color.main_black));
		}

		// 2.信息描述部分
		String description = news.getBody();
		vh.description.setVisibility(View.GONE);
		// 判断描述是否为空
		if (description != null && !StringUtils.isEmpty(description)) {
			vh.description.setVisibility(View.VISIBLE);
			vh.description.setText(description.trim());
		}

		vh.scource.setText(news.getAuthor());
		vh.time.setText(StringUtils.friendly_time(news.getPubDate()));
		if (StringUtils.isToday(news.getPubDate())) {
			vh.tip.setVisibility(View.VISIBLE);
		} else {
			vh.tip.setVisibility(View.GONE);
		}
		
		vh.comment_count.setText(news.getCommentCount() + "");
		
		return convertView;
	}

	static class ViewHolder {

		@InjectView(R.id.ll_title)
		TextView title;
		@InjectView(R.id.tv_description)
		TextView description;
		@InjectView(R.id.tv_time)
		TextView time;
		@InjectView(R.id.tv_source)
		TextView scource;
		@InjectView(R.id.tv_comment_count)
		TextView comment_count;
		@InjectView(R.id.iv_tip)
		ImageView tip;
		@InjectView(R.id.iv_link)
		ImageView link;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}