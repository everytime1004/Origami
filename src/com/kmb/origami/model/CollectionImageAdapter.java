package com.kmb.origami.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kmb.origami.R;
import com.kmb.origami.controller.CacheManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CollectionImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<CollectionImage> mCollectionImages;

	public CollectionImageAdapter(Context context,
			ArrayList<CollectionImage> mCollectionImages) {
		this.mContext = context;
		this.mCollectionImages = mCollectionImages;
	}

	@Override
	public int getCount() {
		return mCollectionImages.size();
	}

	@Override
	public Object getItem(int position) {
		return mCollectionImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CollectionImageRow mCollectionImageView;
		if (convertView == null) {
			mCollectionImageView = new CollectionImageRow(mContext,
					mCollectionImages.get(position));
		} else {
			mCollectionImageView = (CollectionImageRow) convertView;

			try {
				// position은 0부터 시작
				Log.d("position", String.valueOf(position));
				Log.d("LastIndex", String.valueOf(mCollectionImages.get(
						position).getLastIndex()));
				Log.d("Adapter getView getImageId",
						String.valueOf(mCollectionImages.get(position).getId()));
				if (mCollectionImages.get(position).getId() == mCollectionImages
						.get(position).getLastIndex() + 1) {
					BitmapDrawable drawable = (BitmapDrawable) mContext
							.getResources().getDrawable(R.drawable.icon);
					Bitmap lastImageAddButtonBitmap = drawable.getBitmap();
					mCollectionImageView.setImage(
							mCollectionImages.get(position).getId(),
							lastImageAddButtonBitmap);
				} else if (mCollectionImages.get(position).getId() == 0) {
					mCollectionImageView.setImage(
							mCollectionImages.get(position).getId(), null);

				} else {
					mCollectionImageView.setImage(
							mCollectionImages.get(position).getId(),
							CacheManager.retrieveDataForCollection(
									mContext,
									String.valueOf(mCollectionImages.get(
											position).getId())));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mCollectionImageView;
	}
}
