package de.teamgamma.cansat.app.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import de.teamgamma.cansat.app.R;

/**
 * 
 * @author Alexander Brennecke
 * 
 * fragment that displays buttons where the user can choose options e want to change
 *
 */
public class OptionsFragment extends Fragment {
    public static final String ARG_SLIDEMENU_VALUES = "slidemenu_values";

    public OptionsFragment() {
        // Empty constructor
    }
    
    /**
     * 
     * called when the fragment will shown the first time
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//initialize a few importand variables
     	final LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fargment_options,
                 container, false);       
     	
    	Button buttonConnection = (Button) mLinearLayout.findViewById(R.id.connection);
    	Button buttonExport = (Button) mLinearLayout.findViewById(R.id.export);
    	Button buttonChartView = (Button) mLinearLayout.findViewById(R.id.chartView);
    	
    	
    	
    	//called when the connection button was clicked
    	buttonConnection.setOnClickListener(new OnClickListener() {
    	        @Override
    	        public void onClick(View v) {
    	        	selectedOption(R.id.connection);        	        	
    	        }
    	});
    	
    	//called when the export button was clicked
    	buttonExport.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	selectedOption(R.id.export);        	        	
	        }
	});
    	
    	buttonChartView.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	selectedOption(R.id.chartView);        	        	
	        }
	});
    	

        return mLinearLayout;
    }
    
    /**
     * @param button id of the pressed button
     */
	private void selectedOption(int button) {
    	Fragment fragment = null;
        // update the main content by replacing fragments
    	switch (button){
    	case R.id.connection:  fragment = new OptionsConnectionFragment(); break;
    	case R.id.export: fragment = new OptionsExportFragment(); break;
    	case R.id.chartView: fragment = new OptionsChartviewFragment(); break;
    	}    	
        Bundle args = new Bundle();
        args.putInt(OptionsFragment.ARG_SLIDEMENU_VALUES, button);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }
    
    
}