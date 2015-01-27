package de.teamgamma.cansat.app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;

/**
 * 
 * @author Alexander Brennecke
 * ragment that shows the options when a user want to change the path for file export
 * or value storing
 *
 */
public class OptionsChartviewFragment extends Fragment implements OnSeekBarChangeListener {
	//initialize some variables
	public static final String ARG_SLIDEMENU_VALUES = "slidemenu_values";
	LinearLayout mLinearLayout;
	TextView currentPointColor,currentLineColor,currentAreaColor;
	SeekBar pointSeekBar,lineSeekBar,areaSeekBar;
	SeekBar[] seekBarArray = {pointSeekBar,lineSeekBar,areaSeekBar};
	int[] seekBarValue = new int[3];
	int[] seekBarIdArray = {R.id.pointSeekBar,R.id.lineSeekBar,R.id.areaSeekBar};
	TextView[] textViewArray = {currentPointColor,currentLineColor,currentAreaColor};
	int[] textViewIdArray = {R.id.selectedPointColor,R.id.selectedLinesColor,R.id.selectedAreaColor};

	public OptionsChartviewFragment() {
		// Empty constructor
	}

	
	/**
	 * called when the fragment is shown the first time
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// initialized variables and create object out of fragment xml objects
		final Options options = Options.getInstance();
		mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_options_chartview, container, false);
		Button button_save = (Button) mLinearLayout
				.findViewById(R.id.button_save);
		final EditText numberOfShownPoints = (EditText)mLinearLayout.findViewById(R.id.selectedNumber);
		numberOfShownPoints.setText(String.valueOf(options.getOption(KindOfOption.CHARTVIEW.ordinal(), ChartViewOptions.NUMBEROFSHOWNVALUE)),BufferType.EDITABLE);
		for(int i = 0; i<seekBarIdArray.length;i++){
			textViewArray[i]= (TextView)mLinearLayout.findViewById(textViewIdArray[i]);
			seekBarArray[i] = (SeekBar)mLinearLayout.findViewById(seekBarIdArray[i]);
			seekBarArray[i].setOnSeekBarChangeListener(this);
		}
		for(int i=0; i<seekBarIdArray.length;i++){
			onProgressChanged(seekBarArray[i],options.getOptions()[KindOfOption.CHARTVIEW.ordinal()].getColors()[i],false);
		}
		//called when the save button was clicked
		button_save.setOnClickListener(new OnClickListener() {
			/**
			 * gives the options to the option class
			 */
			@Override
			public void onClick(View v) {
				options.getOptions()[KindOfOption.CHARTVIEW.ordinal()].setColors(seekBarValue);
				options.setOption(KindOfOption.CHARTVIEW.ordinal(), ChartViewOptions.NUMBEROFSHOWNVALUE, numberOfShownPoints.getText().toString());
				// a toast will show that the data was saved
				Context context = mLinearLayout.getContext();
				CharSequence text = "Saved";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}

		});

		return mLinearLayout;
	}
	
	/**
	 * updates the color of the displaying chart
	 */
	
	private void updateColor(){
		for(int i = 0; i<seekBarIdArray.length;i++){
			seekBarValue[i]=seekBarArray[i].getProgress();
			textViewArray[i].setBackgroundColor(constantValues.selectableColors[seekBarValue[i]]);
		}

		
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		seekBar.setProgress(progress);
		updateColor();

		
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