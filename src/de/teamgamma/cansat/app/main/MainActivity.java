package de.teamgamma.cansat.app.main;

import java.io.File;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.fragments.HomeFragment;
import de.teamgamma.cansat.app.fragments.ImportFragment;
import de.teamgamma.cansat.app.fragments.OptionsFragment;
import de.teamgamma.cansat.app.fragments.OptionsSearcherFragment;
import de.teamgamma.cansat.app.fragments_androidplot.RealtimeGraph;
import de.teamgamma.cansat.app.options.ChartViewOptions;
import de.teamgamma.cansat.app.options.ConnectionOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.MapsOptions;
import de.teamgamma.cansat.app.options.Options;
import de.teamgamma.cansat.app.options.OptionsExport;
import de.teamgamma.cansat.app.sensors.Sensor;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private Options options = Options.getInstance();

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mSlidemanueTitels;
	private Sensor sensor = new Sensor();
	private static Fragment fragment;
	private int lastCase = 0;
	private boolean lastScreen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		for (double d = 0; d < 10; d++) {
			sensor.setValues(0, d);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = mDrawerTitle = getTitle();
		constantValues.generateSliderArray();
		mSlidemanueTitels = constantValues.getSliderArray();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mSlidemanueTitels));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (new File(Environment.getExternalStorageDirectory().getPath()
				+ "/teamgamma/options.txt").exists()) {
			options.readAll();
			if (savedInstanceState == null) {
				selectItem(0, false);
			}

		} else {
			// setContentView(R.layout.activity_main);
			selectItem(0, true);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_websearch:
			// create intent to perform web search for this planet
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
			// catch event that there's no activity to handle intent
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.app_not_available,
						Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 *  
	 * @author Alexander Brennecke
	 *	The click listner for ListView in the navigation drawer
	 */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position, false);
		}
	}
	
	/**
	 * 
	 * @param position position of the selected item in the item Array List
	 * @param check operation were generated
	 * updates the displaying fragment depend on the selected item in the navigation drawer
	 */

	private void selectItem(int position, boolean check) {
		
		// update the main content by replacing fragment
		switch (position) {
		case 0:
			if (check) {
				fragment = new OptionsSearcherFragment();
				break;
			} else {
				lastCase = 0;
				fragment = new HomeFragment();
				break;
			}
		
		default:
			Log.d("gamma", String.valueOf(position));
			if(constantValues.getActiveSensor()==1){
				options.setOption(KindOfOption.CHARTVIEW.ordinal(),ChartViewOptions.ACTIVESENSORNAME,constantValues.names[position-1]);
				fragment = new RealtimeGraph();
				lastCase = position;
				break;				
			}
			else if(!lastScreen){
				lastScreen = true;
				creatToast("No Connection! Go to: Options -> Connection");
				selectItem(lastCase,false);
				break;
			}
			else{
				lastScreen = false;
				selectItem(0,false);
				break;
			}
					

		case constantValues.sensorNamesSize+1:
			options.setOption(KindOfOption.MAPS.ordinal(), MapsOptions.LATITUDE, 69.2948485);
			options.setOption(KindOfOption.MAPS.ordinal(), MapsOptions.LONGITUDE, 16.029606);
			String longitude = options.getOption(KindOfOption.MAPS.ordinal(), MapsOptions.LONGITUDE);
			String latitude = options.getOption(KindOfOption.MAPS.ordinal(), MapsOptions.LATITUDE);

			if(!(longitude== null)&&!(latitude==null)) {
				selectItem(lastCase,false);
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + latitude + "," + longitude)));
				break;
			}
			else{
				creatToast("No Position found!");
				selectItem(lastCase,false);
				break;
			}
			
			
		case constantValues.sensorNamesSize+2:
			fragment = new ImportFragment();
			lastCase = 6;
			break;
		
		case constantValues.sensorNamesSize+3:
			fragment = new OptionsFragment();
			lastCase = 7;
			break;

		}
		Bundle args = new Bundle();
		args.putInt(OptionsFragment.ARG_SLIDEMENU_VALUES, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mSlidemanueTitels[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	/**
	 * 
	 * @return the current fragment
	 */
	public static Fragment getCurrentFragment(){
		return fragment;
	}
	
	/**
	 * creates a toast
	 * @param message text which should displayed 
	 */
	private void creatToast(String message){
		Context context = this.getBaseContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
