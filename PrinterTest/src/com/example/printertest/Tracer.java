package com.example.printertest;

import android.util.Log;

public class Tracer {

	private final static Boolean LOG_ENABLE = true;

	public static void debug(String TAG, String message) {
		if (LOG_ENABLE) {
			Log.i(TAG, message);
		}
	}

	public static void error(String TAG, String message) {
		if (LOG_ENABLE) {
			Log.e(TAG, message);
		}
	}

	public static void d(String TAG, String message) {
		if (LOG_ENABLE) {
			Log.d(TAG, message);
		}
	}

	public static void imp(String TAG, String message) {
		if (LOG_ENABLE) {
			Log.i(TAG + "_imp", message);
		}
	}

	public static void in(String TAG, String message) {

		Log.i(TAG + "_info", message);

	}

	public static void er(String TAG, String message) {

		Log.e(TAG + "_error", message);

	}

}
