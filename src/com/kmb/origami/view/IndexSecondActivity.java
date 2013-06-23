package com.kmb.origami.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kmb.origami.R;

public class IndexSecondActivity extends Activity {

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index_second);

		leftCloud = (ImageView) findViewById(R.id.cloud_left);
		middleCloud = (ImageView) findViewById(R.id.cloud_middle);
		rightCloud = (ImageView) findViewById(R.id.cloud_right);

		MainActivity.startAnimation(leftCloud, middleCloud, rightCloud);
	}

	public void indexSecondBtn(View v) {
		Intent changeViewIntent = null;
		switch (v.getId()) {
		case R.id.index_left_button:
			changeViewIntent = new Intent(this, IndexFirstActivity.class);
			startActivity(changeViewIntent);
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_left);
			finish();
			break;
		case R.id.index_collection_button:
			changeViewIntent = new Intent(this, CollectionActivity.class);
			startActivity(changeViewIntent);
			break;
		case R.id.index_right_button:
			changeViewIntent = new Intent(this, IndexThirdActivity.class);
			startActivity(changeViewIntent);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_right);
			finish();
			break;
		}
	}
}
