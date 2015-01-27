package de.teamgamma.cansat.app.options;

/**
 * 
 * @author Alexander Brennecke
 * checks if a value is between two other values
 */
public class OptionsReviewer {

	
	public boolean checkBetween(int Begin, int End, int value) {
		if (value >= Begin && value <= End) {
			return true;
		} else {
			return false;
		}
	}

}
