package com.openkm.util.cl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesystemClassLoader extends ClassLoader implements MultipleClassLoader {
	private static Logger log = LoggerFactory.getLogger(FilesystemClassLoader.class);
	private File file = null;
		
	public FilesystemClassLoader(File file) throws IOException {
		super();
		this.file = file;
		
	}
	public FilesystemClassLoader(File file, ClassLoader parent) throws IOException {
		super(parent);
		this.file = file;
	}
	
	/**
	 * Get main class name
	 */
	@Override
	public String getMainClassName() throws IOException {
		log.debug("getMainClassName()");
		File mf = new File(file, "META-INF/MANIFEST.MF");
		FileInputStream fis = null;
		
		try {
			if (mf.exists() && mf.canRead()) {
				fis = new FileInputStream(mf);
				Manifest manif = new Manifest(fis);
		        Attributes attr = manif.getMainAttributes();
				return attr != null ? attr.getValue(Attributes.Name.MAIN_CLASS) : null;
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		
		return null;
	}
	
	/**
	 * Find class
	 */
	@Override
	public Class<?> findClass(String className) {
		log.debug("findClass({})", className);
		
		// Check for system class
		try {
			return findSystemClass(className);
		} catch (ClassNotFoundException e) {
			// Ignore
		}
		
		//byte[] classByte = resources.get(className);
		
		//if (classByte != null) {
			//ret = defineClass(className, classByte, 0, classByte.length, null);  
			//classes.put(className, ret);
			//resources.remove(className);
			//return ret;
		//}
		
		return null;
	}
	
	/**
	 * Get resource input stream
	 */
	@Override
	public InputStream getResourceAsStream(String name) {
		log.debug("getResourceAsStream({})", name);
		//byte[] bytes = resources.get(name);
		
		//if (bytes != null) {
			//return new ByteArrayInputStream(bytes);
		//}
		
		return null;
	}
}
