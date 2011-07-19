package com.android.statscalc;

import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        
        loadData();
    }
    
    public void saveData(View view){
    	
    	EditText eDataValues = (EditText) findViewById(R.id.eDataValues);
    	EditText eDataTitle = (EditText) findViewById(R.id.eDataTitle);
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	
    	settingsEditor.putString(eDataTitle.getText().toString(), eDataValues.getText().toString());
    	
    	settingsEditor.commit();
    	
    	loadData();
    }
    
    public void loadData(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	Map<String, String> savedData = (Map<String, String>) settings.getAll();
    	
    	ListView lSavedData = (ListView) findViewById(R.id.lDataList);
    	ArrayAdapter arrAdap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedData.keySet().toArray(new String[0]) );
    	
    	
    	lSavedData.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(getApplicationContext(), ((TextView) arg1).getText(), Toast.LENGTH_SHORT).show();
				
			}
		});
    	
    	lSavedData.setAdapter( arrAdap );
    }
}