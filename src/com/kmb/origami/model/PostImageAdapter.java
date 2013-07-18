package com.kmb.origami.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PostImageAdapter extends BaseAdapter {
	Context mContext;
	private List<Post> mPosts;

	public PostImageAdapter(Context context, ArrayList<Post> mPosts) {
		this.mContext = context;
		this.mPosts = mPosts;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPosts.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mPosts.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		PostImageRow mPostImageView;
		if (convertView == null) {
			mPostImageView = new PostImageRow(mContext, mPosts.get(position));
		} else {
			mPostImageView = (PostImageRow) convertView;

			mPostImageView.setAuthor(mPosts.get(position).getAuthor());

			mPostImageView.setImage(mPosts.get(position).getImageURL());
		}

		return mPostImageView;
	}
	
	

}