package de.teamgamma.cansat.app.options;


/**
 * 
 * @author Alexander Brennecke
 *	saves every option required for a java socket connection	
 *
 */
public class ConnectionOptions implements OptionsInterface {
	
	public static final int JAVASOCKETPORT = 0;
	public static final int JAVASOCKETIP = 1;
	public String[] values = new String[2];
	
	/*
	 * 0: Java Socket port
	 * 1: Java Socket Ip Adress
	 * 2: Last active Sensor
	 */

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	@Override
	public int[] getColors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColors(int[] colors) {
		// TODO Auto-generated method stub
		
	}
	
	

}
