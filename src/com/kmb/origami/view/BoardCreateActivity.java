package com.kmb.origami.view;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.kmb.origami.R;
import com.kmb.origami.controller.NetworkInfo;
import com.kmb.origami.lib.UrlJsonAsyncTask;

public class BoardCreateActivity extends SherlockActivity {

	private String mPostTitle;
	private String mPostDescription;
	private String mPostAuthor;
	private String mPostPassword;

	ImageView postImage = null;

	Bitmap originBitmap = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board_create);

		postImage = (ImageView) findViewById(R.id.postImage);

		postImage.setOnClickListener(new imageListener());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private class imageListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("Change Image ID", String.valueOf(v.getId()));

			AlertDialog.Builder getImageFrom = new AlertDialog.Builder(
					BoardCreateActivity.this);
			getImageFrom.setTitle("선택하세요");
			final CharSequence[] opsChars = {
					getResources().getString(R.string.takePic),
					getResources().getString(R.string.openGallery) };
			getImageFrom.setItems(opsChars,
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0) {
								getImageFromWhat(0);

							} else if (which == 1) {
								getImageFromWhat(1);
							}
							dialog.dismiss();
						}
					});
			getImageFrom.show();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			Log.d("Add Image ID", String.valueOf(requestCode));

			try {

				originBitmap = Images.Media.getBitmap(getContentResolver(),
						targetUri);

				int width = originBitmap.getWidth() / 100;
				int height = originBitmap.getHeight() / 100;
				int size;
				if (width > height) {
					size = height;
				} else {
					size = width;
				}

				Log.d("compare size", String.valueOf(size));

				originBitmap = resizeBitmap(originBitmap, size * 100);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// originBitmap = decodeUri(targetUri);
			int Measuredwidth = 0;
			Point size = new Point();
			WindowManager w = getWindowManager();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				w.getDefaultDisplay().getSize(size);
				Measuredwidth = size.x;
			} else {
				Display d = w.getDefaultDisplay();
				Measuredwidth = d.getWidth();
			}

			postImage.setImageBitmap(resizeBitmap(originBitmap, (Measuredwidth/3)*2));
		} else {
			Log.d("RESULT_CANCEL", "CANCEL");
		}
	}

	public void getImageFromWhat(int cameraOrgallery) {
		if (cameraOrgallery == 1) {
			Intent galleryIntent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			galleryIntent.setType("image/*");
			startActivityForResult(galleryIntent, 0);
		} else {
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, 0);
		}
	}

	public void saveTask(View button) {
		EditText postTitlelField = (EditText) findViewById(R.id.postTitle);
		mPostTitle = postTitlelField.getText().toString();
		EditText postDescriptionField = (EditText) findViewById(R.id.postDescription);
		mPostDescription = postDescriptionField.getText().toString();
		EditText postAuthorField = (EditText) findViewById(R.id.postAuthor);
		mPostAuthor = postAuthorField.getText().toString();
		EditText postPasswordField = (EditText) findViewById(R.id.postPasswd);
		mPostPassword = postPasswordField.getText().toString();

		if (mPostTitle.length() == 0 || mPostAuthor.length() == 0
				|| mPostPassword.length() == 0) {
			// input fields are empty
			Toast.makeText(this, "빈 칸을 입력해주세요.", Toast.LENGTH_LONG).show();
			return;
		} else if (originBitmap == null) {
			Toast.makeText(this, "사진을 추가해 주세요", Toast.LENGTH_LONG).show();
		} else if (mPostAuthor.length() > 3) {
			Toast.makeText(this, "작성자 이름은 3글자 이하로 해주세요.", Toast.LENGTH_LONG)
					.show();
		} else {
			// everything is ok!
			CreateTaskTask createTask = new CreateTaskTask(
					BoardCreateActivity.this);
			createTask.setMessageLoading("새 글을 등록 중입니다");
			createTask.execute(NetworkInfo.CREATE_TASK_URL);
		}
	}

	private class CreateTaskTask extends UrlJsonAsyncTask {
		public CreateTaskTask(Context context) {
			super(context);
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urls[0]);
			JSONObject holder = new JSONObject();
			JSONObject taskObj = new JSONObject();
			String response = null;
			JSONObject json = new JSONObject();

			ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
			Bitmap imageBitmap = originBitmap;
			imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageStream);
			byte[] data = imageStream.toByteArray();
			String imageData = new String(Base64.encode(data, 1));
			try {
				imageStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				try {
					json.put("success", false);
					json.put("info", "Something went wrong. Retry!");
					taskObj.put("title", mPostTitle);
					taskObj.put("description", mPostDescription);
					taskObj.put("author", mPostAuthor);
					taskObj.put("password", mPostPassword);
					taskObj.put("image", imageData);
					holder.put("post", taskObj);
					StringEntity se = new StringEntity(holder.toString(),
							"utf-8");
					post.setEntity(se);
					post.setHeader("Accept", "application/json");
					post.setHeader("Content-Type", "application/json");
					// post.setHeader("Authorization", "Token token="
					// + mPreferences.getString("AuthToken", ""));

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					response = client.execute(post, responseHandler);
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
					finish();

					Intent createTaskSuccessIntent = new Intent(
							getApplicationContext(), BoardIndexActivity.class);
					startActivity(createTaskSuccessIntent);
				}
				Toast.makeText(context, json.getString("info"),
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
						.show();
			} finally {
				super.onPostExecute(json);
				finish();
			}
		}
	}

	private Bitmap resizeBitmap(Bitmap originBitmap, int size) {
		int height = originBitmap.getHeight();
		int width = originBitmap.getWidth();

		Bitmap resized = null;
		while (height > size) {
			resized = Bitmap.createScaledBitmap(originBitmap, (width * size)
					/ height, size, true);
			height = resized.getHeight();
			width = resized.getWidth();
		}

		return resized;
	}
}