package com.kmb.origami.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PlaySingleItemAdapter extends BaseAdapter {

	private Context mContext;
	private List<PlaySingleItem> mPlaySingleItems;

	public PlaySingleItemAdapter(Context context,
			ArrayList<PlaySingleItem> mPlaySingleItems) {
		this.mContext = context;
		this.mPlaySingleItems = mPlaySingleItems;
	}

	@Override
	public int getCount() {
		return mPlaySingleItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mPlaySingleItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PlaySingleItemRow mPlaySingleItemView;
		if (convertView == null) {
			mPlaySingleItemView = new PlaySingleItemRow(mContext,
					mPlaySingleItems.get(position));
		} else {
			mPlaySingleItemView = (PlaySingleItemRow) convertView;

			mPlaySingleItemView.setImage(mPlaySingleItems.get(position).getId());
		}

		return mPlaySingleItemView;
	}
}
