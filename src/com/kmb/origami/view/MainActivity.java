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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.devsmart.android.ui.HorizontalListView;
import com.kmb.origami.R;
import com.kmb.origami.model.SingleItem;
import com.kmb.origami.model.SingleItemAdapter;
import com.kmb.origami.model.StageItem;
import com.kmb.origami.model.StageItemAdapter;

public class MainActivity extends Activity {

	private int mMaxStage;
	private int mMaxOfSingleStage;

	private int currentStage;

	private SharedPreferences mMaxStagePreferences; // 몇 스테지 까지 깻는지
	private SharedPreferences mMaxOfSingleStagePreferences; // 한 스테이지에 대한 최대 몇
															// 번째 까지 성공했는지

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMaxStagePreferences = getSharedPreferences("MaxStageInfo",
				MODE_PRIVATE);
		mMaxOfSingleStagePreferences = getSharedPreferences("MaxOfSingleStage",
				MODE_PRIVATE);

		mMaxStage = mMaxStagePreferences.getInt("mMaxStage", 1);
		Log.d("MaxStage", String.valueOf(mMaxStage));

		leftCloud = (ImageView) findViewById(R.id.cloud_left);
		middleCloud = (ImageView) findViewById(R.id.cloud_middle);
		rightCloud = (ImageView) findViewById(R.id.cloud_right);

		startAnimation(leftCloud, middleCloud, rightCloud);
	}

	public void mainViewBtn(View v) {

		int id = v.getId();
		if (id == R.id.main_single_button) {
			buildDialog("stage", "single", 0);
		} else if (id == R.id.main_theme_button) {
			buildDialog("stage", "theme", 0);
		} else if (id == R.id.main_boast_button) {
			Toast.makeText(getApplicationContext(), "공사 중입니다.",
					Toast.LENGTH_SHORT).show();
			Intent changeViewIntent = new Intent(this, BoastActivity.class);
			startActivity(changeViewIntent);
		}
	}

	private class stageItemListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parentView, View v,
				int position, long id) {

			StageItem selectedItem = (StageItem) parentView
					.getItemAtPosition(position);
			Log.d("id", String.valueOf(selectedItem.getId()));

			Log.d("mMaxStage", String.valueOf(mMaxStage));

			if (selectedItem.getId() <= mMaxStage) {
				currentStage = selectedItem.getId();
				buildDialog("singleItem", "single", selectedItem.getId());
			}

		}
	}

	private class singleItemListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parentView, View v,
				int position, long id) {

			SingleItem selectedItem = (SingleItem) parentView
					.getItemAtPosition(position);
			Log.d("id", String.valueOf(selectedItem.getId()));

			Log.d("mMaxOfSingleStage", String.valueOf(mMaxOfSingleStage));

			if (selectedItem.getId() <= mMaxOfSingleStage) {
				Intent startFoldingIntent = new Intent(getApplicationContext(),
						PlayActivity.class);
				startFoldingIntent.putExtra("currentStage", currentStage);
				startFoldingIntent
						.putExtra("currentItem", selectedItem.getId());
				startActivity(startFoldingIntent);
			}

		}
	}

	public static void startAnimation(ImageView leftCloud,
			ImageView middleCloud, ImageView rightCloud) {
		TranslateAnimation ta = new TranslateAnimation(-30, 0, 0, 0);
		TranslateAnimation ta_rightCloud = new TranslateAnimation(0, 30, 0, 0);
		ta.setDuration(3000);// 애니메이션의 동작 시간설정
		ta.setRepeatMode(Animation.REVERSE);
		ta.setRepeatCount(-1);// 무한반복을 뜻함
		ta_rightCloud.setDuration(3000);// 애니메이션의 동작 시간설정
		ta_rightCloud.setRepeatMode(Animation.REVERSE);
		ta_rightCloud.setRepeatCount(-1);// 무한반복을 뜻함

		leftCloud.startAnimation(ta);
		middleCloud.startAnimation(ta);
		rightCloud.startAnimation(ta_rightCloud);
	}

	/**
	 * 
	 * @param which
	 *            stage인지 singleItem인지 구분
	 * @param subject
	 *            한 개씩 접기인지 주제별 접기인지 구분
	 * @param position
	 *            몇 번째 아이템이지
	 */
	private void buildDialog(String which, String subject, int position) {
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
		} else if (which.equals("singleItem")) {

			ArrayList<SingleItem> singleItemArray = new ArrayList<SingleItem>();
			SingleItemAdapter singleItemAdapter = null;
			HorizontalListView singleItemListView = null;

			mMaxOfSingleStage = mMaxOfSingleStagePreferences.getInt(
					String.valueOf(position), 1);

			for (int i = 1; i <= 5; i++) {
				singleItemArray.add(new SingleItem(i,
						mMaxOfSingleStagePreferences.getInt(
								String.valueOf(position), 1)));
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
		} else {
			// 주제별 눌렀을 때
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
		}
	}

}
