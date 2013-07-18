package com.kmb.origami.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SingleItemAdapter extends BaseAdapter {

	private Context mContext;
	private List<SingleItem> mSingleItems;

	public SingleItemAdapter(Context context, ArrayList<SingleItem> mSingleItems) {
		this.mContext = context;
		this.mSingleItems = mSingleItems;
	}

	@Override
	public int getCount() {
		return mSingleItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mSingleItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		SingleItemRow mSingleItemView;
		if (convertView == null) {
			mSingleItemView  = new SingleItemRow(mContext,
					mSingleItems.get(position));
		} else {
			mSingleItemView = (SingleItemRow) convertView;

			mSingleItemView.setImage(mSingleItems.get(position).getId(), mSingleItems.get(position).getMaxStage());
		}

		return mSingleItemView ;
	}
}
