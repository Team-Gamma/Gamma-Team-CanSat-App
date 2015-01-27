package de.teamgamma.cansat.app.fragments;

import java.util.ArrayList;

import android.R.color;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.database.DatabaseCoordination;
import de.teamgamma.cansat.app.database.Sensornames;
import de.teamgamma.cansat.app.fileoperations.Read;
import de.teamgamma.cansat.app.fragments_androidplot.ImportSimpleXYChart;
import de.teamgamma.cansat.app.fragments_androidplot.RealtimeGraph;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.ConnectionOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.options.PathOptions;
import de.teamgamma.cansat.app.socket.ServerConnection;
import de.teamgamma.cansat.app.values.Values;

/**
 * 
 * @author Alexander Brennecke creates a fragment which displays every sensor,
 *         cathed from the database as a button
 * 
 * 
 */
public class DatabaseSensorsFragment extends Fragment {

	// initialize a few importend variables
	LinearLayout mLinearLayout;
	private Options options = Options.getInstance();
	private ArrayList<Button> buttons = new ArrayList<Button>();

	/**
	 * will be called when a new DatabaseSensorFragment object is generated
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// initialize a few importend variables
		mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_database_sensor, container, false);

		DatabaseCoordination.getInstance().connectToDatabase();
		while (Sensornames.getInstance().getSensornames() == null) {
		}

		int counter = 0;
		for (int i = 0; i < Sensornames.getInstance().getSensornames().length; i++) {
			boolean notAllowed = false;
			for (int a = 0; a < constantValues.notAllowedKeys.length; a++) {
				if (Sensornames.getInstance().getSensornames()[i]
						.equals(constantValues.notAllowedKeys[a])) {
					notAllowed = true;
				}
			}
				if (!notAllowed) {
					AddAll(Sensornames.getInstance().getSensornames()[i],
							counter);
					counter++;
				}
			
		}

		return mLinearLayout;
	}

	/**
	 * generates a button with the transfered text and id and add it to the
	 * fragment
	 * 
	 * @param text
	 *            text which should displayed on the generated button
	 * @param id
	 *            ID of the button which should be generated
	 */
	private void AddAll(String text, int id) {
		Button btn = new Button(mLinearLayout.getContext());
		btn.setBackgroundColor(Color.rgb(51, 181, 229));
		btn.setText(text);
		btn.setId(id);
		buttons.add(btn);
		btn.setY(buttons.size() * (btn.getHeight() + 10));
		btn.setOnClickListener(new OnClickListener() {

			/**
			 * on click listener creates a new ImportSimpleXYChart Fragment and
			 * displays it with the correct values from the database
			 */
			@Override
			public void onClick(View v) {
				for (int i = 0; i < buttons.size(); i++) {
					if (buttons.get(i).getId() == v.getId()) {

						options.setOption(KindOfOption.CHARTVIEW.ordinal(),
								ChartViewOptions.ACTIVESENSORNAME,
								(String) buttons.get(v.getId()).getText());
						break;
					}
				}

				// generates the new fragment and commits it to the display

				Fragment fragment = new ImportSimpleXYChart();

				Bundle args = new Bundle();
				args.putInt(OptionsFragment.ARG_SLIDEMENU_VALUES, v.getId());
				fragment.setArguments(args);

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				fragmentManager.executePendingTransactions();

				// Database.getInstance().getData(options.getOption(KindOfOption.CHARTVIEW.ordinal(),
				// ChartViewOptions.ACTIVESENSORNAME), fragment);
				DatabaseCoordination.getInstance().getData(
						options.getOption(KindOfOption.CHARTVIEW.ordinal(),
								ChartViewOptions.ACTIVESENSORNAME), fragment);

			}
		});
		mLinearLayout.addView(btn);
	}

}
