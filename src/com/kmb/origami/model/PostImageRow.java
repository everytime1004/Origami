package com.kmb.origami.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.kmb.origami.R;

public class PostImageRow extends LinearLayout {

	private TextView task_author;
	private ImageView task_image;

	public PostImageRow(Context context, Post mPost) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.post_image_row, this, true);

		task_author = (TextView) findViewById(R.id.post_index_author);

		task_author.setText(mPost.getAuthor());

		task_image = (ImageView) findViewById(R.id.post_index_image);
		
		AQuery aq = new AQuery(PostImageRow.this);
		Bitmap a = aq.getCachedImage("https://www.google.com/chart?chs=150x150&cht=qr&chl=http://android-query.googlecode.com/files/android-query.0.25.10.jar&chld=L|1&choe=UTF-8", 50);
		task_image.setImageBitmap(a);
//		aq.id(R.id.post_index_image).image(mPost.getImageURL(), true, true, 50, 0);

	}

	public void setImage(String imageURL) {
		AQuery aq = new AQuery(PostImageRow.this);
//		aq.id(R.id.post_index_image).image(imageURL, true, true, 50, 0);
		Bitmap a = aq.getCachedImage("https://www.google.com/chart?chs=150x150&cht=qr&chl=http://android-query.googlecode.com/files/android-query.0.25.10.jar&chld=L|1&choe=UTF-8", 50);
		task_image.setImageBitmap(a);
	}

	public void setAuthor(String data) {
		task_author.setText(data);
	}
}
