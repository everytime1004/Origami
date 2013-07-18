package com.kmb.origami.view;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.kmb.origami.R;
import com.kmb.origami.controller.NetworkInfo;
import com.kmb.origami.lib.UrlJsonAsyncTask;

public class BoardShowActivity extends SherlockActivity {

	private static int mPostId = 0;
	private static String mPostTitle = "";
	private static String mAuthor = "";
	private static String mPostPassword = "";

	private static String SHOW_TASK_ENDPOINT_URL;

	private static String DESTORY_TASK_ENDPOINT_URL;

	private String imageBitMapURL;

	private Bitmap imageBitmap;

	private ImageView showImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board_show);

		TextView task_show_title = (TextView) findViewById(R.id.task_show_title);
		TextView task_show_author = (TextView) findViewById(R.id.task_show_author);

		Intent taskIntent = getIntent();
		mPostTitle = taskIntent.getStringExtra("title");
		mPostId = taskIntent.getIntExtra("post_id", 0);
		mAuthor = taskIntent.getStringExtra("author");

		task_show_title.setText(mPostTitle);
		task_show_author.setText(mAuthor);

		SHOW_TASK_ENDPOINT_URL = NetworkInfo.IP + "/api/v1/posts/" + mPostId
				+ ".json";

		showTaskTasks showTask = new showTaskTasks(BoardShowActivity.this);
		showTask.setMessageLoading("글 불러오는 중...");
		showTask.execute(SHOW_TASK_ENDPOINT_URL);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.show, menu);

		menu.findItem(R.id.action_create_delete).setVisible(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_create_delete) {
			final Dialog dialog = new Dialog(BoardShowActivity.this);
			dialog.setContentView(R.layout.task_remove_input_password_dialog);
			dialog.setTitle("비밀번호를 입력하세요");
			// 취소 버튼
			((Button) dialog.findViewById(R.id.todayCancelBtn))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
			// 저장 버튼
			((Button) dialog.findViewById(R.id.todayInputBtn))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							EditText taskPasswordEt = (EditText) dialog
									.findViewById(R.id.taskPasswordEt);

							mPostPassword = taskPasswordEt.getText().toString();

							dialog.dismiss();

							DESTORY_TASK_ENDPOINT_URL = NetworkInfo.IP
									+ "/api/v1/posts/" + mPostId + ".json"
									+ "?password=" + mPostPassword;

							final Dialog removeDialog = new Dialog(
									BoardShowActivity.this);
							removeDialog
									.setContentView(R.layout.task_remove_dialog);
							removeDialog.setTitle("삭제하시겠습니까?");

							// 취소 버튼
							((Button) removeDialog
									.findViewById(R.id.todayCancelBtn))
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											removeDialog.dismiss();
										}
									});
							// 저장 버튼
							((Button) removeDialog
									.findViewById(R.id.todayInputBtn))
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											DestroyTaskTasks destroyTaskTasks = new DestroyTaskTasks(
													BoardShowActivity.this);
											destroyTaskTasks
													.setMessageLoading("글 삭제중...");
											// destroyTaskTasks.setAuthToken(mPreferences
											// .getString("AuthToken", ""));
											destroyTaskTasks
													.execute(DESTORY_TASK_ENDPOINT_URL);

											Intent deleteTaskIntent = new Intent(
													getApplicationContext(),
													BoardIndexActivity.class);
											deleteTaskIntent
													.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											deleteTaskIntent
													.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
											startActivity(deleteTaskIntent);

											removeDialog.dismiss();
										}
									});
							removeDialog.show();
						}
					});
			dialog.show();
		}

		return true;
	}

	class BitmapDownloaderTask extends AsyncTask<String, Void, Boolean> {

		public BitmapDownloaderTask() {
		}

		@Override
		// Actual download method, run in the task thread
		protected Boolean doInBackground(String... params) {
			// params comes from the execute() call: params[0] is the url.
			showImage = (ImageView) findViewById(R.id.task_show_image);

			imageBitmap = downloadBitmap(imageBitMapURL);

			return true;
		}

		@Override
		// Once the image is downloaded, associates it to the imageView
		protected void onPostExecute(Boolean bool) {
			if (isCancelled()) {
				bool = false;
			}

			// 비트맵이 널이 아니면 원래 image 초기화 시키고 새로운 이미지 넣고 보여지게 함

			showImage.setImageResource(0);
//			showImage.setImageBitmap(resizeBitmap(imageBitmap));
			showImage.setImageBitmap(imageBitmap);
			showImage.setVisibility(View.VISIBLE);

		}
	}

	private class DestroyTaskTasks extends UrlJsonAsyncTask {
		public DestroyTaskTasks(Context context) {
			super(context);
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpDelete delete = new HttpDelete(urls[0]);
			String response = null;
			JSONObject json = new JSONObject();

			try {
				try {
					// setup the returned values in case
					// something goes wrong
					json.put("success", false);
					json.put("info", "인터넷 연결을 다시 확인해 주세요.");

					delete.setHeader("Accept", "application/json");
					delete.setHeader("Content-Type", "application/json");

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					response = client.execute(delete, responseHandler);
					json = new JSONObject(response);

				} catch (HttpResponseException e) {
					e.printStackTrace();
					Log.e("ClientProtocol", "" + e);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("IO", "" + e);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSON", "" + e);
			}

			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				if (json.getBoolean("success")) {
					Toast.makeText(context, json.getString("info"),
							Toast.LENGTH_LONG).show();
				}

			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
						.show();
			} finally {
				super.onPostExecute(json);
			}
		}
	}

	private class showTaskTasks extends UrlJsonAsyncTask {
		public showTaskTasks(Context context) {
			super(context);
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				if (json.getBoolean("success")) {
					JSONArray jsonTasks = json.getJSONObject("data")
							.getJSONArray("image");
					int length = jsonTasks.length();
					for (int i = 0; i < length; i++) {
						String image = jsonTasks.getString(i);

						if (image.contains("fallback/default.png")) {
							throw new Exception("사진이 없습니다");
						}
						/*
						 * 만약 글에 사진이 없으면 default 이미지가 넘어오는데 default를 지정해주지 않았으므로
						 * url이 없고 bitmap 다운로드할 때 에러가 발생해서 거기서 httl close 등 못하고
						 * 죽어버리므로 leak 발생해서 앱이 죽어버림
						 */

						imageBitMapURL = image;
					}
				}

				BitmapDownloaderTask task = new BitmapDownloaderTask();
				task.execute();

				// Toast.makeText(context, json.getString("info"),
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				try {
					Toast.makeText(context, json.getString("info"),
							Toast.LENGTH_LONG).show();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} finally {
				super.onPostExecute(json);
			}
		}
	}

	public static Bitmap downloadBitmap(String url) {
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("Android");
		String totalURL = null;
		Bitmap bitmap = null;

		try {
			String[] splitURL = null;
			splitURL = url.split("/");
			String encodeURL = URLEncoder.encode(url, "UTF-8");
			String lastEncodeURL = encodeURL.split("%2F")[8];

			totalURL = splitURL[0] + "//" + splitURL[2] + "/" + splitURL[3]
					+ "/" + splitURL[4] + "/" + splitURL[5] + "/" + splitURL[6]
					+ "/" + splitURL[7] + "/" + lastEncodeURL;
			Log.d("imageURL", totalURL);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final HttpGet getRequest = new HttpGet(totalURL);

		try {
			URL imageUrl = new URL(totalURL);
			URLConnection connection = imageUrl.openConnection();
			connection.setUseCaches(true);

			InputStream inputStream = connection.getInputStream();

			// BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inSampleSize = 1;
			// bitmap = BitmapFactory.decodeStream(inputStream, null, options);
			bitmap = BitmapFactory.decodeStream(new FlushedInputStream(
					inputStream));

			return bitmap;
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
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

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	private Bitmap resizeBitmap(Bitmap originBitmap) {
		int Measuredheight = 0;
		Point size = new Point();
		WindowManager w = getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);
			Log.d("width", String.valueOf(size.x));
			Log.d("height", String.valueOf(size.y));
			Measuredheight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			Measuredheight = d.getHeight();
		}

		int height = originBitmap.getHeight();
		int width = originBitmap.getWidth();

		Bitmap resized = null;
		while (height > Measuredheight/3*2) {
			resized = Bitmap.createScaledBitmap(originBitmap,
					(width * (Measuredheight/3*2)) / height, Measuredheight/3*2, true);
			height = resized.getHeight();
			width = resized.getWidth();
		}

		return resized;
	}
	/********************************************** 댓글 *********************************************/

}
