package com.kmb.origami.view;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.kmb.origami.R;
import com.kmb.origami.lib.ProgressWheel;
import com.qualcomm.QCARSamples.CloudRecognition.CloudReco;

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
	private LinearLayout play_switcher_layout = null;

	private Gallery play_gallery = null;

	ProgressWheel pw_three;

	private Handler spinHandler = new Handler() {
		/**
		 * This is the code that will increment the progress variable and so
		 * spin the wheel
		 */
		@Override
		public void handleMessage(Message msg) {
			if (Integer.parseInt(pw_three.getText().toString()) != 0) {
				pw_three.setText(String.valueOf(Integer.parseInt(pw_three
						.getText().toString()) - 1));

				spinHandler.sendEmptyMessageDelayed(0, 1000);
			} else {
				spinHandler.removeMessages(0);
			}
		}
	};

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
		play_switcher_layout = (LinearLayout) findViewById(R.id.play_switcher_layout);

		mSwitcher = (ImageSwitcher) findViewById(R.id.play_switcher);
		mSwitcher.setFactory(this);

		play_gallery = (Gallery) findViewById(R.id.play_gallery);
		play_gallery.setAdapter(new ImageAdapter(this));
		play_gallery.setOnItemSelectedListener(this);
		play_gallery.setOnItemClickListener(this);

		pw_three = (ProgressWheel) findViewById(R.id.progressBarThree);
		pw_three.setText("180");
		pw_three.setSpinSpeed(6);
		spinHandler.sendEmptyMessage(0);
		pw_three.spin();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		spinHandler.removeMessages(0);
		pw_three.stopSpinning();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			Log.d("request Code", String.valueOf(requestCode));

			Bitmap resultBitmap = null;
			try {
				resultBitmap = decodeUri(targetUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Intent resultIntent = new Intent(getApplicationContext(),
					ResultActivity.class);
			Bundle extras = new Bundle();
			extras.putParcelable("resultImage", resultBitmap);
			resultIntent.putExtras(extras);
			startActivity(resultIntent);

		} else {
			// imageId_arr[requestCode - 1] = 0;
			play_left_button.setClickable(true);
			Log.d("RESULT_CANCEL", "CANCEL");
		}
	}

	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(
				getContentResolver().openInputStream(selectedImage), null, o);

		final int REQUIRED_SIZE = 300;

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (!(width_tmp / 2 < REQUIRED_SIZE && height_tmp / 2 < REQUIRED_SIZE)) {
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(
				getContentResolver().openInputStream(selectedImage), null, o2);
	}

	public void playButton(View v) {
		Log.d("mCurrentPosition", String.valueOf(mCurrentPosition));
		int id = v.getId();
		if (id == R.id.play_right_button) {
			if (play_right_button.getTag().equals("ing")) {
				play_gallery.performItemClick(v, mCurrentPosition + 1,
						mCurrentPosition + 1);
				play_gallery.setSelection(mCurrentPosition);
				play_left_button.setClickable(true);
			} else {
				Toast.makeText(getApplicationContext(), "사진을 찍자",
						Toast.LENGTH_LONG).show();
//				Intent cameraIntent = new Intent(
//						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//				startActivityForResult(cameraIntent, 0);
				Intent i = new Intent(this, CloudReco.class);
				startActivity(i);
			}
		} else if (id == R.id.play_left_button) {
			if (mCurrentPosition == 0) {
				play_left_button.setClickable(false);
			}
			play_gallery.performItemClick(v, mCurrentPosition - 1,
					mCurrentPosition - 1);
			play_gallery.setSelection(mCurrentPosition);
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
		// mSwitcher.setImageResource(mImageIds[mCurrentPosition]);
		// play_gallery.setSelection(mCurrentPosition);
		mSwitcher.setImageResource(mImageIds[position]);

		if (position == 0) {
			play_left_button.setBackgroundResource(R.drawable.btn_left_disable);
			play_right_button
					.setBackgroundResource(R.drawable.index_right_button);

			play_switcher_layout.setLayoutParams(new LinearLayout.LayoutParams(
					0, LayoutParams.MATCH_PARENT, 10f));

			play_right_button_layout
					.setLayoutParams(new LinearLayout.LayoutParams(0,
							LayoutParams.MATCH_PARENT, 2f));
			play_right_button_layout.setGravity(Gravity.CENTER | Gravity.LEFT);

			play_right_button.setLayoutParams(play_right_button_params);
			play_right_button.setTag("ing");

		} else if (position + 1 == mThumbIds.length) {
			play_left_button
					.setBackgroundResource(R.drawable.index_left_button);
			play_right_button
					.setBackgroundResource(R.drawable.play_camera_button);

			play_switcher_layout.setLayoutParams(new LinearLayout.LayoutParams(
					0, LayoutParams.MATCH_PARENT, 8f));

			play_right_button_layout.setGravity(Gravity.CENTER | Gravity.RIGHT);
			play_right_button_layout
					.setLayoutParams(new LinearLayout.LayoutParams(0,
							LayoutParams.MATCH_PARENT, 4f));

			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			play_right_button.setLayoutParams(params2);

			play_right_button.setTag("last");

		} else {
			play_left_button
					.setBackgroundResource(R.drawable.index_left_button);
			play_right_button
					.setBackgroundResource(R.drawable.index_right_button);

			play_switcher_layout.setLayoutParams(new LinearLayout.LayoutParams(
					0, LayoutParams.MATCH_PARENT, 10f));

			play_right_button_layout
					.setLayoutParams(new LinearLayout.LayoutParams(0,
							LayoutParams.MATCH_PARENT, 2f));
			play_right_button_layout.setGravity(Gravity.CENTER | Gravity.LEFT);

			play_right_button.setLayoutParams(play_right_button_params);
			play_right_button.setTag("ing");

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

			i.setLayoutParams(new Gallery.LayoutParams(100,
					LayoutParams.MATCH_PARENT));
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