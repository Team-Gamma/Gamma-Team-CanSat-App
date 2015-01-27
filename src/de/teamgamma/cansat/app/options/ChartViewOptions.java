package de.teamgamma.cansat.app.options;

import android.util.Log;


/**
 * 
 * @author Alexander Brennecke
 *	saves every options which are imported for the correct displaying of the charts
 *
 */
public class ChartViewOptions implements OptionsInterface {
	
	public static final int NUMBEROFSHOWNVALUE = 0;
	public static final int POINTCOLOR = 1;
	public static final int LINECOLOR = 2;
	public static final int AREACOLOR = 3;
	public static final int ACTIVESENSORNAME = 4;
	public static final int LASTACTIVESCREEN = 5;
	private String[] values = new String[6];
	
	/*
	 * 0: number of shown values
	 * 1: point color
	 * 2: line color
	 * 3: area color
	 * 4: active sensor
	 * 5: last active screen
	 */
	
	public String[] getValues() {
		return values;
	}
	
	public void setValues(String[] values) {
		this.values = values;
	}
	
	/**
	 * @return colors returns an Array within the different colors fir Line, Area and Point
	 */
	public int[] getColors(){
		int[] colors = new int[3];
		if(!(values[POINTCOLOR].equals(null) && !(values[LINECOLOR].equals(null)) && !(values[AREACOLOR].equals(null)))){
			colors[0] = Integer.valueOf(values[POINTCOLOR]);
			colors[1] = Integer.valueOf(values[LINECOLOR]);
			colors[2] = Integer.valueOf(values[AREACOLOR]);
		}
		return colors;
	}
	
	@Override
	public void setColors(int[] colors) {
		for(int i = 0; i < 3; i++){
			values[POINTCOLOR+i] = String.valueOf(colors[i]);
		}
		
	}


	
	
}