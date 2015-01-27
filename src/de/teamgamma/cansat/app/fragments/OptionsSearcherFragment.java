package de.teamgamma.cansat.app.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.options.GenerateOptions;
import de.teamgamma.cansat.app.options.KindOfOption;

import de.teamgamma.cansat.app.options.OptionsExport;
import de.teamgamma.cansat.app.options.PathOptions;
import de.teamgamma.cansat.app.options.Options;

/**
 * 
 * @author Alexander Brennecke gives the user the option to select an own
 *         options.txt or to generate a new one
 * 
 */
public class OptionsSearcherFragment extends Fragment {

	private Options options = Options.getInstance();

	/**
	 * called when the fragment will be displayed first time
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_option_searcher, container, false);
		final EditText optionsPath = (EditText) mLinearLayout
				.findViewById(R.id.editJavaSocketIPAdress);
		Button buttonGenerateOptions = (Button) mLinearLayout
				.findViewById(R.id.button_generate);
		Button buttonOptionsSave = (Button) mLinearLayout
				.findViewById(R.id.button_save);
		Button buttonBrowser = (Button) mLinearLayout
				.findViewById(R.id.button_browser);

		// called when the generateOptions button was clicked
		buttonGenerateOptions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// creates a new optionsExport object
				OptionsExport optionsExport = new OptionsExport();
				// creates a mewGenerateOptions object
				GenerateOptions generateOptions = new GenerateOptions();
				File f = new File(Environment
						.getExternalStorageDirectory().getPath()+"/teamgamma");
				if (!f.exists()) {
					if (!f.mkdirs()) {
						// create a File object for the parent directory
						File teamgamma = new File(Environment
								.getExternalStorageDirectory().getPath());
						// have the object build the directory structure, if
						// needed.
						teamgamma.mkdirs();
						// create a File object for the output file
						File outputFile = new File(teamgamma, "teamgamma");
						// now attach the OutputStream to the file object,
						// instead of a String representation
						try {
							FileOutputStream fos = new FileOutputStream(
									outputFile);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				// writes gerneral options into a new options.txt
				optionsExport.writeSingle(generateOptions.getOptionsFilepath(),
						generateOptions.generate());
				options.readAll();
				// Create new fragment and transaction
				Fragment newFragment = new HomeFragment();
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				// Replace whatever is in the fragment_container view with this
				// fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.content_frame, newFragment);
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();

			}
		});

		// called when a
		buttonOptionsSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				options.setOption(KindOfOption.PATH.ordinal(), PathOptions.OPTIONSPATH, optionsPath.getText().toString());
				// Create new fragment and transaction
				Fragment newFragment = new HomeFragment();
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				// Replace whatever is in the fragment_container view with this
				// fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.content_frame, newFragment);
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();

			}
		});

		// called when the file brwoser button was clicked
		buttonBrowser.setOnClickListener(new OnClickListener() {
			/**
			 * opens a new activity with the file browser
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						getActivity(),
						de.teamgamma.cansat.app.filebrowser.AndroidExplorer.class);
				// starst the activity
				startActivity(intent);
			}
		});

		return mLinearLayout;

	}
}
