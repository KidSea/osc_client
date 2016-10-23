package com.example.oschina_client.ui;

import com.example.oschina_client.R;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class QuickOptionDialog extends Dialog implements
		android.view.View.OnClickListener {

	private ImageView mClose;
	
	private OnQuickOptionformClick mListener;
	//接口回掉
    public interface OnQuickOptionformClick {
        void onQuickOptionClick(int id);
    }

	protected QuickOptionDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public QuickOptionDialog(Context context) {
		this(context, R.style.quick_option_dialog);
	}

	@SuppressLint("InflateParams")
	private QuickOptionDialog(Context context, int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, defStyle);
		View contentView = getLayoutInflater().inflate(
				R.layout.dialog_quick_option, null);
		contentView.findViewById(R.id.ly_quick_option_text).setOnClickListener(
				this);
		contentView.findViewById(R.id.ly_quick_option_album)
				.setOnClickListener(this);
		contentView.findViewById(R.id.ly_quick_option_photo)
				.setOnClickListener(this);
		mClose = (ImageView) contentView.findViewById(R.id.iv_close);

		// 设置旋转动画
		Animation operationgAnim = AnimationUtils.loadAnimation(getContext(),
				R.anim.quick_option_close);
		LinearInterpolator lin = new LinearInterpolator();
		operationgAnim.setInterpolator(lin);

		mClose.startAnimation(operationgAnim);

		mClose.setOnClickListener(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 设置触摸事件
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// 当空白部分被触摸，消费触摸事件
				QuickOptionDialog.this.dismiss();
				return true;
			}
		});

		super.setContentView(contentView);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setGravity(Gravity.BOTTOM);
		
		WindowManager m = getWindow().getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = getWindow().getAttributes();
		p.width = d.getWidth();
		getWindow().setAttributes(p);
	}
    public void setOnQuickOptionformClickListener(OnQuickOptionformClick lis) {
        mListener = lis;
    }
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.ly_quick_option_text:

			break;
		case R.id.ly_quick_option_album:
			break;
		case R.id.ly_quick_option_photo:
			break;
		case R.id.iv_close:
			dismiss();
			break;

		default:
			break;
		}
		if (mListener != null) {
			mListener.onQuickOptionClick(id);
		}
		dismiss();
	}

}
