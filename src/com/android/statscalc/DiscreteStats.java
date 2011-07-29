package com.android.statscalc;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class DiscreteStats extends Activity {
	private String dataValues = "";
	private static String lastSelection = "";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discrete_stats);

    	((Spinner) findViewById(R.id.sDistributionSelector)).setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View selectedItem,	int arg2, long arg3) {
				LayoutInflater inflater = getLayoutInflater();
				
				FrameLayout root = (FrameLayout) findViewById(R.id.fDistribution);

				String selection = (String) ((TextView) selectedItem).getText();
				
				if ( lastSelection.equals(selection) )
					root.removeAllViews();
				
				lastSelection = selection;
				
				if ( selection.equals( "Gaussian" ) ){
					inflater.inflate( R.layout.distribution_gaussian, root );
				} else if ( selection.equals( "T" ) ){
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
    
    }
    
    
    public void gaussianDistributionOnClick(View view){    	
    	EditText eMean = (EditText) findViewById(R.id.eGaussianMean);
    	EditText eStandardDeviation = (EditText) findViewById(R.id.eGaussianStandardDeviation);
    	EditText eX = (EditText) findViewById(R.id.eGaussianX);
    	EditText eProbability = (EditText) findViewById(R.id.eGaussianProbability);
    	
    	if ( eMean.getText().length() < 1 || eStandardDeviation.getText().length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter a value for Mean and Standard Deviation.", Toast.LENGTH_SHORT);
    		return;
    	}
    	
    	NormalDistributionImpl distribution = new NormalDistributionImpl( Double.valueOf( eMean.getText().toString() ) , Double.valueOf( eStandardDeviation.getText().toString() ) );
    	
    	switch ( view.getId() ){
    	case R.id.bGaussianCalcP:
    		
    		if ( eX.getText().length() < 1 ){
    			Toast.makeText(getApplicationContext(), "Please enter a value for X", Toast.LENGTH_SHORT);
    			return;
    		}
    		
    		try {
				double probability = distribution.cumulativeProbability( Double.valueOf( eX.getText().toString()) );
				eProbability.setText( String.valueOf( probability ) );
    		} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MathException e) {
				e.printStackTrace();
			}
    		
    		break;
    	case R.id.bGaussianCalcX:
    		
    		if ( eProbability.getText().length() < 1 ){
    			Toast.makeText(getApplicationContext(), "Please enter a value for Probability", Toast.LENGTH_SHORT);
    			return;
    		}
    		
    		try {
				double x = distribution.inverseCumulativeProbability( Double.valueOf( eProbability.getText().toString() ) );
				eX.setText( String.valueOf( x ) );
    		} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MathException e) {
				e.printStackTrace();
			}
    		
    		break;    		
    	}
    	
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
    public void onActivityResult(int requestCode,int resultCode,Intent data){
   		super.onActivityResult(requestCode, resultCode, data);

   		if ( resultCode != RESULT_OK )
   			return;
   		
   		if ( data.hasExtra("dataValues") )
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
    	    	
    	if ( lastSelection == "Gaussian" ){
	    	// Compute some statistics
	    	((EditText) findViewById(R.id.eGaussianMean)).setText( String.valueOf(stats.getMean()) );
	    	((EditText) findViewById(R.id.eGaussianStandardDeviation)).setText( String.valueOf(stats.getStandardDeviation()) );
    	}
    }
}