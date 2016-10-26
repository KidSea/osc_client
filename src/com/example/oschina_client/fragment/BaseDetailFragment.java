package com.example.oschina_client.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oschina_client.AppContext;
import com.example.oschina_client.R;
import com.example.oschina_client.base.BaseFragment;


public class BaseDetailFragment extends BaseFragment {
	public static final String INTENT_ACTION_COMMENT_CHANGED = "INTENT_ACTION_COMMENT_CHAGED";

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@SuppressLint("ViewHolder")
	private static class MenuAdapter extends BaseAdapter {

		public void setFavorite(boolean favorite) {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater.from(parent.getContext()).inflate(
					R.layout.list_cell_popup_menu, null);
			TextView name = (TextView) convertView.findViewById(R.id.tv_name);

			int iconResId = 0;
			if (position == 0) {
				name.setText(parent.getResources().getString(
						R.string.detail_menu_for_share));
				iconResId = R.drawable.abc_ic_menu_moreoverflow_normal_holo_dark;
			} else if (position == 1) {
				name.setText(parent.getResources().getString(
						R.string.detail_menu_for_report));
				iconResId = R.drawable.abc_ic_menu_moreoverflow_normal_holo_dark;
			}
            Drawable drawable = AppContext.resources().getDrawable(iconResId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            name.setCompoundDrawables(drawable, null, null, null);			
			return convertView;
		}

	}
}
