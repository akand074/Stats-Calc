package com.android.statscalc;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.android.statscalc.stats.Functions;
import com.android.statscalc.stats.Regression;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;


public class LinearRegression extends Activity {
	private String dataValues = "";
	private XYSeries dataSeries;
	private XYSeries lobfSeries;
	private XYMultipleSeriesDataset multiSeriesDataset;
	private XYMultipleSeriesRenderer multiSeriesRenderer;
	private GraphicalView chart;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_regression);
        
        // Generate the graph
    	multiSeriesDataset = new XYMultipleSeriesDataset();
    	multiSeriesRenderer = new XYMultipleSeriesRenderer();
    	XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
    	XYSeriesRenderer lobfRenderer = new XYSeriesRenderer();
    	dataSeries = new XYSeries("Data Points");
    	lobfSeries = new XYSeries("Regression Line");
    	
    	lobfRenderer.setLineWidth(2);
    	lobfRenderer.setColor(Color.GREEN);
    	lobfRenderer.setPointStyle( PointStyle.CIRCLE );
    	
    	seriesRenderer.setLineWidth(0);
    	seriesRenderer.setColor( Color.BLUE );
    	seriesRenderer.setPointStyle( PointStyle.CIRCLE );
    	seriesRenderer.setFillPoints( true );
    	
    	multiSeriesRenderer.setAxesColor( Color.WHITE );
    	multiSeriesRenderer.setYLabelsAlign( Align.RIGHT );
    	multiSeriesRenderer.setShowLegend( true );
    	multiSeriesRenderer.setLabelsTextSize( 16 );
    	multiSeriesRenderer.setShowAxes(true);
    	multiSeriesRenderer.setXLabels(4);
    	multiSeriesRenderer.setYLabels(4);
	
    	multiSeriesRenderer.addSeriesRenderer( seriesRenderer );
    	multiSeriesRenderer.addSeriesRenderer( lobfRenderer );
    	multiSeriesDataset.addSeries( dataSeries );
    	multiSeriesDataset.addSeries( lobfSeries );
    	
    	// Render the graph
    	chart = ChartFactory.getLineChartView(this, multiSeriesDataset, multiSeriesRenderer);
    	chart.setBackgroundColor(Color.WHITE);

    	((FrameLayout) findViewById(R.id.regression_graph)).addView( chart );
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
    	String[] temp = dataValues.split(";");
    	
    	dataSeries.clear();
    	lobfSeries.clear();
    	
    	double[][] dataPoints = new double[ temp.length ][ 2 ];
    	Regression regression = new Regression();
    	
    	for (int i = 0; i < temp.length; i++) {
    		String[] dataPoint = temp[i].split(",");
    		
    		dataPoints[i][0] = Double.parseDouble(dataPoint[0]);
    		dataPoints[i][1] = Double.parseDouble(dataPoint[1]);
    		
			regression.addXValue( dataPoints[i][0] );
			regression.addYValue( dataPoints[i][1] );
			
			dataSeries.add( dataPoints[i][0], dataPoints[i][1] );
    	}
    	
    	((TextView) findViewById(R.id.tNumDataPoints)).setText( getString(R.string.num_data_points) + " " + regression.getNumDataPoints());
    	((TextView) findViewById(R.id.tEquation)).setText( getString(R.string.equation) + " " + regression.getEquation());
    	((TextView) findViewById(R.id.tIntercept)).setText( getString(R.string.intercept) + " " + String.valueOf(Functions.format(regression.getIntercept())) );
    	((TextView) findViewById(R.id.tSlope)).setText( getString(R.string.slope) + " " + String.valueOf(Functions.format(regression.getSlope())) );
    	((TextView) findViewById(R.id.tSlopeError)).setText( getString(R.string.slope_standard_error) + " " + String.valueOf(Functions.format(regression.getSlopeError())) );
    	((TextView) findViewById(R.id.tRValue)).setText( getString(R.string.r_value) + " " + String.valueOf(Functions.format(regression.getR())) );
    	((TextView) findViewById(R.id.tRSquared)).setText( getString(R.string.r_squared) + " " + String.valueOf(Functions.format(regression.getRSquared())) );
    	((TextView) findViewById(R.id.tMeanSquareError)).setText( getString(R.string.mean_square_error) + " " + String.valueOf(Functions.format(regression.getMeanSquareError())) );
    	//((TextView) findViewById(R.id.tSignificance)).setText( getString(R.string.significance) + " " + String.valueOf(Functions.format(regression.getSignificance())) );
    	
    	lobfSeries.add( 0, regression.getIntercept() );
    	lobfSeries.add( dataPoints[0][0], dataPoints[0][0] * regression.getSlope() + regression.getIntercept()  );
    	lobfSeries.add( dataPoints[dataPoints.length - 1][0], dataPoints[dataPoints.length - 1][0] * regression.getSlope() + regression.getIntercept()  );
    	lobfSeries.setTitle( regression.getEquation() );
    	
    	chart.repaint();
    }
}