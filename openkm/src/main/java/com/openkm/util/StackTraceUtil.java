package com.openkm.util;

public class StackTraceUtil {
	public static StackTraceElement whoCalledMe() {
		// The constructor for Trowable has a native function that fills the stack trace.
		StackTraceElement[] trace = (new Throwable()).getStackTrace();
		
		// Once you have the trace you can pick out information you need.
		if (trace.length >= 2) {
			return trace[2];
		}
		
		return null;
	}
}
