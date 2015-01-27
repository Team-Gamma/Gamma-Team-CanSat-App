package de.teamgamma.cansat.app.filebrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.options.PathOptions;

/**
 * 
 * @author Alexander Brennecke FileExplorer which shows every file located on
 *         the phone
 * 
 */
public class AndroidExplorer extends ListActivity {
	// initialize a few importend variables
	private Options options = Options.getInstance();
	private List<String> item = null;
	private List<String> path = null;
	private String root = "/";
	private String exportPath;
	private TextView myPath;
	private String dirPath;

	/**
	 * Called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		myPath = (TextView) findViewById(R.id.path);
		try{
			if(Integer.valueOf(options.getOption(KindOfOption.CHARTVIEW.ordinal(),ChartViewOptions.LASTACTIVESCREEN))==3){
				getDir(options.getOption(KindOfOption.PATH.ordinal(), PathOptions.VALUEEXPOTPATH));
			}
			else{
				getDir(root);
			}
		}
		catch(NumberFormatException e){
			getDir(root);
		}
	
	}

	/**
	 * 
	 * @param dirPath
	 *            path of a dir, that was opened creates Lists which contains
	 *            every file or dir inside the dir path
	 */

	private void getDir(String dirPath) {
		// variables get there current values
		this.dirPath = dirPath;
		myPath.setText("Location: " + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();

		// when the dirPath is not the root path
		if (!dirPath.equals(root)) {
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());
		}

		// goes throw every object insinde the dir and put its into teh item
		// List
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			path.add(file.getPath());
			if (file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				R.layout.row, item);
		setListAdapter(fileList);
	}

	/**
	 * called when clicked on an dir or an file
	 */

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// initialize a few importend variables
		File file = new File(path.get(position));
		final String fileName = file.getName();

		// if the file is a dir the getDir method will be called to update the
		// screen
		if (file.isDirectory()) {
			if (file.canRead())
				getDir(path.get(position));
			else {
				new AlertDialog.Builder(this)
						.setTitle("[" + fileName + "] folder can't be read!")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		}

		// if the file is not a dir
		// a message box will appear which shows the filename
		// and gives the user the option to select thif ile by pressing the "OK"
		// button

		else {
			new AlertDialog.Builder(this)
					.setTitle("[" + file.getName() + "]")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									boolean valueSetted = true;
									String myPath = dirPath + "/" + fileName;
									switch (Integer.valueOf(options.getOption(
											KindOfOption.CHARTVIEW.ordinal(),
											ChartViewOptions.LASTACTIVESCREEN))) {
									case 0:
										options.setOption(
												KindOfOption.PATH.ordinal(),
												PathOptions.TEMPOPTIONSPATH,
												myPath);
										break;
									case 1:
										options.setOption(
												KindOfOption.PATH.ordinal(),
												PathOptions.TEMPVALUEEXPORTPATH,
												myPath);
										break;
									case 2:
										options.setOption(
												KindOfOption.PATH.ordinal(),
												PathOptions.TEMPVALUESTORAGEPATH,
												myPath);
										break;
									case 3:
										break;
									default:
										break;
									}

									if (valueSetted) {
										options.setOption(
												KindOfOption.PATH.ordinal(),
												PathOptions.TEMBROWSERRESULTPATH,
												myPath);
									} else {
										valueSetted = true;
									}

									Intent intent = new Intent();
									setResult(RESULT_OK, intent);
									finish();
								}
							}).show();

		}
	}
}