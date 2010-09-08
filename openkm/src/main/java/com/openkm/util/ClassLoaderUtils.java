package com.openkm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassLoaderUtils {
		
	/**
	 * Invoke class
	 * 
	 * Class<?> c = loadClass(name);
	 */
	public static void invokeClass(Class<?> c, String[] args) throws ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException {
		Method m = c.getMethod("main", new Class[] { args.getClass() });
		m.setAccessible(true);
		int mods = m.getModifiers();
		
		if (m.getReturnType() != void.class || !Modifier.isStatic(mods) || !Modifier.isPublic(mods)) {
			throw new NoSuchMethodException("main");
		}
		
		try {
			m.invoke(null, new Object[] { args });
		} catch (IllegalAccessException e) {
			// This should not happen, as we have disabled access checks
		}
	}
}
