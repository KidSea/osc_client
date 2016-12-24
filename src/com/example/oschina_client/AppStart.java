package com.example.oschina_client;




import java.io.File;

import org.kymjs.kjframe.http.KJAsyncTask;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.PreferenceHelper;

import com.example.oschina_client.api.ApiHttpClient;
import com.example.oschina_client.ui.MainActivity;
import com.example.oschina_client.utils.TDevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AppStart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//防止第三方跳转出现双实例
		Activity aty = AppManager.getActivity(MainActivity.class);
		if(aty != null && !aty.isFinishing()){
			finish();
		}
		
		final View view = View.inflate(this, R.layout.app_start, null);
		setContentView(view);
		
		//设置渐变展示
		AlphaAnimation ap = new AlphaAnimation(0.5f,1.0f);
		ap.setDuration(800);
		view.startAnimation(ap);
		ap.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				redirectTo();
			}
		});
		

	}

//    @Override
//    protected void onResume() {
//        super.onResume();
//        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
//                "first_install", -1);
//        int currentVersion = TDevice.getVersionCode();
//        if (cacheVersion < currentVersion) {
//            PreferenceHelper.write(this, "first_install", "first_install",
//                    currentVersion);
//            cleanImageCache();
//        }
//    }
//	
//	
//	private void cleanImageCache() {
//		// TODO Auto-generated method stub
//        final File folder = FileUtils.getSaveFolder("OSChina/imagecache");
//        KJAsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                for (File file : folder.listFiles()) {
//                    file.delete();
//                }
//            }
//        });
//	}

	protected void redirectTo() {
		// TODO Auto-generated method stub
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
	}
}