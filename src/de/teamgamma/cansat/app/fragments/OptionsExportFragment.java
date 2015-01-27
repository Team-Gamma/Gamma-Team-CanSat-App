package de.teamgamma.cansat.app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.PathOptions;
import de.teamgamma.cansat.app.options.Options;

/**
 * 
 * @author Alexander Brennecke
 * ragment that shows the options when a user want to change the path for file export
 * or value storing
 *
 */
public class OptionsExportFragment extends Fragment {
	//initialize some variables
	public static final String ARG_SLIDEMENU_VALUES = "slidemenu_values";
	private final Options options = Options.getInstance();
	private boolean[] browserButton = new boolean[2];
	LinearLayout mLinearLayout;

	public OptionsExportFragment() {
		// Empty constructor
	}

	/**
	 * when the fileBrowser finished and the fragment will shown again the onResume will be called
	 * and update the TextView fields inside the fragment
	 */
	@Override
	public void onResume() {

		
		super.onResume();

		// update the TextViews
		final EditText exportPath = (EditText) mLinearLayout
				.findViewById(R.id.exportPath);
		final EditText storagePath = (EditText) mLinearLayout
				.findViewById(R.id.storagePath);

		
		for (int i = 1; i < browserButton.length; i++) {
			if (browserButton[i]) {
				browserButton[i] = false;
				switch (i) {
				case 1:
					exportPath.setText(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.TEMPVALUEEXPORTPATH));
					break;
				case 2:
					storagePath.setText(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.TEMPVALUESTORAGEPATH));
					break;
				}
			} else {
				switch (i) {
				case 1:
					exportPath.setText(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.VALUEEXPOTPATH));
					break;
				case 2:
					storagePath.setText(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.VALUESTORAGEPATH));
					break;
				}
			}
		}
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
				R.layout.fragment_options_export, container, false);
		Button button_save = (Button) mLinearLayout
				.findViewById(R.id.button_save);
		Button button_browser_export = (Button) mLinearLayout
				.findViewById(R.id.button_browser_export);
		Button button_browser_storage = (Button) mLinearLayout
				.findViewById(R.id.button_browser_storage);
		final EditText exportPath = (EditText) mLinearLayout
				.findViewById(R.id.exportPath);
		final EditText storagePath = (EditText) mLinearLayout
				.findViewById(R.id.storagePath);
		
		// set text to TextViews
		exportPath.setText(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.VALUEEXPOTPATH));
		storagePath.setText(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.VALUESTORAGEPATH));
		
		//called when the save button was clicked
		button_save.setOnClickListener(new OnClickListener() {
			/**
			 * gives the options to the option class
			 */
			@Override
			public void onClick(View v) {
				options.setOption(KindOfOption.PATH.ordinal(), PathOptions.VALUEEXPOTPATH, exportPath.getText().toString());
				options.setOption(KindOfOption.PATH.ordinal(), PathOptions.VALUESTORAGEPATH, storagePath.getText().toString());
			}

		});

		//called when the browser button was clicked
		button_browser_export.setOnClickListener(new OnClickListener() {
			/**
			 * opens a new activity with the file browser
			 */
			@Override
			public void onClick(View v) {
				options.setOption(KindOfOption.CHARTVIEW.ordinal(),ChartViewOptions.LASTACTIVESCREEN,1);
				Intent intent = new Intent(
						getActivity(),
						de.teamgamma.cansat.app.filebrowser.AndroidExplorer.class);
				// starts the activity
				startActivity(intent);
			}
		});
		
		// //called when the browser button was clicked
		button_browser_storage.setOnClickListener(new OnClickListener() {
			/**
			 * opens a new activity with the file browser
			 */
			@Override
			public void onClick(View v) {
				options.setOption(KindOfOption.CHARTVIEW.ordinal(),ChartViewOptions.LASTACTIVESCREEN,2);
				Intent intent = new Intent(
						getActivity(),
						de.teamgamma.cansat.app.filebrowser.AndroidExplorer.class);

				// starts the activity
				startActivity(intent);
			}
		});

		return mLinearLayout;
	}
}