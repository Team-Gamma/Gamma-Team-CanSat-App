package de.teamgamma.cansat.app.values;

import java.util.ArrayList;

/**
 * @author Teamgama
 * 
 *         This class built easily an ArrayList. Each index of the ArrayList
 *         consists of an Value Object.
 * 
 */

public class ValueList {

	private ArrayList<Values> data;

	public ValueList() {
		this.data = new ArrayList<Values>();
	}

	public void appendData(Double time, Double value) {
		if (time != null && value != null) {
			this.data.add(new Values());
			this.data.get(this.data.size() - 1).setValues(time, value);
		}
	}

	public ArrayList<Values> getData() {
		return this.data;
	}

	public void clearValues() {
		this.data.clear();
	}

}
