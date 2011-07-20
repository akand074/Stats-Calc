package com.android.statscalc;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DataManagement extends Activity {
    public static final String PREFS_NAME = "SavedData";

    
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

        
        updateListView();
    }
    
    public void saveData(View view){	
    	EditText eDataValues = (EditText) findViewById(R.id.eDataValues);
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor settingsEditor = settings.edit();

    	String dataValues = sanitizeData( eDataValues.getText().toString() );
    	
    	eDataValues.setText( (CharSequence) dataValues );
    	
    	settingsEditor.putString( eDataTitle.getText().toString(), dataValues );
    	settingsEditor.commit();

    	updateListView();
    }
    
    public void selectData(View view){
    	EditText eDataValues = (EditText) findViewById(R.id.eDataValues);
    	
    	Intent intent = new Intent();
        intent.putExtra( "dataValues", eDataValues.getText().toString() );
        setResult(RESULT_OK, intent);
        
        finish();
    }
    
    public void loadData(CharSequence dataKey){
    	EditText eDataValues = (EditText) findViewById(R.id.eDataValues);
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	String dataValue = settings.getString(dataKey.toString(), "1,2,3");
    	
    	eDataValues.setText( (CharSequence) dataValue );
    	eDataTitle.setText( dataKey );
    }
    
    public void deleteData(CharSequence dataKey){
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
							loadData( ((TextView) arg1).getText() );							
							Toast.makeText(getApplicationContext(), "Loaded " + ((TextView) arg1).getText() + " data", Toast.LENGTH_SHORT).show();
							break;

						case 1: // Delete data
							deleteData( ((TextView) arg1).getText() );
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
    
    private String sanitizeData(String dataValues){
    	// Remove all whitespaces
    	dataValues = dataValues.replaceAll("[\\s]", "");
    	// Convert ; to , in case user doesn't read instructions
    	dataValues = dataValues.replaceAll(";+", ",");
    	
    	
    	String[] splitString = dataValues.split(",");
    	
    	StringBuilder sanitizedData = new StringBuilder();
    	
    	for (int i = 0; i < splitString.length; i++) {
			// Check for valid data only a + or - prefix followed by a digit, and check for a floating point
			if ( splitString[i].matches("^[+-]?\\d+[.]?\\d*$") )
				sanitizedData.append(splitString[i]).append(",");
		}

    	sanitizedData.deleteCharAt( sanitizedData.length() - 1 );
    	
    	return sanitizedData.toString();
    }
}