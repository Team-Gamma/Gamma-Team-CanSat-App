package de.teamgamma.cansat.app.database;

import org.json.JSONArray;

import android.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import de.teamgamma.cansat.app.fragments.ImportFragment;
import de.teamgamma.cansat.app.fragments_androidplot.ImportSimpleXYChart;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;

/**
 * @author Teamgamma
 * 
 *         This class coordinate the connection to the Database and the
 *         response. An Thread will started to connect the Database and get the
 *         response.All converted data will give to the Graph.
 * 
 */

public class DatabaseCoordination {

	private static DatabaseCoordination instance = null;
	private ConnectionRun connectionrun = new ConnectionRun();
	private JSONArray jarray;
	public ImportFragment importFragment;
	public View importFragmentView;

	public static DatabaseCoordination getInstance() {
		if (instance == null) {
			instance = new DatabaseCoordination();
		}
		return instance;
	}

	public void setJsonArray(JSONArray jarray) {
		this.jarray = jarray;
	}

	public void connectToDatabase() {
		// The connection to the Database will start in an parallel Thread.
		Thread databaseThread = new Thread(this.connectionrun);
		databaseThread.start();
	}
	
	public void checkConnection(ImportFragment importFragment){
		this.importFragment = importFragment;
		this.importFragmentView = importFragment.getView();
		Thread connectionCheck = new Thread(new ConnectionCheckRun());
		connectionCheck.start();
		
	}

	public void getData(String sensor, Fragment fragment) {
		// The data will give to the Graph.
		if (sensor.equals(Options.getInstance().getOption(
				KindOfOption.CHARTVIEW.ordinal(),
				ChartViewOptions.ACTIVESENSORNAME))) {
			((ImportSimpleXYChart) fragment).setValue(Sensordata.getInstance()
					.getData(sensor, this.jarray));
		}
	}
}