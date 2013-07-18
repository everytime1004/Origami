package com.kmb.origami.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

		Button boast_upload_button = (Button) findViewById(R.id.boast_upload_button);
		boast_upload_button.setOnClickListener(new uploadImage());

		Button boast_board_button = (Button) findViewById(R.id.boast_board_button);
		boast_board_button.setOnClickListener(new showBoard());

	}

	private class showBoard implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			Intent showBoardIntent = new Intent(getApplicationContext(),
					BoardIndexActivity.class);
			startActivity(showBoardIntent);
		}
	}

	private class uploadImage implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent resultIntent = new Intent(getApplicationContext(),
					BoardCreateActivity.class);
			startActivity(resultIntent);

			Toast.makeText(getApplicationContext(), "왼쪽 이미지를 클릭해 올릴 사진을 선택하세요",
					Toast.LENGTH_LONG).show();
		}
	}

}
