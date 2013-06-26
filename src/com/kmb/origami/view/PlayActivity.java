package com.kmb.origami.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.kmb.origami.R;

@SuppressWarnings("deprecation")
public class PlayActivity extends Activity implements OnItemSelectedListener,
		ViewFactory, OnItemClickListener {

	private int mCurrentPosition = 0;
	// private int mSelectedPosition = 0;

	private TextView play_item_level = null;

	private Button play_left_button = null;
	private Button play_right_button = null;

	private LinearLayout play_right_button_layout = null;
	private LinearLayout.LayoutParams play_right_button_params = null;

	private Gallery play_gallery = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		play_item_level = (TextView) findViewById(R.id.play_item_level);
		play_item_level.setText(String.valueOf(getIntent().getIntExtra("level",
				0)));

		play_left_button = (Button) findViewById(R.id.play_left_button);
		play_right_button = (Button) findViewById(R.id.play_right_button);

		play_right_button_layout = (LinearLayout) findViewById(R.id.play_right_button_layout);
		play_right_button_params = (LinearLayout.LayoutParams) play_right_button
				.getLayoutParams();

		mSwitcher = (ImageSwitcher) findViewById(R.id.play_switcher);
		mSwitcher.setFactory(this);

		play_gallery = (Gallery) findViewById(R.id.play_gallery);
		play_gallery.setAdapter(new ImageAdapter(this));
		play_gallery.setOnItemSelectedListener(this);
		play_gallery.setOnItemClickListener(this);

	}

	public void playButton(View v) {
		Log.d("mCurrentPosition", String.valueOf(mCurrentPosition));
		switch (v.getId()) {
		case R.id.play_right_button:
			play_gallery.performItemClick(v, mCurrentPosition + 1,
					mCurrentPosition + 1);
			play_gallery.setSelection(mCurrentPosition);
			play_left_button.setClickable(true);
			break;

		case R.id.play_left_button:
			if (mCurrentPosition == 0) {
				play_left_button.setClickable(false);
				break;
			}

			play_gallery.performItemClick(v, mCurrentPosition - 1,
					mCurrentPosition - 1);
			play_gallery.setSelection(mCurrentPosition);
			break;
		}
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		Log.d("current position in onItemSelected", String.valueOf(position));
		play_item_level = (TextView) findViewById(R.id.play_item_level);
		play_item_level.setText(String.valueOf(position + 1) + "/"
				+ String.valueOf(mThumbIds.length));

		// if (mSelectedPosition != position) {
		// mCurrentPosition = position;
		// mSwitcher.setImageResource(mImageIds[mCurrentPosition]);
		// } else {
		// mCurrentPosition = mSelectedPosition;
		// mSwitcher.setImageResource(mImageIds[mCurrentPosition]);
		// }
		mSwitcher.setImageResource(mImageIds[mCurrentPosition]);
		play_gallery.setSelection(mCurrentPosition);

		if (position == 0) {
			play_left_button.setBackgroundResource(R.drawable.btn_left_disable);
			play_right_button
					.setBackgroundResource(R.drawable.index_right_button);

			play_right_button_layout.setGravity(Gravity.CENTER | Gravity.LEFT);

			play_right_button.setLayoutParams(play_right_button_params);

		} else if (position + 1 == mThumbIds.length) {
			play_left_button
					.setBackgroundResource(R.drawable.index_left_button);
			play_right_button
					.setBackgroundResource(R.drawable.play_camera_button);

			play_right_button_layout.setGravity(Gravity.CENTER | Gravity.RIGHT);

			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			play_right_button.setLayoutParams(params2);

		} else {
			play_left_button
					.setBackgroundResource(R.drawable.index_left_button);
			play_right_button
					.setBackgroundResource(R.drawable.index_right_button);

			play_right_button_layout.setGravity(Gravity.CENTER | Gravity.LEFT);

			play_right_button.setLayoutParams(play_right_button_params);

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		Log.d("current position", String.valueOf(position));

		if (mCurrentPosition <= position) {
			mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_in_right));
			mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_out_right));
		} else {
			mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_in_left));
			mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_out_left));
		}

		mCurrentPosition = position;

	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	private ImageSwitcher mSwitcher;

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			i.setBackgroundResource(R.drawable.play_image_border_current);
			i.setImageResource(mThumbIds[position]);
			i.setAdjustViewBounds(true);

			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			return i;
		}

		private Context mContext;

	}

	private Integer[] mThumbIds = { R.drawable.index_start_button,
			R.drawable.index_collection_button, R.drawable.btn_howto,
			R.drawable.cloud_middle };

	private Integer[] mImageIds = { R.drawable.index_start_button,
			R.drawable.index_collection_button, R.drawable.btn_howto,
			R.drawable.cloud_middle };

}