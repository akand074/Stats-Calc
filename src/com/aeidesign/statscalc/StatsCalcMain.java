package com.aeidesign.statscalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.ads.*;

public class StatsCalcMain extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());

    }
    
    public void launchActivity(View view){
    	Class<?> activityToLaunch = null;
    	String buttonTag = (String) view.getTag();
    	
    	if ( buttonTag.equals("BasicStats") )
    		activityToLaunch = BasicStats.class;
    	else if ( buttonTag.equals("DistributionStats") )
    		activityToLaunch = DistributionStats.class;
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
    
    public boolean onPrepareOptionsMenu(Menu menu){
    	menu.removeItem(R.id.mHelp);
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