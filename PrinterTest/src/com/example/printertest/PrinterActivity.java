package com.example.printertest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PrinterActivity extends Activity {

	private Button bprint;
	private static final String IP_ADDRESS = "10.2.1.56";
	private static final int PORT_NUMBER = 9100;
	private static final String TAG = "Printer";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		bprint = (Button) findViewById(R.id.btnprint);
		bprint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tracer.debug(TAG, "[onClick] _ " + "button print");
				PrinterTask pTask = new PrinterTask(IP_ADDRESS, PORT_NUMBER);
				pTask.execute();
			}
		});

	}

	public class PrinterTask extends AsyncTask<Void, Void, Void> {

		private String portAddress;
		private int portNumber;
		private File sdcard, file;

		public PrinterTask(String address, int port) {
			Tracer.debug(TAG, "[PrinterTask] _ " + "");
			portAddress = address;
			portNumber = port;
			fetchFile();
		}

		/**
		 * Fetch file from SD card and use to print it.
		 */
		private void fetchFile() {
			Tracer.debug(TAG, "[fetchFile] _ " + "");
			try {
				sdcard = Environment.getExternalStorageDirectory();
				file = new File(sdcard, "delhi.prn");
				Tracer.debug(TAG,
						"[fetchFile] _ " + "File size" + file.length());
			} catch (Exception e) {
				Tracer.debug(TAG, "[fetchFile] _ " + "catch" + e.toString());
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Tracer.debug(TAG, "[doInBackground] _ " + "");
				Socket socket;
				socket = new Socket(portAddress, portNumber);
				byte[] buffer = new byte[1024];
				OutputStream out = socket.getOutputStream();
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(file));
				int len;
				Tracer.debug(TAG, "[doInBackground] _ " + "before while");
				while ((len = in.read(buffer)) != -1) {
					Tracer.debug(TAG, "[doInBackground] _ " + "sending");
				    out.write(buffer, 0, len);
				}
				Tracer.debug(TAG, "[doInBackground] _ " + "after while");
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				Tracer.debug(TAG, "[doInBackground] _ " + "socket io exception"
						+ e.toString());
			}

			return null;
		}

	}

}
