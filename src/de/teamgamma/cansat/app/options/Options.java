package de.teamgamma.cansat.app.options;

import android.app.Fragment;

/**
 * 
 * @author Alexander Brennecke
 * collects every object, implementing OptionsInterface
 * includes useful methods to get/set options in an OptionsInterface implementing object
 */
public class Options {
	
	private OptionsInterface[] options = new OptionsInterface[4];
	private OptionsReviewer check = new OptionsReviewer();
	private static Options instance = null;

	// singleton pattern
	public static Options getInstance() {
		if (instance == null) {
			instance = new Options();
			return instance;
		} else {
			return instance;
		}
	}

	/**
	 * generate a new object of every possible options type
	 */
	private Options() {
		options[0] = new ConnectionOptions();
		options[1] = new PathOptions();
		options[2] = new ChartViewOptions();
		options[3] = new MapsOptions();
	}

	/**
	 * 
	 * @return optionsInterface Array within the instances of every option 
	 */
	public OptionsInterface[] getOptions() {
		return options;
	}
	
	/**
	 * 
	 * @param kindOfOption value out of the KindOfOptions enum as .ordinary()
	 * @param nameOfOption static int out of the class you want to change 
	 * 			(same name as in the KindOfOptions class)
	 * @param value value that should be saved into the corresponding object
	 * @return true if it is possible | false if it is not possible
	 */

	public boolean setOption(int kindOfOption, int nameOfOption, Object value) {
		OptionsExport exporter = new OptionsExport();
		if (check.checkBetween(0, options.length - 1, kindOfOption)) {
			if (check.checkBetween(0,
					options[kindOfOption].getValues().length - 1, nameOfOption)) {
				String[] currentValues = options[kindOfOption].getValues();
				currentValues[nameOfOption] = String.valueOf(value);
				options[kindOfOption].setValues(currentValues);
				exporter.writeOptions();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * reads the options.txt and writes the values into their objects
	 */
	
	public void readAll(){
		OptionsExport exporter = new OptionsExport();
		exporter.readOptions();
	}
	/**
	 * 
	 * @param kindOfOption value out of the KindOfOptions enum as .ordinary()
	 * @param nameOfOption static int out of the class you want to change 
	 * 			(same name as in the KindOfOptions class)
	 * @return the value you selected
	 */
	public String getOption(int kindOfOption, int nameOfOption) {
		if (check.checkBetween(0, options.length - 1, kindOfOption)) {
			if (check.checkBetween(0,
					options[kindOfOption].getValues().length - 1, nameOfOption)) {
				return options[kindOfOption].getValues()[nameOfOption];
			}
			return "";
		}
		return "";
	}
}
	
