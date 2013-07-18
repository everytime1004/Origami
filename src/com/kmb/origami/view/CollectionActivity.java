package com.kmb.origami.view;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;
import com.kmb.origami.R;
import com.kmb.origami.controller.CacheManager;
import com.kmb.origami.model.CollectionImage;
import com.kmb.origami.model.CollectionImageAdapter;

public class CollectionActivity extends Activity {

	ArrayList<CollectionImage> collectionPersonalImageArray = new ArrayList<CollectionImage>();
	ArrayList<CollectionImage> collectionStageImageArray = new ArrayList<CollectionImage>();

	private SharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);

		mPreferences = getSharedPreferences("ImageNum", MODE_PRIVATE);

		CollectionImageAdapter collectionPersonalImageAdapter = null;
		CollectionImageAdapter collectionStageImageAdapter = null;
		HorizontalListView collectionPersonalImageListView = null;
		HorizontalListView collectionStageImageListView = null;

		collectionPersonalImageListView = (HorizontalListView) findViewById(R.id.collection_personal_image_list_view);

		// 리스트 뷰에 이미지 추가
		int numOfImage = mPreferences.getInt("numOfImage", 0);
		Log.d("numOfImage In CollectionActivity", String.valueOf(numOfImage));
		for (int id = 0; id <= 50; id++) {
			Bitmap personalImage = null;
			try {
				personalImage = CacheManager.retrieveDataForCollection(
						getApplicationContext(), String.valueOf(id));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (personalImage != null || id == 0) {
				// id가 0이면 collection부분에서 맨 왼쪽 연필 모양				
				collectionPersonalImageArray.add(new CollectionImage(id,
						personalImage, numOfImage));
			}
		}

		// 마지막 + 버튼 추가
		BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(
				R.drawable.icon);
		Bitmap lastImageAddButtonBitmap = drawable.getBitmap();
		collectionPersonalImageArray.add(new CollectionImage(numOfImage + 1,
				lastImageAddButtonBitmap, numOfImage));

		collectionPersonalImageListView
				.setOnItemClickListener(new CollectionImageListener());
		collectionPersonalImageListView
				.setOnItemLongClickListener(new DeleteImageListner());
		collectionPersonalImageAdapter = new CollectionImageAdapter(
				CollectionActivity.this, collectionPersonalImageArray);
		collectionPersonalImageListView
				.setAdapter(collectionPersonalImageAdapter);

		collectionStageImageListView = (HorizontalListView) findViewById(R.id.collection_stage_image_list_view);

		for (int tmp = 1; tmp <= 5; tmp++) {

		}

		// collectionImageListView.setOnItemClickListener(new
		// CollectionImageListener());
		collectionStageImageAdapter = new CollectionImageAdapter(
				CollectionActivity.this, collectionStageImageArray);
		collectionStageImageListView.setAdapter(collectionStageImageAdapter);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			Log.d("request Code", String.valueOf(requestCode));

			Bitmap picBitmap = null;

			try {
				picBitmap = decodeUri(targetUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int numOfImage = mPreferences.getInt("numOfImage", 0);

			addImageToCache(picBitmap);

			SharedPreferences.Editor editor = mPreferences.edit();
			// save the returned auth_token into
			// the SharedPreferences
			editor.putInt("numOfImage", numOfImage + 1);
			editor.commit();

			Log.d("imageNum", String.valueOf(numOfImage + 1));

			Intent resultIntent = new Intent(getApplicationContext(),
					CollectionActivity.class);
			startActivity(resultIntent);
			finish();

		} else {
			// imageId_arr[requestCode - 1] = 0;
			Log.d("RESULT_CANCEL", "CANCEL");
		}
	}

	private class CollectionImageListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parentView, View v,
				int position, long id) {
			Log.d("Array Size",
					String.valueOf(collectionPersonalImageArray.size()));
			Log.d("ImageListenr Parameter id",
					String.valueOf(id));

			if (mPreferences.getInt("numOfImage", 0) == 50) {
				Toast.makeText(getApplicationContext(), "최대 50개까지 추가가 가능합니다.",
						Toast.LENGTH_LONG);
			} else if (collectionPersonalImageArray.size() == id + 1) {
				Dialog dialog = new Dialog(CollectionActivity.this);

				// 타이틀바 없애기(setContentView 전에 선언해줘야 한다)
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialog.setContentView(R.layout.boast_upload_button);

				dialog.setCancelable(true);
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

				dialog.show();
			} else if (id != 0) {
				CollectionImage selectedImage = (CollectionImage) parentView
						.getItemAtPosition(position);
				Log.d("id", String.valueOf(selectedImage.getId()));
				Log.d("position", String.valueOf(position));

				if (selectedImage.getImage() != null) {

					Intent shareIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					shareIntent
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					shareIntent.setType("image/*");
					// For a file in shared storage. For data in private
					// storage,
					// use a ContentProvider.
					shareIntent.putExtra(
							Intent.EXTRA_STREAM,
							getImageUri(getApplicationContext(),
									selectedImage.getImage()));
					startActivity(Intent.createChooser(shareIntent, "공유합시다"));
				}
			}
		}
	}
	
	// 이미지 삭제
	private class DeleteImageListner implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parentView, View v,
				final int position, final long id) {
			// TODO Auto-generated method stub
			
			Log.d("Long Click Image id", String.valueOf(collectionPersonalImageArray.get(position).getId()));
			
			final Dialog dialog = new Dialog(CollectionActivity.this);
			dialog.setContentView(R.layout.task_remove_input);
			dialog.setTitle("삭제하시겠습니까?");

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
							Toast.makeText(getApplicationContext(), "삭제했습니다.",
									Toast.LENGTH_SHORT).show();

							CacheManager.delete(getApplicationContext(),
									String.valueOf(collectionPersonalImageArray.get(position).getId()));
							
							int numOfImage = mPreferences.getInt("numOfImage", 0);
							
							SharedPreferences.Editor editor = mPreferences.edit();
							// save the returned auth_token into
							// the SharedPreferences
							editor.putInt("numOfImage", numOfImage - 1);
							editor.commit();

							Log.d("imageNum", String.valueOf(numOfImage - 1));

							Intent resultIntent = new Intent(
									getApplicationContext(),
									CollectionActivity.class);
							startActivity(resultIntent);
							finish();

							dialog.dismiss();
						}
					});
			dialog.show();

			return false;
		}
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
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

	private void addImageToCache(Bitmap picImage) {
		for (int i = 1; i <= 50; i++) {
			Bitmap tmpImage = null;
			try {
				tmpImage = CacheManager.retrieveDataForCollection(
						getApplicationContext(), String.valueOf(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tmpImage == null){
				try {
					CacheManager.cacheDataForCollection(getApplicationContext(), picImage, String.valueOf(i));
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Toast.makeText(getApplicationContext(), "50개 이상 추가할 수 없습니다.", Toast.LENGTH_LONG).show();
	}
}