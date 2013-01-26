package com.aeidesign.statscalc;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aeidesign.statscalc.stats.PermComb;

public class PermutationsCombinations extends Activity {
	private EditText setSize;
	private EditText groupSize;
	private EditText permVal;
	private EditText permWithRep;
	private EditText combVal;
	private EditText combWithRep;
	private EditText numSubset;
	private EditText pigeonhole;
	private Button calc;
	
	private OnClickListener clickCalc = new OnClickListener(){
		public void onClick(View v) {
		      calculateData();
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permutations_combinations);
        
        setSize = (EditText) findViewById(R.id.setSize);
        groupSize = (EditText) findViewById(R.id.groupSize);
        permVal = (EditText) findViewById(R.id.permVal);
        permWithRep = (EditText) findViewById(R.id.permWithRep);
        combVal = (EditText) findViewById(R.id.combVal);
        combWithRep = (EditText) findViewById(R.id.combWithRep);
        numSubset = (EditText) findViewById(R.id.numSubset);
        pigeonhole = (EditText) findViewById(R.id.pigeonhole);
        calc = (Button) findViewById(R.id.perComCalc);
        calc.setOnClickListener(clickCalc);       
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onPrepareOptionsMenu(Menu menu){
    	menu.removeItem(R.id.mManageData);
        menu.removeItem(R.id.mTable);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch ( item.getItemId() ) {
        	case R.id.mHelp:
        		Intent helpIntent = new Intent(this, Appendix.class);
        		helpIntent.putExtra("appendix_text_id", R.string.permcomb_help);
        		startActivity(helpIntent);
        		break;
        }
		return true;
    }
    
    private void calculateData() {
    	int set = 0;
    	int group = 0;
    	
    	if ( setSize.getText().toString().equals("")){
    		Toast.makeText(getApplicationContext(), "Please enter a value for the set", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	if(groupSize.getText().toString().equals("")){
    		set = Integer.parseInt(setSize.getText().toString());
        	group = Integer.parseInt(setSize.getText().toString());
    	} else {
        	set = Integer.parseInt(setSize.getText().toString());
        	group = Integer.parseInt(groupSize.getText().toString());
    	}
    	
    	if ( group > set){
    		Toast.makeText(getApplicationContext(), "The group cannot be larger than the set.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	permVal.setText("");
    	permWithRep.setText("");
    	combVal.setText("");
    	combWithRep.setText("");
    	numSubset.setText("");
    	pigeonhole.setText("");
    	
    	calc.setEnabled(false);
    	calc.setText( "Calculating ..." );
    	
    	final int setF = set;
    	final int groupF = group;
    	
		new Thread(new Runnable() {
			public void run() {
				final String perm = PermComb.calcPermutations(setF, groupF);
				permVal.post(new Runnable() {
					public void run() {
						permVal.setText(String.valueOf(perm));
					}
				});


				final String permR = PermComb.calcPermutationsWithRep(setF, groupF)		;	
				permWithRep.post(new Runnable() {
					public void run() {
						permWithRep.setText(String.valueOf(permR));
					}
				});
			
				final String comb = PermComb.calcCombinations(setF, groupF);
				combVal.post(new Runnable() {
					public void run() {
						combVal.setText(String.valueOf(comb));
					}
				});
				
				final String combR = PermComb.calcCombinationsWithRep(setF, groupF);
				combWithRep.post(new Runnable() {
					public void run() {
						combWithRep.setText(String.valueOf(combR));
					}
				});
				
				final String sub = PermComb.calcNumSubset(setF);
				numSubset.post(new Runnable() {
					public void run() {
						numSubset.setText(String.valueOf(sub));
					}
				});
		
				final int pigeon = PermComb.calcPigeonhole(setF, groupF);
				pigeonhole.post(new Runnable() {
					public void run() {
						pigeonhole.setText(String.valueOf(pigeon));
					}
				});
				
				calc.post(new Runnable() {
					public void run() {
						calc.setText( getString( R.string.calc ) );
						calc.setEnabled(true);
					}
				});
			}
		}).start();
	}

}