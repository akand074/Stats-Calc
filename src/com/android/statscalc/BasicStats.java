package com.android.statscalc;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class BasicStats extends Activity {
	private String dataValues = "";
	private EditText eMean;
	private EditText eMedian;
	private EditText eNumSamples;
	private EditText eSum;
	private EditText eStandardDeviation;
	private EditText eMin;
	private EditText eMax;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);
        
        eMean = (EditText) findViewById(R.id.eMean);
        eMedian = (EditText) findViewById(R.id.eMedian);
    	eNumSamples = (EditText) findViewById(R.id.eNumSamples);
    	eSum = (EditText) findViewById(R.id.eSum);
    	eStandardDeviation = (EditText) findViewById(R.id.eStandardDeviation);
    	eMin = (EditText) findViewById(R.id.eMin);
    	eMax = (EditText) findViewById(R.id.eMax);
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
        switch ( item.getItemId() ) {
        	case R.id.mManageData:
        		Intent activityIntent = new Intent(this, DataManagement.class);
            	activityIntent.putExtra("resultRequired", true);
        		startActivityForResult(activityIntent,1);
        		break;
        }
		return true;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
   		super.onActivityResult(requestCode, resultCode, data);
   		
   		if ( resultCode != RESULT_OK)
   			return;
   		
		dataValues = data.getStringExtra("dataValues");

   		analyzeData();
    }
    
    private void analyzeData(){    	
    	String[] arrData = dataValues.split(";");

    	DescriptiveStatistics stats = new DescriptiveStatistics();
    	
    	for (int i = 0; i < arrData.length; i++) {
    		String[] dataPoint = arrData[i].split(",");
    		stats.addValue( Double.parseDouble( dataPoint[1] ) );
		}
    	
    	// Compute some statistics
    	eMean.setText( (CharSequence) String.valueOf( stats.getMean() ) );
    	eMedian.setText( (CharSequence) String.valueOf( stats.getPercentile(50) ) );
    	eNumSamples.setText( (CharSequence) String.valueOf( stats.getN() ) );
    	eSum.setText( (CharSequence) String.valueOf( stats.getSum() ) );
    	eStandardDeviation.setText( (CharSequence) String.valueOf( stats.getStandardDeviation() ) );
    	eMin.setText( (CharSequence) String.valueOf( stats.getMin() ) );
    	eMax.setText( (CharSequence) String.valueOf( stats.getMax() ) );
    }
}