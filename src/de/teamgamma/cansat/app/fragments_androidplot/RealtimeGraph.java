package de.teamgamma.cansat.app.fragments_androidplot;

import java.util.Arrays;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.sensors.Sensor;


/**
 * 
 * @author Alexander Brennecke
 * 
 * generates a RealtimeGraph which can displays the values in real time
 *
 */
public class RealtimeGraph extends Fragment {
	private XYPlot plot;
	private XYSeries series1 = null;
	private int counter = 0;
	private LineAndPointFormatter series1Format;
	
	private Options options = Options.getInstance();
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// fun little snippet that prevents users from taking screenshots
		// on ICS+ devices :-)
		final LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.realtime_androidplot_xyplot, container, false);

		// initialize our XYPlot reference:
		plot = (XYPlot) mLinearLayout.findViewById(R.id.simpleXYPlot);
		plot.setTitle(options.getOption(KindOfOption.CHARTVIEW.ordinal(),ChartViewOptions.ACTIVESENSORNAME));

		// Create a couple arrays of y-values to plot:
		Number[] series1Numbers = new Number[Integer.valueOf(options.getOption(
				KindOfOption.CHARTVIEW.ordinal(),
				ChartViewOptions.NUMBEROFSHOWNVALUE))];
		for (Number a : series1Numbers) {
			a = 0;
		}

		// Turn the above arrays into XYSeries':
		series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), // SimpleXYSeries
																	// takes a
																	// List so
																	// turn our
																	// array
																	// into a
																	// List
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use
																// the element index as
																// the x value
				options.getOption(KindOfOption.CHARTVIEW.ordinal(),
						ChartViewOptions.ACTIVESENSORNAME)); // Set the display
																// title of the
																// series

		// Create a formatter to use for drawing a series using
		// LineAndPointRenderer
		// and configure it from xml:
//		series1Format = new LineAndPointFormatter(
//				constantValues.selectableColors[options.getOptions()[KindOfOption.CHARTVIEW
//						.ordinal()].getColors()[1]], // Line color
//				constantValues.selectableColors[options.getOptions()[KindOfOption.CHARTVIEW
//						.ordinal()].getColors()[0]], // Point color
//				constantValues.selectableColors[options.getOptions()[KindOfOption.CHARTVIEW
//						.ordinal()].getColors()[2]], // Area color
//				null);
		series1Format = new LineAndPointFormatter(Color.BLUE,Color.RED,Color.YELLOW,
				null);

		// add a new series' to the xyplot:
		plot.addSeries(series1, series1Format);

		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.getGraphWidget().setDomainLabelOrientation(-45);

		return mLinearLayout;
	}
	
	/**
	 * updates the chart with the new current values
	 * @param sensor sensor which should be displayed
	 */

	public synchronized void onValueChanged(Sensor sensor) {
		// get rid the oldest sample in history:
		if (series1.size() > Integer.valueOf(options.getOption(KindOfOption.CHARTVIEW.ordinal(), ChartViewOptions.NUMBEROFSHOWNVALUE))) {
			((SimpleXYSeries) series1).removeFirst();
		}
		// add the latest history sample:
		//Log.d("gamma", String.valueOf(sensor.getValues()[19][0]) + "    " + String.valueOf(sensor.getValues()[19][1]));
		((SimpleXYSeries) series1).addLast(sensor.getValues()[19][0],sensor.getValues()[19][1]);
		counter = counter +1 ;
		// redraw the Plots:
		plot.redraw();
	}
}
