package com.aeidesign.statscalc;

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

import com.aeidesign.statscalc.stats.Bernoulli;
import com.aeidesign.statscalc.stats.ChiSquared;
import com.aeidesign.statscalc.stats.Descriptive;
import com.aeidesign.statscalc.stats.Functions;
import com.aeidesign.statscalc.stats.Gaussian;
import com.aeidesign.statscalc.stats.Poisson;
import com.aeidesign.statscalc.stats.T;

public class DistributionStats extends Activity {
	private String dataValues = "";
	private static String lastSelection = "";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribution_stats);

    	((Spinner) findViewById(R.id.sDistributionSelector)).setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View selectedItem,	int arg2, long arg3) {
				LayoutInflater inflater = getLayoutInflater();
				
				FrameLayout root = (FrameLayout) findViewById(R.id.fDistribution);

				String selection = (String) ((TextView) selectedItem).getText();
				root.removeAllViews();
				
				lastSelection = selection;
				
				if ( selection.equals( "Gaussian" ) ){
					inflater.inflate( R.layout.distribution_gaussian, root );
				} else if ( selection.equals( "T" ) ){
					inflater.inflate( R.layout.distribution_t, root );
				} else if ( selection.equals("Poisson")){
					inflater.inflate(R.layout.distribution_poisson, root);
				} else if ( selection.equals("ChiSquared")){
					inflater.inflate(R.layout.distribution_chisquared, root);
				} else if ( selection.equals("Bernoulli")){
					inflater.inflate(R.layout.distribution_bernoulli, root);
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
    		Toast.makeText(getApplicationContext(), "Please enter a value for Mean and Standard Deviation.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	switch ( view.getId() ){
    	case R.id.bGaussianCalcP:
    		
    		if ( eX.getText().length() < 1 ){
    			Toast.makeText(getApplicationContext(), "Please enter a value for X", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		
    		try {
				eProbability.setText( String.valueOf( Functions.format(Gaussian.calcProbability(Double.valueOf(eX.getText().toString()), 
						Double.valueOf(eMean.getText().toString()), Double.valueOf(eStandardDeviation.getText().toString())))) );
    		} catch (NumberFormatException e) {
				e.printStackTrace();
			}
    		
    		break;
    	case R.id.bGaussianCalcX:
    		
    		if ( eProbability.getText().length() < 1 ){
    			Toast.makeText(getApplicationContext(), "Please enter a value for Z", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		
    		try {
				eX.setText( String.valueOf( Functions.format(Gaussian.calcX(Double.valueOf(eProbability.getText().toString()), 
						Double.valueOf(eMean.getText().toString()), Double.valueOf(eStandardDeviation.getText().toString()))) ) );
    		} catch (NumberFormatException e) {
				e.printStackTrace();
			}
    		
    		break;    		
    	}
    	
    }
    
    public void poissonDistributionOnClick(View view){
    	EditText eOccurences = (EditText) findViewById(R.id.ePoissonX);
    	EditText eLambda = (EditText) findViewById(R.id.ePoissonL);
    	EditText eProbability = (EditText) findViewById(R.id.ePoissonP);
    	EditText eProbabilityGTE = (EditText) findViewById(R.id.ePoissonPGT_E);
    	EditText eProbabilityGT = (EditText) findViewById(R.id.ePoissonPGT);
    	EditText eProbabilityLTE = (EditText) findViewById(R.id.ePoissonPLT_E);
    	EditText eProbabilityLT = (EditText) findViewById(R.id.ePoissonPLT);
    	
    	if ( eOccurences.getText().length() < 1 || eLambda.getText().length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter a value for # of occurences and Lambda.", Toast.LENGTH_SHORT).show();
    		return;
    	}
   		
    	try {
			eProbability.setText( String.valueOf( Functions.format(Poisson.calcProbability(Integer.valueOf(eOccurences.getText().toString()), 
					Double.valueOf(eLambda.getText().toString()))) ) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityGTE.setText( String.valueOf( Functions.format(Poisson.calcProbabilityGreaterThanOrEqual(Integer.valueOf(eOccurences.getText().toString()),
					Double.valueOf(eLambda.getText().toString()))) ) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityGT.setText( String.valueOf( Functions.format(Poisson.calcProbabilityGreaterThan(Integer.valueOf(eOccurences.getText().toString()), 
					Double.valueOf(eLambda.getText().toString()))) ) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityLTE.setText( String.valueOf( Functions.format(Poisson.calcProbabilityLessThanOrEqual(Integer.valueOf(eOccurences.getText().toString()), 
					Double.valueOf(eLambda.getText().toString()))) ) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityLT.setText( String.valueOf( Functions.format(Poisson.calcProbabilityLessThan(Integer.valueOf(eOccurences.getText().toString()), 
					Double.valueOf(eLambda.getText().toString()))) ));
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	

    }
    
    public void bernoulliDistributionOnClick(View view){
    	EditText eNumSuccess = (EditText) findViewById(R.id.eBernoulliX);
    	EditText eNumTrials = (EditText) findViewById(R.id.eBernoulliN);
    	EditText ePercentSuccess = (EditText) findViewById(R.id.eBernoulliS);
    	EditText eProbability = (EditText) findViewById(R.id.eBernoulliP);
    	EditText eProbabilityGTE = (EditText) findViewById(R.id.eBernoulliPGT_E);
    	EditText eProbabilityGT = (EditText) findViewById(R.id.eBernoulliPGT);
    	EditText eProbabilityLTE = (EditText) findViewById(R.id.eBernoulliPLT_E);
    	EditText eProbabilityLT = (EditText) findViewById(R.id.eBernoulliPLT);
    	
    	if ( eNumSuccess.getText().length() < 1 || eNumTrials.getText().length() < 1 || ePercentSuccess.getText().length() < 1){
    		Toast.makeText(getApplicationContext(), "Please enter a value for # of Successes, # of Trials and % Success", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	if ( Double.valueOf(eNumSuccess.getText().toString()) > Double.valueOf(eNumTrials.getText().toString())){
    		Toast.makeText(getApplicationContext(), "You cannot have more successes than trials", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	if ( Double.valueOf(ePercentSuccess.getText().toString()) < 0 || Double.valueOf(ePercentSuccess.getText().toString()) > 100){
    		Toast.makeText(getApplicationContext(), "% success must be between 0 and 100", Toast.LENGTH_SHORT).show();
    		return;
    	}
   		
    	try {
			eProbability.setText( String.valueOf( Functions.format(Bernoulli.calcProbability(Integer.valueOf(eNumSuccess.getText().toString()), 
					Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) )) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityGTE.setText( String.valueOf( Functions.format(Bernoulli.calcProbabilityGreaterThanOrEqual(Integer.valueOf(eNumSuccess.getText().toString()), 
					Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) )) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityGT.setText( String.valueOf( Functions.format(Bernoulli.calcProbabilityGreaterThan(Integer.valueOf(eNumSuccess.getText().toString()), 
					Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) )) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityLTE.setText( String.valueOf( Functions.format(Bernoulli.calcProbabilityLessThanOrEqual(Integer.valueOf(eNumSuccess.getText().toString()), 
					Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) )) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    	try {
			eProbabilityLT.setText( String.valueOf( Functions.format(Bernoulli.calcProbabilityLessThan(Integer.valueOf(eNumSuccess.getText().toString()), 
					Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) )) );
    	} catch (NumberFormatException e) {
				e.printStackTrace();
		}
    }
    
    public void tDistributionOnClick(View view){    	
    	EditText eMean = (EditText) findViewById(R.id.eTMean);
    	EditText eStandardError = (EditText) findViewById(R.id.eTStandardError);
    	EditText eX = (EditText) findViewById(R.id.eTX);
    	EditText eT = (EditText) findViewById(R.id.eTT);
    	
    	if ( eMean.getText().length() < 1 || eStandardError.getText().length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter a value for Mean and Standard Deviation.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	switch ( view.getId() ){
    	case R.id.bTCalcT:
    		
    		if ( eX.getText().length() < 1 ){
    			Toast.makeText(getApplicationContext(), "Please enter a value for X", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		
    		try {
				eT.setText( String.valueOf( Functions.format(T.calcT( 	Double.valueOf( eX.getText().toString() ),
														Double.valueOf( eMean.getText().toString() ), 
														Double.valueOf( eStandardError.getText().toString() )
													) )) );
    		} catch (NumberFormatException e) {
				e.printStackTrace();
			}
    		
    		break;
    	case R.id.bTCalcX:
    		
    		if ( eT.getText().length() < 1 ){
    			Toast.makeText(getApplicationContext(), "Please enter a value for t", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		
    		try {
				eX.setText( String.valueOf( Functions.format(T.calcX(	Double.valueOf( eT.getText().toString() ),
														Double.valueOf( eMean.getText().toString() ), 
														Double.valueOf( eStandardError.getText().toString() )
													) ) ));
    		} catch (NumberFormatException e) {
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
    
    public boolean onPrepareOptionsMenu(Menu menu){
    	if (lastSelection.equals("Poisson") || lastSelection.equals("Bernoulli")){
        	menu.setGroupVisible(R.id.mManageData, false);
        }
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch ( item.getItemId() ) {
        	case R.id.mManageData:
        		Intent activityIntent = new Intent(this, DataManagement.class);
            	
        		// Only require result for some distributions
        		if ( lastSelection.equals("Gaussian") || lastSelection.equals("T") || lastSelection.equals("ChiSquared") )
        		activityIntent.putExtra("resultRequired", true);
            	
            	// Gaussian & T distribution only require unidimensional data
            	if ( lastSelection.equals("Gaussian") || lastSelection.equals("T") )
            		activityIntent.putExtra("unidimData", true);
            	
        		startActivityForResult(activityIntent,1);
        		break;
        	case R.id.mHelp:
        		Intent helpIntent = new Intent(this, Appendix.class);
        		
        		int appendix_text_id = 0;
        		
        		if ( lastSelection.equals( "Gaussian" ) ){
					appendix_text_id = R.string.gaussian_help;
				} else if ( lastSelection.equals( "T" ) ){
					appendix_text_id = R.string.t_help;
				} else if ( lastSelection.equals("Poisson")){
					appendix_text_id = R.string.poisson_help;
				} else if ( lastSelection.equals("ChiSquared")){
					appendix_text_id = R.string.chisquared_help;
				} else if ( lastSelection.equals("Bernoulli")){
					appendix_text_id = R.string.bernoulli_help;
				}
        		
        		helpIntent.putExtra("appendix_text_id", appendix_text_id);
        		startActivity(helpIntent);
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
    	int setSize = arrData.length;
    	
    	if (lastSelection.equals("Gaussian")){
        	Descriptive stats = new Descriptive();
        	
        	for (int i = 0; i < setSize; i++) {
        		String[] dataPoint = arrData[i].split(",");
        		stats.addValue( Double.parseDouble( dataPoint[1] ) );
    		}
    	    // Compute some statistics
    	    ((EditText) findViewById(R.id.eGaussianMean)).setText( String.valueOf(Functions.format(stats.getMean())) );
    	    ((EditText) findViewById(R.id.eGaussianStandardDeviation)).setText( String.valueOf(Functions.format(stats.getStandardDeviation())) );
    		
    	} else if (lastSelection.equals("T")){
        	Descriptive stats = new Descriptive();
        	
        	for (int i = 0; i < setSize; i++) {
        		String[] dataPoint = arrData[i].split(",");
        		stats.addValue( Double.parseDouble( dataPoint[1] ) );
    		}
    	    // Compute some statistics
    	    ((EditText) findViewById(R.id.eTMean)).setText( String.valueOf(Functions.format(stats.getMean())) );
    	    ((EditText) findViewById(R.id.eTStandardError)).setText( String.valueOf(Functions.format(stats.getStandardError())) );
    	} else if (lastSelection.equals("ChiSquared")){
    		ChiSquared statsX = new ChiSquared();
    		
    		for (int i = 0; i < setSize; i++){
    			String[] dataPointX = arrData[i].split(",");
    			statsX.addExpectedValue(Double.parseDouble(dataPointX[0]));
    			statsX.addObservedValue(Double.parseDouble(dataPointX[1]));
    		}
    		((EditText) findViewById(R.id.eChiSquared)).setText(String.valueOf(Functions.format(statsX.getChiSquared())));
    	}
    }
}