package com.android.statscalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class StatsCalcMain extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void launchActivity(View view){
    	Class<?> activityToLaunch = null;
    	String buttonTag = (String) view.getTag();
    	
    	if ( buttonTag.equals("BasicStats") )
    		activityToLaunch = BasicStats.class;
    	else if ( buttonTag.equals("DiscreteStats") )
    		activityToLaunch = DiscreteStats.class;
    	else if ( buttonTag.equals("LinearRegression") )
    		activityToLaunch = LinearRegression.class;
		else if ( buttonTag.equals("PermutationsCombinations") )
    		activityToLaunch = PermutationsCombinations.class;
    	

    	Intent activityIntent = new Intent(this, activityToLaunch);
    	startActivity(activityIntent);
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
        	case R.id.mManageData:
        		Intent activityIntent = new Intent(this, DataManagement.class);
            	startActivity(activityIntent);
        		break;
        }
		return true;
    }
}