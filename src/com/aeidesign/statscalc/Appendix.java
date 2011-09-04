package com.aeidesign.statscalc;

import com.google.ads.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Appendix extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_appendix);
        
        Intent senderIntent = getIntent();
       
        int appendix_text_id = 0;
        
        if ( senderIntent.getExtras() != null )
        	appendix_text_id = senderIntent.getExtras().getInt("appendix_text_id");
        
        TextView tHelp = (TextView) findViewById(R.id.tAppendix);
        tHelp.setText( getText( appendix_text_id ) );        
	}

}
