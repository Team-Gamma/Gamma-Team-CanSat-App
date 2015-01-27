package de.teamgamma.cansat.app.database;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.teamgamma.cansat.app.values.ValueList;
import de.teamgamma.cansat.app.values.Values;

/**
 * @author Teamgamma
 * 
 *         This class takes the response of the Database as an JSONArray,
 *         converted it to an ArrayList of an specific needed Sensor and return it.
 * 
 * 
 */

public class Sensordata {
	private ValueList data;
	private static Sensordata instance = null;

	public static Sensordata getInstance() {
		if (instance == null) {
			instance = new Sensordata();
		}
		return instance;
	}

	public ArrayList<Values> getData(String sensor, JSONArray jarray) {
		this.data = new ValueList();

		if (jarray == null) {
			return null;
		}
		JSONObject jvalue;
		Long firstTimestamp = Long.valueOf(0);
		try {
			jvalue = jarray.getJSONObject(0);
			firstTimestamp = jvalue.getLong("utc_time");

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		//The data of an specific Sensor will add to an ArrayList.
		for (int i = 0; i < jarray.length(); i++) {

			try {
				jvalue = jarray.getJSONObject(i);
				this.data.appendData(Double.valueOf(jvalue.getLong("utc_time")
						- firstTimestamp), jvalue.getDouble(sensor));
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return this.data.getData();

	}
}