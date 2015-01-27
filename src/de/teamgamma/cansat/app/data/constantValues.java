package de.teamgamma.cansat.app.data;

import android.graphics.Color;
/**
 * @author Teamgamma
 * 
 * This class consists of static types of data.
 * The Application adapt to this Class and change it self easily, if there are changes
 * 
 * 
 */

public class constantValues {

	
	public static String[] names = { "temperature", "pressure", "humidity", "altitude", "longitude", "latitude", "acceleration_z", "acceleration_y","acceleration_x" };
	public static final int sensorNamesSize = 9;
	public static String[] sliderNames = {"Last Position", "Import", "Options"};
	
	public static String[] notAllowedKeys = { "id", "ID", "time" , "longitude", "latitude", "utc_time"};
	
	public static String[] sliderArray;
	
	public static long firstTimestamp;
	
	public static String exportTime;

	public static int activeSensor = 0;
	
	
	public static int getActiveSensor() {
		return activeSensor;
	}

	public static void setActiveSensor(int active) {
		activeSensor = active;
	}

	public static final int[] selectableColors = { Color.TRANSPARENT,
			Color.BLUE, Color.BLACK, Color.CYAN, Color.GREEN, Color.RED,
			Color.MAGENTA, Color.YELLOW, Color.WHITE };
	
	public static final String head = "Team-Gamma_Android-App";
	
	public static void generateSliderArray(){
		sliderArray = new String[names.length + sliderNames.length + 1];
		sliderArray[0] = "Home";
		int counter = 1;
		for(int i = 0; i<names.length; i++){
			sliderArray[counter] = names[i];
			counter++;
		}
		for(int i = 0; i<sliderNames.length; i++){
			sliderArray[counter] = sliderNames[i];
			counter++;
		}
	}

	public static String[] getSliderArray() {
		// TODO Auto-generated method stub
		return sliderArray;
	}

}
