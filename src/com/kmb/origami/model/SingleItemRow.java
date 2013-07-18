package com.kmb.origami.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kmb.origami.R;

@SuppressLint("ViewConstructor")
public class SingleItemRow extends LinearLayout {

	private ImageView singleItemIv;

	public SingleItemRow(Context context, SingleItem mSingleItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_row, this, true);
		singleItemIv = (ImageView) findViewById(R.id.singleItemIv);

		if (mSingleItem.getId() <= mSingleItem.getMaxStage()) {
			selectImage(mSingleItem.getId());
			Log.d("Stage ID in row", String.valueOf(mSingleItem.getId()));
		} else {
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
			singleItemIv.setClickable(false);
			Log.d("Stage ID in row", String.valueOf(mSingleItem.getId()));
		}

	}

	public void setImage(int id, int maxStage) {

		if (id <= maxStage) {
			Log.d("maxStage", String.valueOf(maxStage));
			selectImage(id);
		} else {
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
		}

	}

	private void selectImage(int id) {
		switch (id) {
		case 1:
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_1));
			break;
		case 2:
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_2));
			break;
		case 3:
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_3));
			break;
		case 4:
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_4));
			break;
		case 5:
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_5));
			break;

		default:
			singleItemIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
			break;
		}
	}
}
