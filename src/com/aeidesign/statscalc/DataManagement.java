package com.aeidesign.statscalc;

import java.util.Arrays;
import java.util.Comparator;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DataManagement extends Activity {
    public static final String PREFS_NAME = "SavedData";
    private LinearLayout lDataPoints;
    private int count;
    
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
        
        addDataPoint("", "");
        
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
    
    private String dataSetToString(){
    	StringBuilder dataValues = new StringBuilder();
    	LinearLayout lView;
    	count = 0;
    	
    	int arrLength = lDataPoints.getChildCount() - 1; // -1 is for the + button 
    	
    	Double[][] dataMatrix = new Double[ arrLength ][ 2 ];
    	
    	for ( int i = 0; i < arrLength; i++ ){
    		lView = (LinearLayout) lDataPoints.getChildAt(i);
    		
    		try {
    			dataMatrix[i][0] =  Double.valueOf( ((EditText) lView.getChildAt(0)).getText().toString() );
    			dataMatrix[i][1] =  Double.valueOf( ((EditText) lView.getChildAt(1)).getText().toString() );
    		} catch ( NumberFormatException ex ){
    			continue;
    		}
    	}
    	
    	Arrays.sort( dataMatrix, new Comparator<Double[]>(){
    	    @Override
    	    public int compare(final Double[] first, final Double[] second){
    	    	if ( first[0] == null || second[0] == null )
    	    		return 1;
    	    	
    	    	return first[0].compareTo( second[0] );
    	    }
    	});
    	
    	for ( int i = 0; i < arrLength; i++ ){
    		if ( dataMatrix[i][0] == null || dataMatrix[i][1] == null )
    			continue;
    		
    		if ( (i + 1) < (arrLength) )
    			if ( dataMatrix[i][0].equals( dataMatrix[i+1][0] ) )
    				continue;
    		
    		dataValues.append( dataMatrix[i][0] + "," + dataMatrix[i][1] + ";" );
    		count++;
    	}
    	    	
    	return dataValues.toString();
    }
    
    public void saveDataSet(View view){
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	if ( eDataTitle.getText().toString().length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter a title before saving the data set", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	    	  	
    	String dataValues = dataSetToString(); 
    	
    	if ( dataValues.length() < 1 ){
    		Toast.makeText(getApplicationContext(), "Please enter valid data before saving", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	settingsEditor.putString( eDataTitle.getText().toString(), dataValues );
    	settingsEditor.commit();

    	updateListView();
    	loadDataSet(eDataTitle.getText().toString());
    }
    
    public void selectDataSet(View view){
    	String dataValues = dataSetToString();
    	if ( count < 2 ){
    		Toast.makeText(getApplicationContext(), "Please enter at least two data points", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	Intent intent = new Intent();
        intent.putExtra( "dataValues", dataValues );
        setResult(RESULT_OK, intent);
        
        finish();
    }
    
    public void loadDataSet(CharSequence dataKey){
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	// Clear old data & EditTexts from Horizontal Scroll View
    	lDataPoints.removeViews(0, lDataPoints.getChildCount() - 1 );
    	
    	String[] dataValues = settings.getString( dataKey.toString(), "" ).split(";");
    	
    	int setSize = dataValues.length;
    	
    	for (int i = 0; i < setSize; i++) {
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
    
    @SuppressWarnings("unchecked")
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