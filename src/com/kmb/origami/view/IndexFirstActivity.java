package com.kmb.origami.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kmb.origami.R;

public class IndexFirstActivity extends Activity {

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index_first);

		leftCloud = (ImageView) findViewById(R.id.cloud_left);
		middleCloud = (ImageView) findViewById(R.id.cloud_middle);
		rightCloud = (ImageView) findViewById(R.id.cloud_right);

		MainActivity.startAnimation(leftCloud, middleCloud, rightCloud);

	}

	public void indexFirstBtn(View v) {
		Intent changeViewIntent = null;
		switch (v.getId()) {
		case R.id.index_start_button:
			changeViewIntent = new Intent(this, MainActivity.class);
			startActivity(changeViewIntent);
			break;
		case R.id.index_right_button:
			changeViewIntent = new Intent(this, IndexSecondActivity.class);
			startActivity(changeViewIntent);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_right);
			finish();

			break;
		}
	}

	public static class FragmentOne extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.index_start_button, null);
		}

	}

	public static class FragmentTwo extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.index_collection_button, null);
		}

	}

	public static class FragmentThree extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.index_howto_button, null);
		}

	}

}
