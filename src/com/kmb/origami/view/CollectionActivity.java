package com.kmb.origami.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.devsmart.android.ui.HorizontalListView;
import com.kmb.origami.R;
import com.kmb.origami.controller.CacheManager;
import com.kmb.origami.model.CollectionImage;
import com.kmb.origami.model.CollectionImageAdapter;

public class CollectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);

		ArrayList<CollectionImage> collectionPersonalImageArray = new ArrayList<CollectionImage>();
		ArrayList<CollectionImage> collectionStageImageArray = new ArrayList<CollectionImage>();
		CollectionImageAdapter collectionPersonalImageAdapter = null;
		CollectionImageAdapter collectionStageImageAdapter = null;
		HorizontalListView collectionPersonalImageListView = null;
		HorizontalListView collectionStageImageListView = null;

		collectionPersonalImageListView = (HorizontalListView) findViewById(R.id.collection_personal_image_list_view);

		int i = 1;
		while (true) {
			Bitmap result = null;
			try {
				result = CacheManager.retrieveDataForCollection(
						getApplicationContext(), String.valueOf(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			if (result == null) {
				break;
			}
		}

		for (int id = 1; id <= i; id++) {
			Bitmap personalImage = null;
			try {
				personalImage = CacheManager.retrieveDataForCollection(
						getApplicationContext(), String.valueOf(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			collectionPersonalImageArray.add(new CollectionImage(id, personalImage));
		}

		// collectionImageListView.setOnItemClickListener(new
		// CollectionImageListener());
		collectionPersonalImageAdapter = new CollectionImageAdapter(
				CollectionActivity.this, collectionPersonalImageArray);
		collectionPersonalImageListView
				.setAdapter(collectionPersonalImageAdapter);

		collectionStageImageListView = (HorizontalListView) findViewById(R.id.collection_stage_image_list_view);

		for (int tmp = 1; tmp <= 5; tmp++) {
			try {
				collectionStageImageArray.add(new CollectionImage(tmp, CacheManager.retrieveDataForCollection(
						getApplicationContext(), String.valueOf(1))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// collectionImageListView.setOnItemClickListener(new
		// CollectionImageListener());
		collectionStageImageAdapter = new CollectionImageAdapter(
				CollectionActivity.this, collectionStageImageArray);
		collectionStageImageListView.setAdapter(collectionStageImageAdapter);

	}
}