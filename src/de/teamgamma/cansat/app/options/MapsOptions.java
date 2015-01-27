package de.teamgamma.cansat.app.options;

/**
 * 
 * @author Alexander Brennecke
 *
 * saves every imported options for the google maps link
 */
public class MapsOptions implements OptionsInterface {

	private String[] values = new String[2];
	public static final int LONGITUDE = 0;
	public static final int LATITUDE = 1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getValues() {
		// TODO Auto-generated method stub
		return values;
	}

	@Override
	public void setValues(String[] values) {
		// TODO Auto-generated method stub
		
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
