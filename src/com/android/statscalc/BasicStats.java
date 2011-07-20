package com.android.statscalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BasicStats extends Activity {
	private String dataValues = "";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	case R.id.data_management:
        		Intent activityIntent = new Intent(this, DataManagement.class);
            	activityIntent.putExtra("resultRequired", true);
        		startActivityForResult(activityIntent,1);
        		break;
        }
		return true;
    }
    
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
   		super.onActivityResult(requestCode, resultCode, data);

   		dataValues = data.getStringExtra("dataValues");
   		analyzeData();
    }
    
    private void analyzeData(){
    	String[] arrData = dataValues.split(",");

    	float sum = 0;
    	float observations = arrData.length;
    	
    	for (int i = 0; i < arrData.length; i++) {
			float dataPoint = Float.parseFloat( arrData[i] );
		
			sum += dataPoint;
		}
    }
}