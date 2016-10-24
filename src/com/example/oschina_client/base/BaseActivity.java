package com.example.oschina_client.base;

import com.example.oschina_client.AppManager;
import com.example.oschina_client.interf.BaseViewInterface;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * BaseActionBar Activity基类
 * @author yuxuehai
 *
 */
public class BaseActivity extends ActionBarActivity implements BaseViewInterface, OnClickListener{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		if(!hasActionBar()){
			
		}
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	protected boolean hasActionBar() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
