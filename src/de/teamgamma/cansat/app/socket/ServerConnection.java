package de.teamgamma.cansat.app.socket;

import android.util.Log;
import de.teamgamma.cansat.app.data.DataCoordination;
import de.teamgamma.cansat.app.options.ConnectionOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;

/**
 * @author Teamgamma
 * 
 *         This class implements Runnable to get the data parallel in a new
 *         Thread and give the data to the DataCoordination class.
 * 
 */

public class ServerConnection {

	public ServerConnection() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Options options = Options.getInstance();
				AndroidClient client = new AndroidClient(options.getOption(
						KindOfOption.CONNECTION.ordinal(),
						ConnectionOptions.JAVASOCKETIP),

				Integer.parseInt(options.getOption(
						KindOfOption.CONNECTION.ordinal(),
						ConnectionOptions.JAVASOCKETPORT)),
						new MessageAdapter() {

							@Override
							public void messageArrived(String message) {
								Log.d("gamma", "received");
								DataCoordination.getInstance().coordinateData(
										message);
							}
						});

			}
		}).start();

	}
}
