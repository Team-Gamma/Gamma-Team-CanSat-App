package de.teamgamma.cansat.app.options;

import de.teamgamma.cansat.app.R;

import android.os.Environment;

/**
 * 
 * @author Alexander Brennecke
 * generate a options.txt if there doens't exists one
 *
 */
public class GenerateOptions {
	//generale filepath for this app
	private static String filepath = Environment.getExternalStorageDirectory()
			.getPath() + "/teamgamma";
	// to type om 
	private static String newLine = "\n";

	/**
	 * called when a new options.txt should be generated
	 * @return a writable string with importend informations for data saving
	 */
	public String generate() {
		String writeableString = "";
		//add connection options to the string
		writeableString = writeableString+""+newLine+"";
		// add path options to the string
		writeableString = writeableString + newLine + filepath + newLine + "" + newLine + filepath + newLine+ "" + newLine + getOptionsFilepath()+newLine+ "" + newLine +"";
		// add chart view options to string
		writeableString = writeableString +newLine+"20"+newLine+"2"+newLine+"2"+newLine+"0"+newLine+"0"+newLine+"0";
		// add lastPositionOptions
		writeableString = writeableString +newLine+""+newLine+""+newLine+"";

		return writeableString;
	}
	
	/**
	 * 
	 * @return the general options.txt filepath
	 */
	public  String getOptionsFilepath(){
		return this.filepath+"/options.txt";
	}
}
