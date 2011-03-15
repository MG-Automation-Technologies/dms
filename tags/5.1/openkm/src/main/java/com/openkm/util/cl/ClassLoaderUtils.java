package com.openkm.util.cl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassLoaderUtils {
	private static Logger log = LoggerFactory.getLogger(ClassLoaderUtils.class);
	
	/**
	 * Invoke class
	 */
	public static Object invokeClass(String className, String[] args, ClassLoader classLoader) throws 
			ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
		Class<?> c = classLoader.loadClass(className);
		return invokeClass(c, args);
	}
	
	/**
	 * Invoke class
	 */
	public static Object invokeClass(Class<?> c, String[] args) throws ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException {
		log.debug("invokeClass({}, {})", c, args);
		Method m = c.getMethod("main", new Class[] { args.getClass() });
		m.setAccessible(true);
		int mods = m.getModifiers();
		
		if (m.getReturnType() != void.class || !Modifier.isStatic(mods) || !Modifier.isPublic(mods)) {
			throw new NoSuchMethodException("main");
		}
		
		try {
			return m.invoke(null, new Object[] { args });
		} catch (IllegalAccessException e) {
			// This should not happen, as we have disabled access checks
		}
		
		return null;
	}
}
