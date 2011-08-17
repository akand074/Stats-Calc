package com.android.statscalc;


import com.android.statscalc.stats.PermComb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
    
    private void calculateData() {
    	long set = 0;
    	long group = 0;
    	if(groupSize.getText().toString().equals("")){
    		set = Long.parseLong(setSize.getText().toString());
        	group = Long.parseLong(setSize.getText().toString());
    	} else {
        	set = Long.parseLong(setSize.getText().toString());
        	group = Long.parseLong(groupSize.getText().toString());
    	}
    	
    	permVal.setText(String.valueOf(PermComb.calcPermutations(set, group)));
    	permWithRep.setText(String.valueOf(PermComb.calcPermutationsWithRep(set, group)));
    	combVal.setText(String.valueOf(PermComb.calcCombinations(set, group)));
    	combWithRep.setText(String.valueOf(PermComb.calcCombinationsWithRep(set, group)));
    	numSubset.setText(String.valueOf(PermComb.calcNumSubset(set)));
    	pigeonhole.setText(String.valueOf(PermComb.calcPigeonhole(set, group)));
	}

}