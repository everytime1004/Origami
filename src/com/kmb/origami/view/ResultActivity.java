package com.kmb.origami.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kmb.origami.R;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		ImageView resultImage = (ImageView) findViewById(R.id.result_image);

		resultImage.setImageBitmap((Bitmap) getIntent().getExtras()
				.getParcelable("resultImage"));

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) resultImage
				.getLayoutParams();
		params.width = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 300, getResources()
						.getDisplayMetrics());
		;
		params.height = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 130, getResources()
						.getDisplayMetrics());
		;
		resultImage.setLayoutParams(params);
	}

	public void resultButtonListener(View v) {
		Intent resultIntent = null;
		int id = v.getId();
		if (id == R.id.result_save_button) {
		} else if (id == R.id.result_share_button) {
		} else if (id == R.id.result_main_button) {
			resultIntent = new Intent(getApplicationContext(),
					IndexActivity.class);
			resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(resultIntent);
		}
	}

}
