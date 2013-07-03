package com.kmb.origami.lib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.kmb.origami.R;

public class Result extends Activity {
	
	String result_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_result);
	    
	    result_id = getIntent().getStringExtra("id");
	    
	    Log.i("TAG","id : " + result_id);
	    
	}

}
