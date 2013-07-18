package com.kmb.origami.view;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kmb.origami.R;
import com.kmb.origami.controller.CacheManager;

@SuppressLint("HandlerLeak")
public class SplashActivity extends Activity {

	private SharedPreferences mImageNumPreferences; // 이미지 몇개 저장
	private SharedPreferences mMaxStagePreferences; // 몇 스테지 까지 깻는지
	private SharedPreferences mMaxOfSingleStagePreferences; // 한 스테이지에 대한 최대 몇
															// 번째 까지 성공했는지

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		mMaxStagePreferences = getSharedPreferences("MaxStageInfo",
				MODE_PRIVATE);
		mMaxOfSingleStagePreferences = getSharedPreferences("MaxOfSingleStage",
				MODE_PRIVATE);
		mImageNumPreferences = getSharedPreferences("ImageNum", MODE_PRIVATE);
		
		if( !(mMaxStagePreferences.contains("maxStage")) && !(mMaxOfSingleStagePreferences.contains("1"))){
			SharedPreferences.Editor editor = mMaxStagePreferences.edit();
			// save the returned auth_token into
			// the SharedPreferences
			editor.putInt("maxStage", 1);
			editor.commit();
			
			SharedPreferences.Editor editor2 = mMaxOfSingleStagePreferences.edit();
			// save the returned auth_token into
			// the SharedPreferences
			editor2.putInt("1", 1);
			editor2.commit();
		}

		int imageNum = 0;
		int flag = 0;
		while (flag != 50) {
			Bitmap result = null;
			try {
				result = CacheManager.retrieveDataForCollection(
						getApplicationContext(), String.valueOf(imageNum + 1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flag++;

			if (result == null) {
				continue;
			} else {
				imageNum++;
			}
		}

		if (imageNum == 0) {
			SharedPreferences.Editor editor = mImageNumPreferences.edit();
			// save the returned auth_token into
			// the SharedPreferences
			editor.putInt("numOfImage", 0);
			editor.commit();
		} else {
			SharedPreferences.Editor editor = mImageNumPreferences.edit();
			// save the returned auth_token into
			// the SharedPreferences
			editor.putInt("numOfImage", imageNum);
			editor.commit();
		}

		Log.d("numOfImage in Start", String.valueOf(imageNum));

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				startActivity(new Intent(SplashActivity.this,
						IndexActivity.class));
				finish();
			}
		};

		Log.d("SplashActivity", "sendEmptyMessageDelayed");

		handler.sendEmptyMessageDelayed(0, 3000);
	}
}
