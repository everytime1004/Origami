package com.kmb.origami.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StageItemAdapter extends BaseAdapter {

	private Context mContext;
	private List<StageItem> mStageItems;

	public StageItemAdapter(Context context, ArrayList<StageItem> mStageItems) {
		this.mContext = context;
		this.mStageItems = mStageItems;
	}

	@Override
	public int getCount() {
		return mStageItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mStageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		StageItemRow mSingleItemView;
		if (convertView == null) {
			mSingleItemView = new StageItemRow(mContext,
					mStageItems.get(position));
		} else {
			mSingleItemView = (StageItemRow) convertView;

			mSingleItemView.setImage(mStageItems.get(position).getId(),
					mStageItems.get(position).getMaxStage(),
					mStageItems.get(position).getSubject());
		}

		return mSingleItemView;
	}
}
