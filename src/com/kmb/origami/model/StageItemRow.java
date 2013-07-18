package com.kmb.origami.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kmb.origami.R;

@SuppressLint("ViewConstructor")
public class StageItemRow extends LinearLayout {

	private ImageView singleStageIv;

	public StageItemRow(Context context, StageItem mSingleStage) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.stage_row, this, true);
		singleStageIv = (ImageView) findViewById(R.id.singleStageIv);

		if (mSingleStage.getId() <= mSingleStage.getMaxStage()) {
			selectImage(mSingleStage.getId(), mSingleStage.getSubject());
			Log.d("Stage ID in row", String.valueOf(mSingleStage.getId()));
		} else {
			singleStageIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
			singleStageIv.setClickable(false);
			Log.d("Stage ID in row", String.valueOf(mSingleStage.getId()));
		}

	}

	public void setImage(int id, int maxStage, String mSubject) {
		if (id <= maxStage) {
			Log.d("maxStage", String.valueOf(maxStage));
			selectImage(id, mSubject);
		} else {
			singleStageIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
		}
	}

	private void selectImage(int id, String mSubject) {
		// if else 에서 else는 주제별 눌렀을 때 사진 박으면됨
		switch (id) {
		case 1:
			if (mSubject.equals("single")) {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_1));
			} else {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_1));
			}
			break;
		case 2:
			if (mSubject.equals("single")) {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_2));
			} else {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_2));
			}
			break;
		case 3:
			if (mSubject.equals("single")) {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_3));
			} else {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_3));
			}
			break;
		case 4:
			if (mSubject.equals("single")) {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_4));
			} else {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_4));
			}
			break;
		case 5:
			if (mSubject.equals("single")) {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_5));
			} else {
				singleStageIv.setImageDrawable(this.getResources().getDrawable(
						R.drawable.stage_5));
			}
			break;

		default:
			singleStageIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
			break;
		}
	}
}
