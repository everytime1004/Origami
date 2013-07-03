package com.kmb.origami.view;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kmb.origami.R;

public class BoastActivity extends Activity {

	private ImageView leftCloud = null;
	private ImageView middleCloud = null;
	private ImageView rightCloud = null;
	
	private Dialog tmpDialog = null;

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
			Dialog dialog = new Dialog(BoastActivity.this);

			// 타이틀바 없애기(setContentView 전에 선언해줘야 한다)
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.boast_upload_button);

			dialog.setCancelable(true);

			// dialog 사이즈 변경
			// WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			// lp.copyFrom(dialog.getWindow().getAttributes());
			// lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			// Window window = dialog.getWindow();
			// window.setAttributes(lp);

			// dialog.getWindow().getAttributes().gravity = Gravity.CENTER;

			// 배경색 투명으로
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			ImageButton boast_upload_image_from_camera = (ImageButton) dialog
					.findViewById(R.id.boast_upload_image_from_camera);
			ImageButton boast_upload_image_from_gallery = (ImageButton) dialog
					.findViewById(R.id.boast_upload_image_from_gallery);

			boast_upload_image_from_camera
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(cameraIntent, 0);
						}
					});

			boast_upload_image_from_gallery
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent galleryIntent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							galleryIntent.setType("image/*");
							startActivityForResult(galleryIntent, 0);
						}
					});
			
			tmpDialog = dialog;

			dialog.show();

		}
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
					BoardCreateActivity.class);
			Bundle extras = new Bundle();
			extras.putParcelable("resultImage", resultBitmap);
			resultIntent.putExtras(extras);
			startActivity(resultIntent);
			
			tmpDialog.dismiss();
		} else {
			// imageId_arr[requestCode - 1] = 0;
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

}
