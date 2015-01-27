package de.teamgamma.cansat.app.values;

/**
 * @author Teamgamma
 * 
 *         This class is only an Object to save two Values for each Sensor. This
 *         Value Object consists of an Array of Double. The indexes consists of an
 *         Value from an Sensor and the respective timestamp.
 * 
 */

public class Values {
	private Double[] values = new Double[2];

	public Double[] getValues() {
		return values;
	}

	public void setValues(Double time, Double value) {
		this.values[0] = time;
		this.values[1] = value;
	}

}
