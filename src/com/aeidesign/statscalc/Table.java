package com.aeidesign.statscalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Table extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tables);
        
        Intent senderIntent = getIntent();

        int table_id = 0;
        
        if ( senderIntent.getExtras() != null )
        	table_id = senderIntent.getExtras().getInt("table_id");
        
        WebView table = (WebView) findViewById(R.id.wTableView);
        table.getSettings().setLoadWithOverviewMode(true);
        table.getSettings().setUseWideViewPort(true);
        table.getSettings().setBuiltInZoomControls(true);
        table.getSettings().setSupportZoom(true);
        if (table_id == 1){
        	table.loadUrl("file:///android_asset/z_table.jpg");
        } else if (table_id == 2){
        	table.loadUrl("file:///android_asset/t_table.jpg");
        } else if (table_id == 3){
        	table.loadUrl("file:///android_asset/chi_table.jpg");
        }
        
	}

}
