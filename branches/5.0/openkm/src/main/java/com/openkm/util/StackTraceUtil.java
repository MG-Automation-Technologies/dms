package com.openkm.util;

public class StackTraceUtil {
	
	/**
	 * Return the method who make the call.
	 */
	public static StackTraceElement whoCalledMe() {
		// The constructor for Throwable has a native function that fills the stack trace.
		StackTraceElement[] trace = (new Throwable()).getStackTrace();
		
		// Once you have the trace you can pick out information you need.
		if (trace.length >= 2) {
			return trace[2];
		}
		
		return null;
	}
	
	/**
	 * Return the whole call trace.
	 */
	public static String getTrace() {
		// The constructor for Throwable has a native function that fills the stack trace.
		StackTraceElement[] trace = (new Throwable()).getStackTrace();
		StringBuilder sb = new StringBuilder();
		
		// Once you have the trace you can pick out information you need.
		if (trace.length >= 2) {
			for (int i=2; i<trace.length; i++) {
				if (trace[i].getClassName().startsWith("com.openkm")) {
					sb.append(trace[i]);
					sb.append("\n");
				}
			}
		}
		
		return sb.toString();
	}
}
