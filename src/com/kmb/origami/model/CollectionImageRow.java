package com.kmb.origami.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
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

//		if (mCollectionImage.getId() <= mCollectionImage.getMaxStage()) {
//			selectImage(mCollectionImage.getId(), mCollectionImage.getSubject());
//			Log.d("Stage ID in row", String.valueOf(mCollectionImage.getId()));
//		} else {
//			collectionImageIv.setImageDrawable(this.getResources().getDrawable(
//					R.drawable.stage_lock));
//			Log.d("Stage ID in row", String.valueOf(mCollectionImage.getId()));
//		}
		
		setImage(mCollectionImage.getId(), mCollectionImage.getImage());

	}

	public void setImage(int id, Bitmap image) {
		if (id == 1) {
			collectionImageIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.collect_personal));
		} else if(id == 2){
			collectionImageIv.setBackground(this.getResources().getDrawable(
					R.drawable.collect_stage_first));			
			collectionImageIv.setImageBitmap(image);
		}else {
			collectionImageIv.setImageDrawable(this.getResources().getDrawable(
					R.drawable.collect_stage_other));
			collectionImageIv.setImageBitmap(image);
		}
	}
}
