package de.teamgamma.cansat.app.options;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.util.Log;

/**
 * 
 * @author Alexander Brennecke
 *read/write in/out the options.txt
 *
 */
public class OptionsExport {
	private Options options = Options.getInstance();
	private String newLine = "\n";
	private String filepath = options.getOption(KindOfOption.PATH.ordinal(),
			PathOptions.OPTIONSPATH);
	private String writeableString = "";

	/**
	 * generates an writable String and writes it into the options.txt
	 */
	public void writeOptions() {
		OptionsInterface[] optionsArray = options.getOptions();
		for (OptionsInterface element : optionsArray) {
			for (int b = 0; b < element.getValues().length; b++) {
				if(element.getValues()[b] == null){
					writeableString = writeableString + "null"
							+ newLine;
				}
				else{
				writeableString = writeableString + element.getValues()[b]
						+ newLine;
				}
			}
		}
		writeSingle(options.getOption(KindOfOption.PATH.ordinal(),
				PathOptions.OPTIONSPATH), writeableString);
		writeableString = "";
	}

	/**
	 * reads the options.txt and writes the values into the correct OptionsInterface object
	 */
	public void readOptions() {
		int counter = 0;
		OptionsInterface[] optionsArray = options.getOptions();
		for (OptionsInterface element : optionsArray) {
			if (element.getValues().length == 0) {
				
			} else {
				String[] values = new String[element.getValues().length];
				for (int b = 0; b < element.getValues().length; b++) {
					values[b] = getValue(counter);
					counter++;
				}
				element.setValues(values);
			
			}
		}
	}
	
	/**
	 * writes a single message into a file
	 * @param filepath filepath for the file in which it writes
	 * @param message text which should be write into the filepath
	 */
	public void writeSingle(String filepath, String message) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(filepath));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			out.write(message.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param positionLine line position into the file
	 * @return choosen line as string
	 */
	private String getValue(int positionLine) {
		String zeile = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			for (int i = 0; i < positionLine + 1; i++) {
				zeile = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zeile;
	}

}
