package de.teamgamma.cansat.app.json;

import org.json.JSONException;
import org.json.JSONObject;

import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.MapsOptions;
import de.teamgamma.cansat.app.options.Options;

/**
 * @author Teamgamma
 * 
 *         This class convert the String message from the Socketsystem to an
 *         JSONObject. With the JSONObject, the data will convert to an "Sensor"
 *         Object for every Sensor.
 * 
 */

public class Json {

	private Double[][] sensors;
	private static Json instance = null;

	public static Json getInstance() {
		if (instance == null) {
			instance = new Json();
		}
		return instance;
	}

	private Json() {
		sensors = new Double[constantValues.names.length][2];
	}

	public Double[][] unpack(String json) {
		// the data are read out with the help of a "JsonObject" and added to
		// the respective "Sensor" class
		JSONObject data;
		try {
			data = new JSONObject(json);
			Long time;
			time = data.getLong("utc_time");
			for (int i = 0; i < constantValues.names.length; i++) {
				this.sensors[i][0] = Double.valueOf(time
						- constantValues.firstTimestamp);
				
				try {
				
					if (constantValues.names[i].equals("longitude")) {

						Options.getInstance().setOption(
								KindOfOption.MAPS.ordinal(),
								MapsOptions.LONGITUDE, this.sensors[i][1]);

					} else if (constantValues.names[i].equals("latitude")) {
						Options.getInstance().setOption(
								KindOfOption.MAPS.ordinal(),

								MapsOptions.LATITUDE, this.sensors[i][1]);

					} else if (data.getDouble(constantValues.names[i]) != 0) {
						this.sensors[i][1] = data
								.getDouble(constantValues.names[i]);
						if (!constantValues.names[i].equals("altitude")) {
						
						}
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}


			
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return this.sensors;
	}

}



//
//package de.teamgamma.cansat.app.json;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import de.teamgamma.cansat.app.data.constantValues;
//import de.teamgamma.cansat.app.options.KindOfOption;
//import de.teamgamma.cansat.app.options.MapsOptions;
//import de.teamgamma.cansat.app.options.Options;
//
///**
// * @author Teamgamma
// * 
// *         This class convert the String message from the Socketsystem to an
// *         JSONObject. With the JSONObject, the data will convert to an "Sensor"
// *         Object for every Sensor.
// * 
// */
//
//public class Json {
//
//	private Double[][] sensors;
//	private static Json instance = null;
//	public Double[][] altitudeandvalue;
//
//	public static Json getInstance() {
//		if (instance == null) {
//			instance = new Json();
//		}
//		return instance;
//	}
//
//	private Json() {
//		sensors = new Double[constantValues.names.length][2];
//	}
//
//
//	public Double[][] unpack(String json) {
//		// the data are read out with the help of a "JsonObject" and added to
//		// the respective "Sensor" class
//		JSONObject data;
//		try {
//			data = new JSONObject(json);
//			Long time;
//			time = data.getLong("utc_time");
//			Double altitude = 0D;
//			try {
//				altitude = data.getDouble("altitude");
//			} catch (JSONException e) {
//			}
//			for (int i = 0; i < constantValues.names.length; i++) {
//				this.sensors[i][0] = Double.valueOf(time
//						- constantValues.firstTimestamp);
//
//				this.altitudeandvalue[i][0] = altitude;
//
//				try {
//					if (constantValues.names[i].equals("longitude")) {
//
//						Options.getInstance().setOption(
//								KindOfOption.MAPS.ordinal(),
//								MapsOptions.LONGITUDE, this.sensors[i][1]);
//
//					} else if (constantValues.names[i].equals("latitude")) {
//						Options.getInstance().setOption(
//								KindOfOption.MAPS.ordinal(),
//
//								MapsOptions.LATITUDE, this.sensors[i][1]);
//
//					} else if (data.getDouble(constantValues.names[i]) != 0) {
//						this.sensors[i][1] = data
//								.getDouble(constantValues.names[i]);
//						if (!constantValues.names[i].equals("altitude")) {
//							this.altitudeandvalue[i][1] = data
//									.getDouble(constantValues.names[i]);
//						}
//
//					}
//				} catch (JSONException e) {
//				}
//
//			}
//			this.sensors[constantValues.names.length + 1][0] = altitude;
//			this.sensors[constantValues.names.length + 1][1] = Double
//					.valueOf(data.getLong("utc_time"));
//		} catch (JSONException e) {
//
//			e.printStackTrace();
//		}
//
//		return this.sensors;
//	}
//
//}
