package com.android.statscalc;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.regression.SimpleRegression;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableRow;


public class LinearRegression extends Activity {
	private String dataValues = "";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_regression);
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
        switch ( item.getItemId() ) {
        	case R.id.mManageData:
        		Intent activityIntent = new Intent(this, DataManagement.class);
            	activityIntent.putExtra("resultRequired", true);
        		startActivityForResult(activityIntent,1);
        		break;
        }
		return true;
    }
    
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
   		super.onActivityResult(requestCode, resultCode, data);

   		if ( resultCode != RESULT_OK )
   			return;
   		
   		if ( data.hasExtra("dataValues") )
   			dataValues = data.getStringExtra("dataValues");
   		   		
   		analyzeData();
    }
    
    private void analyzeData(){
    	SimpleRegression regression = new SimpleRegression();
    	XYSeries series = new XYSeries("Data Points");
    	XYMultipleSeriesDataset multiSeriesDataset = new XYMultipleSeriesDataset();
    	XYMultipleSeriesRenderer multiSeriesRenderer = new XYMultipleSeriesRenderer();
    	XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
    	
    	String[] temp = dataValues.split(";");
    	double[][] dataPoints = new double[ temp.length ][ 2 ];
    	
    	for (int i = 0; i < temp.length; i++) {
			String[] dataPoint = temp[i].split(",");
			dataPoints[i][0] = Double.valueOf( dataPoint[0] );
			dataPoints[i][1] = Double.valueOf( dataPoint[1] );
			
			series.add( dataPoints[i][0], dataPoints[i][1] );
    	}
    	
    	seriesRenderer.setColor(Color.BLUE);
    	seriesRenderer.setPointStyle(PointStyle.CIRCLE);
    	
    	multiSeriesRenderer.addSeriesRenderer(seriesRenderer);
    	multiSeriesDataset.addSeries( series );
    	
    	
    	GraphicalView chart = ChartFactory.getScatterChartView(this, multiSeriesDataset, multiSeriesRenderer);
    	chart.setBackgroundColor(Color.WHITE);
    	((TableRow) findViewById(R.id.regression_graph)).removeAllViews();
    	((TableRow) findViewById(R.id.regression_graph)).addView(chart);
    	
    	regression.addData( dataPoints );
    	
    	regression.getN();
    	regression.getIntercept();
    	regression.getMeanSquareError();
    	regression.getSlope();
    	regression.getR();
    	regression.getRSquare();
    	regression.getSlopeStdErr();
    	try {
			regression.getSignificance();
		} catch (MathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}