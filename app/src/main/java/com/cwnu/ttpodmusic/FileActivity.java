package com.cwnu.ttpodmusic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class FileActivity extends Activity {

	
	 private ImageView imgbg0;
	 @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_file);
			setupview();
		}
		private void setupview() {
			// TODO Auto-generated method stub
			imgbg0=(ImageView) findViewById(R.id.img_Oone);
		}


}
