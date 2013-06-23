package com.kmb.origami.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kmb.origami.R;

@SuppressLint("HandlerLeak")
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				startActivity(new Intent(SplashActivity.this,
						IndexFirstActivity.class));
				finish();
			}
		};

		Log.d("SplashActivity", "sendEmptyMessageDelayed");

		handler.sendEmptyMessageDelayed(0, 3000);
	}
}
