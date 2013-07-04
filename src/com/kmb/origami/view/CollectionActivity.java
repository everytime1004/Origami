package com.kmb.origami.view;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kmb.origami.R;
import com.kmb.origami.controller.CacheManager;

public class CollectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		
		ImageView resultImage = (ImageView) findViewById(R.id.cache_image);
		
		Bitmap bitmap = null;
		try {
			bitmap = CacheManager.retrieveData(getApplicationContext(),
					"1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resultImage.setImageBitmap(bitmap);

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
