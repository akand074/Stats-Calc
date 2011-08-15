package com.android.statscalc;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class BasicStats extends Activity {
	private String dataValues = "";
	
	private TextView tNumSamples;
	private TextView tMin;
	private TextView tMax;
	private TextView tSum;
	private TextView tMean;
	private TextView tMedian;
	private TextView tStandardDeviation;
	private TextView tSkewness;
	private TextView tKurtosis;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);

        tNumSamples = (TextView) findViewById(R.id.tBasicNumSamples);
    	tMin = (TextView) findViewById(R.id.tBasicMin);
    	tMax = (TextView) findViewById(R.id.tBasicMax);
    	tSum = (TextView) findViewById(R.id.tBasicSum);
    	tMean = (TextView) findViewById(R.id.tBasicMean);
        tMedian = (TextView) findViewById(R.id.tBasicMedian);   	
    	tStandardDeviation = (TextView) findViewById(R.id.tBasicStandardDeviation);
    	tKurtosis = (TextView) findViewById(R.id.tBasicKurtosis);
    	tSkewness = (TextView) findViewById(R.id.tBasicSkewness);
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
    	
    	// Regular expression to format numbers to -123.4321
    	String regEx = "([+-]?\\d+\\.?\\d{0,4}).*";
    	
    	// Compute some statistics & format the result to 4 decimal places
    	// Display the results
    	tNumSamples.setText( String.valueOf( stats.getN() ) );
    	tMin.setText( String.valueOf( stats.getMin() ).replaceAll(regEx, "$1") );
    	tMax.setText( String.valueOf( stats.getMax() ).replaceAll(regEx, "$1") );
    	tSum.setText( String.valueOf( stats.getSum() ).replaceAll(regEx, "$1") );	
    	tMean.setText( String.valueOf( stats.getMean() ).replaceAll(regEx, "$1") );
    	tMedian.setText( String.valueOf( stats.getPercentile(50) ).replaceAll(regEx, "$1") );  	
    	tStandardDeviation.setText( String.valueOf( stats.getStandardDeviation() ).replaceAll(regEx, "$1") );
    	tKurtosis.setText( String.valueOf( stats.getKurtosis() ).replaceAll(regEx, "$1") );
    	tSkewness.setText( String.valueOf( stats.getSkewness() ).replaceAll(regEx, "$1") );
    }
}