package com.kmb.origami.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kmb.origami.R;

@SuppressLint("ViewConstructor")
public class PlaySingleItemRow extends LinearLayout {

	private ImageView play_single_item_iv;

	public PlaySingleItemRow(Context context, PlaySingleItem mPlaySingleItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.play_item_row, this, true);
		play_single_item_iv = (ImageView) findViewById(R.id.play_single_item_iv);

		selectImage(mPlaySingleItem.getId());

	}

	public void setImage(int id) {

		selectImage(id);

	}

	private void selectImage(int id) {
		switch (id) {
		case 1:
			play_single_item_iv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_1));
			break;
		case 2:
			play_single_item_iv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_2));
			break;
		case 3:
			play_single_item_iv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_3));
			break;
		case 4:
			play_single_item_iv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_4));
			break;
		case 5:
			play_single_item_iv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_5));
			break;

		default:
			play_single_item_iv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.stage_lock));
			break;
		}
	}
}
