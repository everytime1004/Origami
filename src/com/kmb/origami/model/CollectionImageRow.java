package com.kmb.origami.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kmb.origami.R;

@SuppressLint("ViewConstructor")
public class CollectionImageRow extends LinearLayout {

	private ImageView collectionImageIv;

	public CollectionImageRow(Context context, CollectionImage mCollectionImage) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.collection_row, this, true);
		collectionImageIv = (ImageView) findViewById(R.id.collectionImageIv);

		// if (mCollectionImage.getId() <= mCollectionImage.getMaxStage()) {
		// selectImage(mCollectionImage.getId(), mCollectionImage.getSubject());
		// Log.d("Stage ID in row", String.valueOf(mCollectionImage.getId()));
		// } else {
		// collectionImageIv.setImageDrawable(this.getResources().getDrawable(
		// R.drawable.stage_lock));
		// Log.d("Stage ID in row", String.valueOf(mCollectionImage.getId()));
		// }

		setImage(mCollectionImage.getId(), mCollectionImage.getImage());

	}

	public void setImage(int id, Bitmap image) {
		LinearLayout collectionImageLayout = (LinearLayout) findViewById(R.id.collectionImageLayout);
		if (image != null) {
			image = Bitmap.createScaledBitmap(image, (int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70,
							getResources().getDisplayMetrics()),
					(int) TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_DIP, 70, getResources()
									.getDisplayMetrics()), true);
		}

		if (id == 0) {
			collectionImageLayout.setBackground(this.getResources()
					.getDrawable(R.drawable.collect_personal));
			collectionImageLayout
					.setLayoutParams(new LinearLayout.LayoutParams(
							(int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 70,
									getResources().getDisplayMetrics()),
							(int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 100,
									getResources().getDisplayMetrics()), 0F));
			collectionImageIv.setImageDrawable(null);
			collectionImageIv.setBackground(null);

		} else if (id == 1) {
			collectionImageLayout.setGravity(Gravity.CENTER);
			collectionImageLayout.setBackground(this.getResources()
					.getDrawable(R.drawable.collect_stage_first));

			collectionImageIv.setBackground(this.getResources().getDrawable(
					R.drawable.collection_image_border));
			collectionImageIv.setImageBitmap(image);

		} else {
			collectionImageLayout.setBackground(this.getResources()
					.getDrawable(R.drawable.collect_stage_other));
			collectionImageLayout
					.setLayoutParams(new LinearLayout.LayoutParams(
							(int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 100,
									getResources().getDisplayMetrics()),
							(int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 100,
									getResources().getDisplayMetrics()), 0F));
			collectionImageLayout.setGravity(Gravity.CENTER);

			collectionImageIv.setBackground(this.getResources().getDrawable(
					R.drawable.collection_image_border));
			collectionImageIv.setImageBitmap(image);
		}
	}
}
