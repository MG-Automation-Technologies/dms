package com.openkm.webdav;

public class JcrSessionTokenHolder {
	private static ThreadLocal<String> ctx = new ThreadLocal<String>();

	public static void set(String token) {
		ctx.set(token);
	}
	
	public static String get() {
		return ctx.get();
	}
}
