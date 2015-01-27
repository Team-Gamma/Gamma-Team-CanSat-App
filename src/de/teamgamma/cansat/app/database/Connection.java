package de.teamgamma.cansat.app.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * @author Teamgamma
 * 
 * this class connects to our database and is returned the data as an "JSONArray"
 * 
 * 
 */

public class Connection {

	public static JSONArray connection() {
		Log.d("gamma", "Json");
		//here the connection to the database will start
		try {

			URL url = new URL("http://gammaweb.noodle-net.de/read.php");

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			try {
				//The data from our database will be cast to an ("JSONArray") and the JsonArray is returned
				JSONArray jarray = new JSONObject(reader.readLine()).getJSONArray("data");

				reader.close();
				return jarray;

			} catch (JSONException e) {
				return null;
			}

		} catch (IOException e) {
			return null;

		}
	}
	
	
		public boolean checkConnection(){
			  try{
			        URL myUrl = new URL("http://gammaweb.noodle-net.de/read.php");
			        URLConnection connection = myUrl.openConnection();
			        connection.setConnectTimeout(10);
			        connection.connect();
			        return true;
			    } catch (Exception e) {
			        // Handle your exceptions
			        return false;
			    }			
	}

}
