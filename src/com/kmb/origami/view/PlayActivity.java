package com.kmb.origami.view;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.devsmart.android.ui.HorizontalListView;
import com.kmb.origami.R;
import com.kmb.origami.model.PlaySingleItem;
import com.kmb.origami.model.PlaySingleItemAdapter;

public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		ArrayList<PlaySingleItem> playSingleItemArray = new ArrayList<PlaySingleItem>();
		PlaySingleItemAdapter playSingleItemAdapter = null;
		HorizontalListView playSingleItemListView = null;

		for (int i = 1; i <= 5; i++) {
			playSingleItemArray.add(new PlaySingleItem(i));
		}

		playSingleItemListView = (HorizontalListView) findViewById(R.id.play_item_image_list_view);

		// playSingleItemListView .setOnItemClickListener(new
		// playSingleItemListener());
		playSingleItemAdapter = new PlaySingleItemAdapter(PlayActivity.this,
				playSingleItemArray);
		playSingleItemListView.setAdapter(playSingleItemAdapter);

	}

}
