package com.kmb.origami.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.devsmart.android.ui.HorizontalListView;
import com.kmb.origami.R;
import com.kmb.origami.model.SingleItem;
import com.kmb.origami.model.SingleItemAdapter;
import com.kmb.origami.model.StageItem;
import com.kmb.origami.model.StageItemAdapter;

public class MainActivity extends Activity {

	private int mMaxStage = 1;

	private SharedPreferences mPreferences;

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPreferences = getSharedPreferences("Auth", MODE_PRIVATE);

		if (mPreferences.contains("mMaxStage")) {
			mMaxStage = mPreferences.getInt("mMaxStage", 1);
		} else {
			SharedPreferences.Editor editor = mPreferences.edit();
			// save the returned auth_token into
			// the SharedPreferences
			editor.putInt("Auth", mMaxStage);
			editor.commit();
		}

		leftCloud = (ImageView) findViewById(R.id.cloud_left);
		middleCloud = (ImageView) findViewById(R.id.cloud_middle);
		rightCloud = (ImageView) findViewById(R.id.cloud_right);

		startAnimation(leftCloud, middleCloud, rightCloud);
	}

	public void mainViewBtn(View v) {

		switch (v.getId()) {
		case R.id.main_single_button:
			buildDialog("stage", "single");
			break;

		case R.id.main_theme_button:
			buildDialog("stage", "theme");
			break;
		case R.id.main_boast_button:
			Intent changeViewIntent = new Intent(this, BoastActivity.class);
			startActivity(changeViewIntent);
			break;
		}
	}

	private class stageItemListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parentView, View v,
				int position, long id) {

			StageItem selectedItem = (StageItem) parentView
					.getItemAtPosition(position);
			Log.d("id", String.valueOf(selectedItem.getId()));

			buildDialog("SingleItem", "single");

		}
	}

	private class singleItemListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parentView, View v,
				int position, long id) {

			SingleItem selectedItem = (SingleItem) parentView
					.getItemAtPosition(position);
			Log.d("id", String.valueOf(selectedItem.getId()));

			Intent startFoldingIntent = new Intent(getApplicationContext(),
					PlayActivity.class);
			startFoldingIntent.putExtra("level", selectedItem.getId());
			startActivity(startFoldingIntent);

		}
	}

	public static void startAnimation(ImageView leftCloud,
			ImageView middleCloud, ImageView rightCloud) {
		TranslateAnimation ta = new TranslateAnimation(-30, 0, 0, 0);
		TranslateAnimation ta_rightCloud = new TranslateAnimation(0, 30, 0, 0);
		ta.setDuration(5000);// 애니메이션의 동작 시간설정
		ta.setRepeatMode(Animation.REVERSE);
		ta.setRepeatCount(-1);// 무한반복을 뜻함
		ta_rightCloud.setDuration(5000);// 애니메이션의 동작 시간설정
		ta_rightCloud.setRepeatMode(Animation.REVERSE);
		ta_rightCloud.setRepeatCount(-1);// 무한반복을 뜻함

		leftCloud.startAnimation(ta);
		middleCloud.startAnimation(ta);
		rightCloud.startAnimation(ta_rightCloud);
	}

	private void buildDialog(String which, String subject) {
		if (which.equals("stage")) {

			ArrayList<StageItem> stageItemArray = new ArrayList<StageItem>();
			StageItemAdapter stageItemAdapter = null;
			HorizontalListView stageItemListView = null;

			for (int i = 1; i <= 5; i++) {
				stageItemArray.add(new StageItem(i, mMaxStage, subject));
			}

			Dialog dialog = new Dialog(MainActivity.this);

			// 타이틀바 없애기(setContentView 전에 선언해줘야 한다)
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.main_stage_item_list_view);

			stageItemListView = (HorizontalListView) dialog
					.findViewById(R.id.stage_item_lv);
			dialog.setCancelable(true);

			// dialog 사이즈 변경
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			Window window = dialog.getWindow();
			window.setAttributes(lp);

			dialog.getWindow().getAttributes().gravity = Gravity.CENTER;

			// 배경색 투명으로
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			stageItemListView.setOnItemClickListener(new stageItemListener());
			stageItemAdapter = new StageItemAdapter(MainActivity.this,
					stageItemArray);
			stageItemListView.setAdapter(stageItemAdapter);

			dialog.show();
		} else {

			ArrayList<SingleItem> singleItemArray = new ArrayList<SingleItem>();
			SingleItemAdapter singleItemAdapter = null;
			HorizontalListView singleItemListView = null;

			for (int i = 1; i <= 5; i++) {
				singleItemArray.add(new SingleItem(i));
			}

			Dialog dialog = new Dialog(MainActivity.this);

			// 타이틀바 없애기(setContentView 전에 선언해줘야 한다)
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.main_stage_item_list_view);

			singleItemListView = (HorizontalListView) dialog
					.findViewById(R.id.stage_item_lv);
			dialog.setCancelable(true);

			// dialog 사이즈 변경
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			Window window = dialog.getWindow();
			window.setAttributes(lp);

			dialog.getWindow().getAttributes().gravity = Gravity.CENTER;

			// 배경색 투명으로
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			singleItemListView.setOnItemClickListener(new singleItemListener());
			singleItemAdapter = new SingleItemAdapter(MainActivity.this,
					singleItemArray);
			singleItemListView.setAdapter(singleItemAdapter);

			dialog.show();
		}
	}

}
