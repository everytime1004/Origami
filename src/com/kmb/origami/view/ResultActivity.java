package com.kmb.origami.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kmb.origami.R;
import com.kmb.origami.controller.CacheManager;

public class ResultActivity extends Activity {

	ImageView resultImage = null;
	Bitmap resultBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		resultImage = (ImageView) findViewById(R.id.result_image);

		 BitmapDownloaderTask getImageTask = new BitmapDownloaderTask();
		 getImageTask.execute(getIntent().getStringExtra("resultImageUrl"));
	}

	public void resultButtonListener(View v) {
		Intent resultIntent = null;
		int id = v.getId();
		if (id == R.id.result_save_button) {
			resultIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(resultIntent, 0);
			Toast.makeText(getApplicationContext(), "자신이 접은 종이를 찍어주세요.",
					Toast.LENGTH_LONG).show();
		} else if (id == R.id.result_share_button) {
			resultIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(resultIntent, 1);
			Toast.makeText(getApplicationContext(), "자신이 접은 종이를 찍어서 공유하세요.",
					Toast.LENGTH_LONG).show();
		} else if (id == R.id.result_main_button) {
			resultIntent = new Intent(getApplicationContext(),
					IndexActivity.class);
			resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(resultIntent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			Log.d("request Code", String.valueOf(requestCode));
			if (requestCode == 0) {
				Bitmap picBitmap = null;

				try {
					picBitmap = decodeUri(targetUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					CacheManager.cacheDataForCollection(
							getApplicationContext(), picBitmap, "1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent resultIntent = new Intent(getApplicationContext(),
						CollectionActivity.class);
				startActivity(resultIntent);
				finish();
			} else {
				Intent shareIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				shareIntent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				shareIntent.setType("image/*");
				// For a file in shared storage. For data in private storage,
				// use a ContentProvider.
				shareIntent.putExtra(Intent.EXTRA_STREAM, targetUri);
				startActivity(Intent.createChooser(shareIntent, "Share via"));
			}

		} else {
			// imageId_arr[requestCode - 1] = 0;
			Log.d("RESULT_CANCEL", "CANCEL");
		}
	}

	class BitmapDownloaderTask extends AsyncTask<String, Void, Boolean> {

		public BitmapDownloaderTask() {
		}

		@Override
		// Actual download method, run in the task thread
		protected Boolean doInBackground(String... params) {
			// params comes from the execute() call: params[0] is the url.

			resultBitmap = downloadBitmap(params[0]);

			return true;
		}

		Bitmap downloadBitmap(String url) {
			final AndroidHttpClient client = AndroidHttpClient
					.newInstance("Android");
			String totalURL = null;
			Bitmap bitmap = null;

			totalURL = url;

			final HttpGet getRequest = new HttpGet(totalURL);

			try {
				URL imageUrl = new URL(totalURL);
				URLConnection connection = imageUrl.openConnection();
				connection.setUseCaches(true);

				InputStream inputStream = connection.getInputStream();

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				bitmap = BitmapFactory.decodeStream(inputStream);

				return bitmap;
			} catch (Exception e) {
				// Could provide a more explicit error message for IOException
				// or
				// IllegalStateException
				getRequest.abort();
				Log.w("ImageDownloader", "Error while retrieving bitmap from "
						+ url + e.toString());
			} finally {
				if (client != null) {
					client.close();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			resultImage.setImageBitmap(resultBitmap);

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
