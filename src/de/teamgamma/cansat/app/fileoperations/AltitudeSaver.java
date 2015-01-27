package de.teamgamma.cansat.app.fileoperations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.options.PathOptions;

public class AltitudeSaver implements Runnable {
	// private Double[][] data;
	private String message;
	private Options option;
	private String filepath;
	private String writableString;
	private String exportTime;
	private static AltitudeSaver instance = null;

	public static AltitudeSaver getInstance() {
		if (instance == null) {
			instance = new AltitudeSaver();
		}
		return instance;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void save(Double[][] data) {

		this.exportTime = constantValues.exportTime;

		FileOutputStream out;

		try {

			for (int i = 0; i < constantValues.names.length; i++) {
				// the values ​​of individual sensors are each written to a
				// separate Sensor file
				// the name of the file consists of the date, time and name of
				// the sensor
				if (data != null) {
					if (data[i][1] != null) {
						this.filepath = option.getInstance().getOption(
								KindOfOption.PATH.ordinal(),
								PathOptions.VALUESTORAGEPATH)
								+ "/"
								+ this.exportTime
								+ constantValues.names[i]
								+ "-altitude" + ".teamgamma";
						out = new FileOutputStream(new File(this.filepath),
								true);

						this.writableString = String.valueOf(data[i][0]) + ":"
								+ Double.toString(data[i][1]) + "\n";
						out.write(this.writableString.getBytes());
						out.close();

					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Double[][] unpack() {
		try {
			JSONObject jdata = new JSONObject(this.message);
			Double[][] data = new Double[constantValues.names.length + 1][2];
			int lasti = 0;

			for (int i = 0; i < constantValues.names.length; i++) {
				data[i][0] = jdata.getDouble("altitude");

				try {
					if (jdata.getDouble(constantValues.names[i]) != 0
							&& !constantValues.names[i].equals("latitude")
							&& !constantValues.names[i].equals("longitude")
							&& !constantValues.names[i].equals("altitude")) {

						data[i][1] = jdata.getDouble(constantValues.names[i]);
						lasti++;
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				data[lasti + 1][1] = Double.valueOf(jdata.getLong("utc_time"));
				data[lasti + 1][0] = jdata.getDouble("altitude");

			}
			return data;
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void run() {

		this.save(this.unpack());

	}

}
