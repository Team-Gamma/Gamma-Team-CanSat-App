package de.teamgamma.cansat.app.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.database.DatabaseCoordination;
import de.teamgamma.cansat.app.fileoperations.Read;
import de.teamgamma.cansat.app.fragments_androidplot.ImportSimpleXYChart;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.options.PathOptions;
import de.teamgamma.cansat.app.values.Values;

/**
 * 
 * 
 * @author Alexander Brennecke
 * 
 *         shows a fragment to select an file which should be displayed in a
 *         chart
 */

public class ImportFragment extends Fragment {

	// initialize a few importend variables
	String importFilepath;
	LinearLayout mLinearLayout;
	private Options options = Options.getInstance();

	/**
	 * when the fileBrowser finished and the fragment will shown again the
	 * onResume will be called and update the TextView fields inside the
	 * fragment
	 */
	@Override
	public void onResume() {
		super.onResume();
		final EditText filePath = (EditText) mLinearLayout
				.findViewById(R.id.filePath);
		importFilepath = options.getOption(KindOfOption.PATH.ordinal(),PathOptions.TEMBROWSERRESULTPATH);
		filePath.setText(importFilepath);
		

	}

	/**
	 * will be called when a new ImportedFragment object is generated
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// initialize a few importend variables
		mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_import, container, false);
		Button buttonImport = (Button) mLinearLayout
				.findViewById(R.id.button_import);
		Button buttonBrowser = (Button) mLinearLayout
				.findViewById(R.id.button_browser);
		Button connectDatabase = (Button) mLinearLayout.findViewById(R.id.Database);

		// ActionListener to open the fileBrowser
		buttonBrowser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				options.setOption(KindOfOption.CHARTVIEW.ordinal(),ChartViewOptions.LASTACTIVESCREEN,3);
				Intent intent = new Intent(
						getActivity(),
						de.teamgamma.cansat.app.filebrowser.AndroidExplorer.class);
	
				startActivity(intent);

			}
		});
		
		// ActionListener to open the fileBrowser
				connectDatabase.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						DatabaseCoordination.getInstance().checkConnection(getInstance());
						
					}
				});
		// Button to import the file into a chart and display this chart
		buttonImport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Read reader = Read.getInstance();
				ArrayList<Values> allValues = reader.getValuefromFile(importFilepath);
				if (allValues == null) {
					createToast("Wrong file format");
				}				
				else{
					// Create new fragment and transaction
		
					Fragment fragment = new ImportSimpleXYChart();
					
					Bundle args = new Bundle();
			        args.putInt(OptionsFragment.ARG_SLIDEMENU_VALUES, v.getId());
			        fragment.setArguments(args);
			        	
			        FragmentManager fragmentManager = getFragmentManager();
			        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			        fragmentManager.executePendingTransactions();
			        ((ImportSimpleXYChart) fragment).setValue(allValues);
			        
			        
				}
			}
		});
		
		return mLinearLayout;
	}
	
	private ImportFragment getInstance(){

		return this;
	}
	
	public View getView(){
		return mLinearLayout;
	}
	
	private void createToast(String message){
		Context context = mLinearLayout.getContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	public void setDatabaseConnectionStatus(boolean connected){
		Log.d("gamma",String.valueOf(connected));
		if(connected){
			// Create new fragment and transaction
			Fragment newFragment = new DatabaseSensorsFragment();
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			transaction.replace(R.id.content_frame, newFragment);
			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();			
		}
		else{
			//createToast("No connection!");
		}
	}

}
