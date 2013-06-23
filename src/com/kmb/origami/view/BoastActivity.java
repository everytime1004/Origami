package com.kmb.origami.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.kmb.origami.R;

public class BoastActivity extends Activity {

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boast);

		leftCloud = (ImageView) findViewById(R.id.cloud_left);
		middleCloud = (ImageView) findViewById(R.id.cloud_middle);
		rightCloud = (ImageView) findViewById(R.id.cloud_right);

		MainActivity.startAnimation(leftCloud, middleCloud, rightCloud);
	}
}
