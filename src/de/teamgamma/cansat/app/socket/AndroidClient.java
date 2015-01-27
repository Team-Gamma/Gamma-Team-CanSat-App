package de.teamgamma.cansat.app.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.util.Log;
import de.teamgamma.cansat.app.data.constantValues;
import de.teamgamma.cansat.app.options.ConnectionOptions;
import de.teamgamma.cansat.app.options.KindOfOption;
import de.teamgamma.cansat.app.options.Options;

/**
 * @author Teamgamma
 * 
 *         This class is our Socketclient, to connect the Socketserver. The
 *         connection starts in an parallel Thread and wait for the data, while
 *         the connection exists.
 * 
 */

public class AndroidClient {
	private Socket clientSocket = null;

	class CommunicationThread implements Runnable {

		private BufferedReader in;
		private MessageAdapter messageAdapter;

		public CommunicationThread(MessageAdapter messageAdapter) {
			this.messageAdapter = messageAdapter;

			try {
				this.in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));

			} catch (IOException e) {
				Log.d("socket_test", "failed to buffer creation");
				e.printStackTrace();
			}
		}

		public void run() {
			String message = null;
			int counter = 0;
			// while the connection exists, the class wait for data.
			while (clientSocket.isConnected()) {
				try {
					if (this.in.ready()) {
						message = this.in.readLine();
						Log.d("values", message);
						this.messageAdapter.messageArrived(message.toString());
						Log.d("socket_test", message.toString());
						if (counter == 0) {
							constantValues.firstTimestamp = new JSONObject(message).getLong("time");
						}
						counter++;

					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d("socket_test", "IOException while receiving message!");
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("socket_test", "Exception while receiving message!");
				}
			}
			Log.d("socket_test", "disconnected!");
		}
	}

	private Thread commThread;

	public AndroidClient(String dst, int dstPort, MessageAdapter messageAdapter) {
		try {

			InetAddress inetAddress = InetAddress.getByName(dst);

			try {
				// a new Thread start to get the message from the Server.
				this.clientSocket = new Socket(inetAddress, dstPort);
				Log.d("socket_test", "Socket created, everything fine!");
				this.commThread = new Thread(new CommunicationThread(
						messageAdapter));
				this.commThread.start();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_kk_mm",
						Locale.GERMANY);
				constantValues.exportTime = sdf.format(new Date());
				constantValues.setActiveSensor(1);
				Log.d("socket_test", "connected");

			} catch (IOException e) {
				Log.d("socket_test", "Socket created, IO Exception!");
				Log.d("socket_test", e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				Log.d("socket_test", "Socket created, Exception!");
				e.printStackTrace();
			}
		} catch (UnknownHostException e1) {
			Log.d("socket_test", "Error while get Internet Address");
			e1.printStackTrace();
		}
	}

	public void startStreaming() {
		this.commThread.start();

	}

	public void stopStreaming() {
		this.commThread.interrupt();
	}

	public void disconnect() {
		try {
			this.clientSocket.close();
		} catch (IOException e) {
			Log.d("socket_test", "Exception while closing the socket!");
		}
	}
}
