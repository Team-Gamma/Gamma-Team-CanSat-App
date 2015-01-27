package de.teamgamma.cansat.app.database;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Teamgamma
 * 
 *         This class implements the Interface Runnable to connect to the
 *         Database in an parallel Thread. Here also saved the Names of the
 *         Sensor in the "Sensornames" class and gives the name to the Graph
 * 
 */
public class ConnectionRun implements Runnable {

	@Override
	public void run() {

		// The connection start and save the response as an JSONArray.
		JSONArray jarray = Connection.connection();
		DatabaseCoordination.getInstance().setJsonArray(jarray);

		try {
			String[] names = new String[jarray.getJSONObject(0).names()
					.length()];

			for (int i = 0; i < names.length; i++) {
				names[i] = (String) jarray.getJSONObject(0).names().get(i);
			}

			// The names of Sensors will saved in the Sensornames class.
			Sensornames.getInstance().setNamesArray(names);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
