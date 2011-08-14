package com.android.statscalc;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DataManagement extends Activity {
    public static final String PREFS_NAME = "SavedData";
    private LinearLayout lDataPoints;
    
    private OnFocusChangeListener DataPointFocus = new OnFocusChangeListener(){
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if ( hasFocus )
				((LinearLayout) v.getParent()).getChildAt(2).setVisibility(View.VISIBLE);
			else
				((LinearLayout) v.getParent()).getChildAt(2).setVisibility(View.INVISIBLE);
		}
	};
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_management);
        
        boolean resultRequired = false;

        Intent senderIntent = getIntent();
        if ( senderIntent.getExtras() != null )
        	resultRequired = senderIntent.getExtras().getBoolean("resultRequired", false);
        
        if ( resultRequired )
        	((Button) findViewById(R.id.bSelectData)).setVisibility(0);
        
        lDataPoints = (LinearLayout) findViewById(R.id.lDataPoints);
        
        addDataPoint("1", "4");
        addDataPoint("2", "5");
        addDataPoint("3", "6");
        
        updateListView();
    }
    
    public void addDataPoint(CharSequence x, CharSequence y){
    	LayoutInflater inflater = LayoutInflater.from(this);
    	LinearLayout nextPoints = (LinearLayout) inflater.inflate(R.layout.data_point, null);
    	
    	lDataPoints.addView(nextPoints, lDataPoints.getChildCount() - 1);
    	
    	EditText eX = ((EditText) nextPoints.getChildAt(0));
    	EditText eY = ((EditText) nextPoints.getChildAt(1));
    	
    	eX.setText(x);
    	eY.setText(y);
    	
    	eX.setOnFocusChangeListener(DataPointFocus);
    	eY.setOnFocusChangeListener(DataPointFocus);
    	eX.requestFocus();
    }
    
    public void addDataPoint(View v){
    	addDataPoint("", "");
    }
    
    public void deleteDataPoint(View v){
    	((LinearLayout) v.getParent().getParent()).removeView((View) v.getParent());
    }
    
    public void saveDataSet(View view){
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	if ( eDataTitle.getText().length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter a valid title fot the data set.", Toast.LENGTH_SHORT);
    		return;
    	}
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	
    	StringBuilder dataValues = new StringBuilder();
    	LinearLayout lView;
    	
    	for (int i = 0; i < lDataPoints.getChildCount() - 1; i++){
    		lView = (LinearLayout) lDataPoints.getChildAt(i);
    		
    		String dataPoint = (String) ( 
    										((EditText) lView.getChildAt(0)).getText() + "," +
    										((EditText) lView.getChildAt(1)).getText() + ";"
    									);
    		
    		dataPoint.replaceAll("[\\s]", "");
    		
    		if ( dataPoint.matches("^[+-]?\\d+[.]?\\d*,[+-]?\\d+[.]?\\d*;$") )
    			dataValues.append( dataPoint );
    	}
    	  	
    	settingsEditor.putString( eDataTitle.getText().toString(), dataValues.toString() );
    	settingsEditor.commit();

    	updateListView();
    }
    
    public void selectDataSet(View view){
    	StringBuilder dataValues = new StringBuilder();
    	LinearLayout lView;
    	
    	for (int i = 0; i < lDataPoints.getChildCount() - 1; i++){
    		lView = (LinearLayout) lDataPoints.getChildAt(i);
    		
    		String dataPoint = (String) ( 
    										((EditText) lView.getChildAt(0)).getText() + "," +
    										((EditText) lView.getChildAt(1)).getText() + ";"
    									);
    		
    		dataPoint.replaceAll("[\\s]", "");
    		
    		if ( dataPoint.matches("^[+-]?\\d+[.]?\\d*,[+-]?\\d+[.]?\\d*;$") )
    			dataValues.append( dataPoint );
    	}
    	
    	Intent intent = new Intent();
        intent.putExtra( "dataValues", dataValues.toString() );
        setResult(RESULT_OK, intent);
        
        finish();
    }
    
    public void loadDataSet(CharSequence dataKey){
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	// Clear old data & EditTexts from Horizontal Scroll View
    	lDataPoints.removeViews(0, lDataPoints.getChildCount() - 1 );
    	
    	String[] dataValues = settings.getString( dataKey.toString(), "" ).split(";");
    	
    	for (int i = 0; i < dataValues.length; i++) {
			String[] dataPoint = dataValues[i].split(",");
			
			addDataPoint( dataPoint[0], dataPoint[1] );
		}

    	eDataTitle.setText( dataKey );
    }
    
    public void deleteDataSet(CharSequence dataKey){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	
    	settingsEditor.remove( dataKey.toString() );
    	
    	settingsEditor.commit();

    	updateListView();
    }
    
    public void updateListView(){
		// Open stored preferences page
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	
		// Load stored data key/value pairs
    	Map<String, String> savedData = (Map<String, String>) settings.getAll();
    	
    	// Populate the list view in the activity with the stored data keys
    	ListView lSavedData = (ListView) findViewById(R.id.lDataList);
    	
    	ArrayAdapter<String> arrAdap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedData.keySet().toArray(new String[0]) );
    	lSavedData.setAdapter( arrAdap );
    	
    	lSavedData.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, final View arg1, int arg2, long arg3) {
		    	// Prepare a function selection menu for when the user selects a data item 
		    	AlertDialog.Builder alertBuilder = new AlertDialog.Builder( DataManagement.this );
				
				alertBuilder.setTitle("What would you like to do?");
				
				final CharSequence[] dialogOptions =  {"Load", "Delete"};
				
				alertBuilder.setItems( dialogOptions, new DialogInterface.OnClickListener() {
					
				    public void onClick(DialogInterface dialog, int selectedItem) {
				    	switch (selectedItem) {
						case 0: // Load Data
							loadDataSet( ((TextView) arg1).getText() );							
							Toast.makeText(getApplicationContext(), "Loaded " + ((TextView) arg1).getText() + " data", Toast.LENGTH_SHORT).show();
							break;

						case 1: // Delete data
							deleteDataSet( ((TextView) arg1).getText() );
							Toast.makeText(getApplicationContext(), "Deleted " + ((TextView) arg1).getText() + " data", Toast.LENGTH_SHORT).show();
							break;
				    	}   
				    }
				    
				});
				
				AlertDialog functionDialog = alertBuilder.create();	
				functionDialog.show();				
			}
		});
    }
}