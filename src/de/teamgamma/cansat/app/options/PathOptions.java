package de.teamgamma.cansat.app.options;

import android.os.Environment;
import android.util.Log;
/**
 * 
 * @author Alexander Brennecke
 * Path options to save short- and long-term pathes
 */

public class PathOptions implements OptionsInterface {
	
	public static final int VALUEEXPOTPATH = 0;
	public static final int TEMPVALUEEXPORTPATH = 1;
	public static final int VALUESTORAGEPATH = 2;
	public static final int TEMPVALUESTORAGEPATH = 3;
	public static final int OPTIONSPATH = 4;
	public static final int TEMPOPTIONSPATH = 5;
	public static final int TEMBROWSERRESULTPATH = 6;

	private String[] values = new String[7];

	/*
	 * 0: valueExportPath 
	 * 1: tempValueExportPath 
	 * 2: valueStoragePath 
	 * 3: tempValueStoragePath 
	 * 4: optionsPath 
	 * 5: tempOptionsPath 
	 * 6: temporaryBrowserResultPath
	 */

	/**
	 * generates the home path 
	 */
	public PathOptions() {
		values[4] = Environment.getExternalStorageDirectory().getPath()
				+ "/teamgamma/options.txt";
	}

	@Override
	public String[] getValues() {
		return values;
	}

	@Override
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
