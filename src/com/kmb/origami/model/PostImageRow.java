package com.kmb.origami.model;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.kmb.origami.R;

public class PostImageRow extends LinearLayout {

	private TextView mPostIndexAuthor;

	private ImageView mPostIndexImageView;

	Activity mContext;

	public PostImageRow(Context context, Post mPost)
			throws UnsupportedEncodingException {
		super(context);
		this.mContext = (Activity) context;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.post_image_row, this, true);

		mPostIndexAuthor = (TextView) findViewById(R.id.post_index_author);

		mPostIndexAuthor.setText(mPost.getAuthor());

		mPostIndexImageView = (ImageView) findViewById(R.id.post_index_image);

		LayoutParams params = (LayoutParams) mPostIndexImageView
				.getLayoutParams();
		params.height = (int) ((getOneIndexImageHeight() / 10) * 3);
		Log.d("params.height", String.valueOf(params.height));
		// existing height is ok as is, no need to edit it
		mPostIndexImageView.setLayoutParams(params);

		AQuery aq = new AQuery(PostImageRow.this);
		aq.id(R.id.post_index_image).image(mPost.getImageURL(), true, true,
				100, 0);

	}

	public void setImage(String imageURL) {
		AQuery aq = new AQuery(PostImageRow.this);
		aq.id(R.id.post_index_image).image(imageURL, true, true, 100, 0);

	}

	public void setAuthor(String data) {
		mPostIndexAuthor.setText(data);
	}

	private int getOneIndexImageHeight() {
		int Measuredheight = 0;
		Point size = new Point();
		WindowManager w = mContext.getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);
			Log.d("width", String.valueOf(size.x));
			Log.d("height", String.valueOf(size.y));
			Measuredheight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			Measuredheight = d.getHeight();
		}

		return Measuredheight;
	}
}
