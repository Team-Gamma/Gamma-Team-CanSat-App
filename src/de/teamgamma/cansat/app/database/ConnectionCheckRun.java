package de.teamgamma.cansat.app.database;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class ConnectionCheckRun implements Runnable{
	
	/**
	 * @author Teamgamma
	 * 
	 *         This class implements the Interface Runnable to connect to the
	 *         Database in an parallel Thread and check the connection.
	 * 
	 */

	@Override
	public void run() {
		Looper.prepare();
		if (new Connection().checkConnection()){
			DatabaseCoordination.getInstance().importFragment.setDatabaseConnectionStatus(true);
		}else{
			DatabaseCoordination.getInstance().importFragment.setDatabaseConnectionStatus(false);

		}
		
	}

}
