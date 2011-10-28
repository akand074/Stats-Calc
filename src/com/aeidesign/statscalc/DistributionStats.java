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
import android.widget.Button;
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
    	final EditText eOccurences = (EditText) findViewById(R.id.ePoissonX);
    	final EditText eLambda = (EditText) findViewById(R.id.ePoissonL);
    	final EditText eProbability = (EditText) findViewById(R.id.ePoissonP);
    	final EditText eProbabilityGTE = (EditText) findViewById(R.id.ePoissonPGT_E);
    	final EditText eProbabilityGT = (EditText) findViewById(R.id.ePoissonPGT);
    	final EditText eProbabilityLTE = (EditText) findViewById(R.id.ePoissonPLT_E);
    	final EditText eProbabilityLT = (EditText) findViewById(R.id.ePoissonPLT);
    	final Button bPoissonCalcP = ((Button) findViewById(R.id.bPoissonCalcP));
    	
    	if ( eOccurences.getText().length() < 1 || eLambda.getText().length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter a value for # of occurences and Lambda.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	if( Integer.valueOf(eOccurences.getText().toString()) > Integer.MAX_VALUE || Integer.valueOf(eLambda.getText().toString()) > Integer.MAX_VALUE  ){
    		Toast.makeText(getApplicationContext(), "Value for # of occurences or Lambda is too large.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	eProbability.setText("");
    	eProbabilityGTE.setText("");
    	eProbabilityGT.setText("");
    	eProbabilityLTE.setText("");
    	eProbabilityLT.setText("");
    	
    	final Poisson poisson = new Poisson();
    	bPoissonCalcP.setEnabled(false);
    	bPoissonCalcP.setText( "Calculating ..." );
    	
		new Thread(new Runnable() {
			public void run() {
				final double p = Functions.format(poisson.calcProbability(
						Integer.valueOf(eOccurences.getText().toString()),
						Double.valueOf(eLambda.getText().toString())));
				eProbability.post(new Runnable() {
					public void run() {
						eProbability.setText(String.valueOf(p));
					}
				});


				final double pGTE = Functions.format(poisson
						.calcProbabilityGreaterThanOrEqual(Integer
								.valueOf(eOccurences.getText().toString()),
								Double.valueOf(eLambda.getText().toString())));				
				eProbabilityGTE.post(new Runnable() {
					public void run() {
						eProbabilityGTE.setText(String.valueOf(pGTE));
					}
				});
			
				final double pGT = Functions.format(poisson
						.calcProbabilityGreaterThan(Integer.valueOf(eOccurences
								.getText().toString()), Double.valueOf(eLambda
								.getText().toString())));
				eProbabilityGT.post(new Runnable() {
					public void run() {
						eProbabilityGT.setText(String.valueOf(pGT));
					}
				});
		
				final double pLTE = Functions.format(poisson
						.calcProbabilityLessThanOrEqual(Integer
								.valueOf(eOccurences.getText().toString()),
								Double.valueOf(eLambda.getText().toString())));
				eProbabilityLTE.post(new Runnable() {
					public void run() {
						eProbabilityLTE.setText(String.valueOf(pLTE));
					}
				});
		
				final double pLT = Functions.format(poisson
						.calcProbabilityLessThan(Integer.valueOf(eOccurences
								.getText().toString()), Double.valueOf(eLambda
								.getText().toString())));
				eProbabilityLT.post(new Runnable() {
					public void run() {
						eProbabilityLT.setText(String.valueOf(pLT));
					}
				});
				
				bPoissonCalcP.post(new Runnable() {
					public void run() {
						bPoissonCalcP.setText( getString( R.string.calc ) );
						bPoissonCalcP.setEnabled(true);
					}
				});
			}
		}).start();
    }

    public void bernoulliDistributionOnClick(View view){
    	final EditText eNumSuccess = (EditText) findViewById(R.id.eBernoulliX);
    	final EditText eNumTrials = (EditText) findViewById(R.id.eBernoulliN);
    	final EditText ePercentSuccess = (EditText) findViewById(R.id.eBernoulliS);
    	final EditText eProbability = (EditText) findViewById(R.id.eBernoulliP);
    	final EditText eProbabilityGTE = (EditText) findViewById(R.id.eBernoulliPGT_E);
    	final EditText eProbabilityGT = (EditText) findViewById(R.id.eBernoulliPGT);
    	final EditText eProbabilityLTE = (EditText) findViewById(R.id.eBernoulliPLT_E);
    	final EditText eProbabilityLT = (EditText) findViewById(R.id.eBernoulliPLT);
    	final Button bBernoulliCalcP = ((Button) findViewById(R.id.bBernoulliCalcP));
    	
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
    	
    	eProbability.setText("");
    	eProbabilityGTE.setText("");
    	eProbabilityGT.setText("");
    	eProbabilityLTE.setText("");
    	eProbabilityLT.setText("");
   		
    	bBernoulliCalcP.setEnabled(false);
    	bBernoulliCalcP.setText( "Calculating ..." );
    	
		new Thread(new Runnable() {
			public void run() {
				final double p = Functions.format(Bernoulli.calcProbability(Integer.valueOf(eNumSuccess.getText().toString()), 
						Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) );
				eProbability.post(new Runnable() {
					public void run() {
						eProbability.setText(String.valueOf(p));
					}
				});


				final double pGTE = Functions.format(Bernoulli.calcProbabilityGreaterThanOrEqual(Integer.valueOf(eNumSuccess.getText().toString()), 
						Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) );			
				eProbabilityGTE.post(new Runnable() {
					public void run() {
						eProbabilityGTE.setText(String.valueOf(pGTE));
					}
				});
			
				final double pGT = Functions.format(Bernoulli.calcProbabilityGreaterThan(Integer.valueOf(eNumSuccess.getText().toString()), 
						Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) );
				eProbabilityGT.post(new Runnable() {
					public void run() {
						eProbabilityGT.setText(String.valueOf(pGT));
					}
				});
		
				final double pLTE = Functions.format(Bernoulli.calcProbabilityLessThanOrEqual(Integer.valueOf(eNumSuccess.getText().toString()), 
						Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) );
				eProbabilityLTE.post(new Runnable() {
					public void run() {
						eProbabilityLTE.setText(String.valueOf(pLTE));
					}
				});
		
				final double pLT = Functions.format(Bernoulli.calcProbabilityLessThan(Integer.valueOf(eNumSuccess.getText().toString()), 
						Integer.valueOf(eNumTrials.getText().toString()), Double.valueOf(ePercentSuccess.getText().toString()) / 100) );
				eProbabilityLT.post(new Runnable() {
					public void run() {
						eProbabilityLT.setText(String.valueOf(pLT));
					}
				});
				
				bBernoulliCalcP.post(new Runnable() {
					public void run() {
						bBernoulliCalcP.setText( getString( R.string.calc ) );
						bBernoulliCalcP.setEnabled(true);
					}
				});
			}
		}).start();
    	
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
        	menu.removeItem(R.id.mManageData);
        	menu.removeItem(R.id.mTable);
        } else {
        	menu.clear();
        	onCreateOptionsMenu(menu);
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
        	case R.id.mTable:
        		Intent tableIntent = new Intent(this, Table.class);
        		
        		int table_id = 0;
        		
        		if ( lastSelection.equals( "Gaussian" ) ){
					table_id = 1;
				} else if ( lastSelection.equals( "T" ) ){
					table_id = 2;
				} else if ( lastSelection.equals("ChiSquared")){
					table_id = 3;
				} 
        		
        		tableIntent.putExtra("table_id", table_id);
        		startActivity(tableIntent);
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