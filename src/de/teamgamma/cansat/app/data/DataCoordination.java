package de.teamgamma.cansat.app.data;

import android.util.Log;
import de.teamgamma.cansat.app.fileoperations.SaveThread;
import de.teamgamma.cansat.app.fileoperations.AltitudeSaver;
import de.teamgamma.cansat.app.fragments_androidplot.RealtimeGraph;
import de.teamgamma.cansat.app.json.Json;
import de.teamgamma.cansat.app.main.MainActivity;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.sensors.Sensor;

/**
 * @author Teamgamma
 * 
 *         This class controls the entire data traffic in real-time connection.
 *         The data will converted From an String to an Sensor Object. Every
 *         Sensordata will give to the Realtimegraph and save it parallel in an own
 *         Sensorfile for every try.
 * 
 * 
 */

public class DataCoordination {

	private Json json;
	private static DataCoordination instance = null;
	private Sensor[] sensors;
	private Options options;

	// an instance of the class is created, so there is no recursive call
	public static DataCoordination getInstance() {
		if (instance == null) {
			instance = new DataCoordination();
		}
		return instance;
	}

	public DataCoordination() {
		// the constructor of the class adds in each index of the sensor array,
		// a new object of class sensor and give this the corresponding names
		this.sensors = new Sensor[constantValues.names.length];
		this.options = Options.getInstance();
		this.json = Json.getInstance();
		for (int i = 0; i < constantValues.names.length; i++) {
			this.sensors[i] = new Sensor();
			this.sensors[i].setName(constantValues.names[i]);

		}
	}

	public void coordinateData(String message) {
		Log.d("Message", message);
		// takes the message from the AndroidClient
		// message is unpacked
		Double[][] data = this.json.unpack(message);
		for (int i = 0; i < constantValues.names.length; i++) {
			// the data from the unpacked message will be written in every
			// corresponding Sensor
			this.sensors[i].setValues(data[i][0].longValue(), data[i][1]);
		}
		for (Sensor sensor : sensors) {
			

			
			// The SensorArray are the SensorArray passed.
			if (sensor.getName().equals(
					this.options.getOption(KindOfOption.CHARTVIEW.ordinal(),
							ChartViewOptions.ACTIVESENSORNAME))) {
				if (MainActivity.getCurrentFragment().getClass()
						.equals(RealtimeGraph.class)) {
					((RealtimeGraph) MainActivity.getCurrentFragment())
							.onValueChanged(sensor);
				}
			}
		}
		// A new Thread will create and give them the SensorArray to save them
		// parallel.
		SaveThread saveData = new SaveThread();
		saveData.setData(data);
		Thread saveThread = new Thread(saveData);
		saveThread.start();

		AltitudeSaver saver = AltitudeSaver.getInstance();
		saver.setMessage(message);
		Thread altitudeThread = new Thread(saver);
		altitudeThread.start();
	}

}
