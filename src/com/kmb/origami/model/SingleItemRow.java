package com.kmb.origami.model;

import android.annotation.SuppressLint;
import android.content.Context;
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

		selectImage(mSingleItem.getId());

	}

	public void setImage(int id) {

		selectImage(id);

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
	//
	// public void setCategory(String data) {
	// comment_author.setText(data);
	// }
	//
	// public void setUpdated_day(String data) {
	// comment_updated_day.setText(data);
	// }
	//
	// public void setUpdated_time(String data) {
	// comment_updated_time.setText(data);
	// }
	//
	// public void setAuthor(String data) {
	// comment_author.setText(data);
	// }
	//
	// public void setDeleteBtn(boolean isOwner) {
	// if (isOwner) {
	// commentDeleteBtn.setVisibility(Button.VISIBLE);
	// } else {
	// commentDeleteBtn.setVisibility(Button.INVISIBLE);
	// }
	// }
}
