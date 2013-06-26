package com.kmb.origami.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.kmb.origami.R;

public class IndexActivity extends Activity implements ViewFactory {

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;
	private ImageSwitcher mSwitcher = null;
	private int imageFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		leftCloud = (ImageView) findViewById(R.id.cloud_left);
		middleCloud = (ImageView) findViewById(R.id.cloud_middle);
		rightCloud = (ImageView) findViewById(R.id.cloud_right);

		MainActivity.startAnimation(leftCloud, middleCloud, rightCloud);

		mSwitcher = (ImageSwitcher) findViewById(R.id.index_image_switcher);
		mSwitcher.setFactory(this);

		mSwitcher.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent changeViewIntent = null;
				Log.d("imageFlag", String.valueOf(imageFlag));
				switch (imageFlag) {
				case 0:
					changeViewIntent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(changeViewIntent);
					break;
				case 1:
					changeViewIntent = new Intent(getApplicationContext(),
							CollectionActivity.class);
					startActivity(changeViewIntent);
					break;
				case 2:
					changeViewIntent = new Intent(getApplicationContext(),
							CollectionActivity.class);
					startActivity(changeViewIntent);
					break;
				}
			}
		});

	}

	public void indexFirstBtn(View v) {
		Log.d("imageFlag", String.valueOf(imageFlag));
		switch (v.getId()) {
		case R.id.index_right_button:
			mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_in_right));
			mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_out_right));
			switch (imageFlag) {
			case 0:
				mSwitcher.setImageResource(mImageIds[1]);
				imageFlag++;
				break;
			case 1:
				mSwitcher.setImageResource(mImageIds[2]);
				imageFlag++;
				break;
			case 2:
				mSwitcher.setImageResource(mImageIds[0]);
				imageFlag = 0;
				break;

			}
			break;

		case R.id.index_left_button:
			mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_in_left));
			mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_out_left));
			switch (imageFlag) {
			case 0:
				mSwitcher.setImageResource(mImageIds[2]);
				imageFlag = 2;
				break;
			case 1:
				mSwitcher.setImageResource(mImageIds[0]);
				imageFlag--;
				break;
			case 2:
				mSwitcher.setImageResource(mImageIds[1]);
				imageFlag--;
				break;
			}

			break;
		}
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(this);
		i.setImageResource(mImageIds[0]);
		i.setId(R.drawable.index_start_button);
		i.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		return i;
	}

	private Integer[] mImageIds = { R.drawable.index_start_button,
			R.drawable.index_collection_button, R.drawable.btn_howto };

}
