package com.android.statscalc;


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
    	if(groupSize.getText().toString().equals("")){
    		groupSize = setSize;
    	}
    	long set = Long.parseLong(setSize.getText().toString());
    	long group = Long.parseLong(groupSize.getText().toString());
    	
    	permVal.setText((CharSequence) String.valueOf(calcPermutations(set, group)));
    	permWithRep.setText((CharSequence) String.valueOf(calcPermutationsWithRep(set, group)));
    	combVal.setText((CharSequence) String.valueOf(calcCombinations(set, group)));
    	combWithRep.setText((CharSequence) String.valueOf(calcCombinationsWithRep(set, group)));
    	numSubset.setText((CharSequence) String.valueOf(calcNumSubset(set)));
    	pigeonhole.setText((CharSequence) String.valueOf(calcPigeonhole(set, group)));
	}

	private long calcPigeonhole(long set, long group) {
		return set % group == 0? set/group : (set/group) + 1;
	}

	private long calcNumSubset(long set) {
		return (long) Math.pow(2, set);
	}

	private long calcCombinations(long set, long group) {
		return calcFactorial(set) / (calcFactorial(group) * calcFactorial(set - group)); 
	}

	private long calcCombinationsWithRep(long set, long group) {
		return calcFactorial(set + group - 1) / (calcFactorial(group)*calcFactorial(set - 1));
	}

	private long calcPermutationsWithRep(long set, long group) {
		return (long) Math.pow(set, group);
	}

	private long calcPermutations(long set, long group) {
		return calcFactorial(set) / calcFactorial(set - group);
	}

	private long calcFactorial(long value) {
		if(value == 0 || value == 1){
			return 1;
		}
		return value * calcFactorial(value - 1);		
	}
}