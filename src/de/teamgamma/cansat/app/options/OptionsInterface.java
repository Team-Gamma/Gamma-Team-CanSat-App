package de.teamgamma.cansat.app.options;

/**
 * 
 * @author Alexander Brennecke
 * Interface for every option type
 */
public interface OptionsInterface {
	
	public String[] getValues();
	public void setValues(String[] values);
	public int[] getColors();
	void setColors(int[] colors);

}
