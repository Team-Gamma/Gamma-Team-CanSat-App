package de.teamgamma.cansat.app.fragments_androidplot;

import java.util.ArrayList;

import android.R.color;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.values.Values;

/**
 * 
 * @author Alexander Brennecke 
 * 
 * Shows a fragment which displays a SimpleXYChart
 *         of the selected import file or database
 */

public class ImportSimpleXYChart extends Fragment implements
		OnSeekBarChangeListener {

	private RelativeLayout mLinearLayout;
	private XYPlot plot;
	private Options options = Options.getInstance();
	private ArrayList<Values> allValues;
	private SeekBar timeSlider;
	private SeekBar numberOfShownValueSlider;
	private XYSeries series1 = null;
	private XYSeries highestAndLowestPoints = null;
	private int numberOfShownValues;
	int highestValue;
	int lowestValue;
	boolean layoutCreated = false;
	
	/**
	 * method to set the ArrayList with all values, which can be shown
	 */

	public void setValue(ArrayList<Values> values) {		
		allValues = values;
		numberOfShownValues = allValues.size();
		lowestValue = allValues.get(0).getValues()[1].intValue();
		highestValue = allValues.get(0).getValues()[1].intValue();
		for (int i = 0; i < allValues.size(); i++) {
			Log.d("gamma",String.valueOf(allValues.get(i).getValues()[0].intValue()));
			if (allValues.get(i).getValues()[1].intValue() > highestValue) {
				highestValue = allValues.get(i).getValues()[1].intValue();
			}
			if (allValues.get(i).getValues()[1].intValue() < lowestValue) {
				lowestValue = allValues.get(i).getValues()[1].intValue();
			}
		}
		createFragment();
	}

	/**
	 * called when an object of this class will be created
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// LinearLayout object, of the androitplot_xyplot.xml fragment
		mLinearLayout = (RelativeLayout) inflater.inflate(
				R.layout.import_androidplot_xyplot, container, false);
		layoutCreated = true;
		return mLinearLayout;
	}
	

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		updateSeries(numberOfShownValueSlider.getProgress(),
				timeSlider.getProgress());
		numberOfShownValues = numberOfShownValueSlider.getProgress();

	}
	
	/**
	 * called when the fragments get the values it should display
	 */

	private void createFragment(){
		numberOfShownValueSlider = (SeekBar) mLinearLayout
				.findViewById(R.id.numberOfShownValueSeekBar);
		numberOfShownValueSlider.setMax(allValues.size());
		numberOfShownValueSlider.setProgress(numberOfShownValueSlider.getMax());
		numberOfShownValueSlider.setOnSeekBarChangeListener(this);

		timeSlider = (SeekBar) mLinearLayout.findViewById(R.id.seekBar1);
		timeSlider.setMax(allValues.size() - numberOfShownValues);
		timeSlider.setProgress(timeSlider.getMax());
		timeSlider.setOnSeekBarChangeListener(this);

		// initialize the plot by using the XYPlot in the fragment
		plot = (XYPlot) mLinearLayout.findViewById(R.id.simpleXYPlot);

		// initialize a few importend variables to display the values correct

		// Make the seriesNumbers [] to an XYSeries
		series1 = new SimpleXYSeries(null,
				SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, ""); // Name of
																		// the
																		// series
		highestAndLowestPoints = new SimpleXYSeries(null,
				SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, "");
		((SimpleXYSeries) highestAndLowestPoints).addFirst(0, highestValue);
		((SimpleXYSeries) highestAndLowestPoints).addFirst(0, lowestValue);

		updateSeries(numberOfShownValues, 0);
		// Configures the graph
		LineAndPointFormatter series1Format = new LineAndPointFormatter(
				constantValues.selectableColors[options.getOptions()[KindOfOption.CHARTVIEW
						.ordinal()].getColors()[1]], // Line color
				constantValues.selectableColors[options.getOptions()[KindOfOption.CHARTVIEW
						.ordinal()].getColors()[0]], // Point color
				constantValues.selectableColors[options.getOptions()[KindOfOption.CHARTVIEW
						.ordinal()].getColors()[2]], // Area color
				null);

		LineAndPointFormatter highestPointFormatter = new LineAndPointFormatter(
				color.transparent, color.transparent, color.transparent, null);

		// add a new series' to the xyplot:
		plot.addSeries(series1, series1Format);
		plot.addSeries(highestAndLowestPoints, highestPointFormatter);
		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.getGraphWidget().setDomainLabelOrientation(-45);
	}
	
	/**
	 * updates the displaying series with the values transfered from the seekBars
	 */
	
	private void updateSeries(int numberOfShownValues, int timeSliderProgress) {
		int timeSliderMax = timeSliderProgress;
		if (numberOfShownValues != this.numberOfShownValues) {
			while (numberOfShownValues + timeSliderMax != allValues.size()) {
				if (numberOfShownValues + timeSliderMax > allValues.size()) {
					timeSliderMax = timeSliderMax - 1;
				} else if (numberOfShownValues + timeSliderMax < allValues
						.size()) {
					timeSliderMax = timeSliderMax + 1;
				}
			}
			timeSlider.setMax(timeSliderMax);
		}
		if (timeSliderProgress + numberOfShownValues > allValues.size()) {
			timeSliderProgress = timeSliderMax;
		}
		int arraySize = ((SimpleXYSeries) series1).size();
		for (int i = 0; i < arraySize; i++) {
			((SimpleXYSeries) series1).removeFirst();
		}
		for (int i = 0; i < numberOfShownValues; i++) {
			((SimpleXYSeries) series1).addLast(
					allValues.get(timeSliderProgress + i).getValues()[0]
							.intValue(), allValues.get(timeSliderProgress + i)
							.getValues()[1].intValue());
		}

		for (int i = 0; i < 2; i++) {
			((SimpleXYSeries) highestAndLowestPoints).removeFirst();
		}
		((SimpleXYSeries) highestAndLowestPoints).addFirst(timeSliderProgress,
				lowestValue);
		((SimpleXYSeries) highestAndLowestPoints).addFirst(timeSliderProgress,
				highestValue);

		// redraw the Plot:
		plot.redraw();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}