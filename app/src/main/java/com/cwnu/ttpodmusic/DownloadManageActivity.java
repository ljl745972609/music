package com.cwnu.ttpodmusic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DownloadManageActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_manage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download_manage, menu);
		return true;
	}

}
