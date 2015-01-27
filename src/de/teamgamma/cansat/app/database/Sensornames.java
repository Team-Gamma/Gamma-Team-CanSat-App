package de.teamgamma.cansat.app.database;

/**
 * @author Teamgamma
 * 
 *         This class is only an getter and setter class to save the names of
 *         Sensors from the Database.
 * 
 */

public class Sensornames {
	private String[] namesArray = null;
	private static Sensornames instance = null;

	public static Sensornames getInstance() {
		if (instance == null) {
			instance = new Sensornames();
		}
		return instance;
	}

	public String[] getSensornames() {
		return namesArray;
	}

	public void setNamesArray(String[] namesArray) {
		this.namesArray = namesArray;
	}
}