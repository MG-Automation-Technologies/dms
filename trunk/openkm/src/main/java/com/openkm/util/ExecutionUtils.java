package com.openkm.util;

import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;

import com.openkm.util.cl.BinaryClassLoader;
import com.openkm.util.cl.ClassLoaderUtils;
import com.openkm.util.cl.JarClassLoader;

public class ExecutionUtils {
	private static Logger log = LoggerFactory.getLogger(ExecutionUtils.class);
	
	/**
     * Execute script from file
     */
	public static void runScript(File script) {
    	try {
        	FileReader fr = null;
        	
        	if (script.exists() && script.canRead()) {
        		Interpreter i = new Interpreter();
        		fr = new FileReader(script);
				
				try {
					Object result = i.eval(fr);
					log.info("Script output: "+(result!=null?result.toString():"null"));
				} catch (EvalError e) {
					log.warn(e.getMessage(), e);
				} finally {
					IOUtils.closeQuietly(fr);
				}
        	} else {
        		log.warn("Unable to read script: {}", script.getPath());
        	}
        } catch (Exception e) {
        	log.warn(e.getMessage(), e);
        }
    }
	
	/**
     * Execute script
     */
	public static void runScript(String script) {
    	try {
       		Interpreter i = new Interpreter();
				
			try {
				Object result = i.eval(script);
				log.info("Script output: "+(result!=null?result.toString():"null"));
			} catch (EvalError e) {
				log.warn(e.getMessage(), e);
			}
        } catch (Exception e) {
        	log.warn(e.getMessage(), e);
        }
    }
    
    /**
     * Execute jar from file
     */
    public static void runJar(File jar) {
    	try {
    		if (jar.exists() && jar.canRead()) {
    			ClassLoader cl = ExecutionUtils.class.getClass().getClassLoader(); 
    			JarClassLoader jcl = new JarClassLoader(jar.toURI().toURL(), cl);
    			String mainClass = jcl.getMainClassName();
    			
    			if (mainClass != null) {
    				Class<?> c = jcl.loadClass(jcl.getMainClassName());
    				ClassLoaderUtils.invokeClass(c, new String[] {});
    			} else {
    				log.error("Main class not defined at: {}", jar.getPath());
    			}
    		} else {
    			log.warn("Unable to read jar: {}", jar.getPath());
    		}
    	} catch (Exception e) {
    		log.warn(e.getMessage(), e);
    	}
	}
    
    /**
     * Execute jar
     */
    public static void runJar(byte[] jar) {
    	try {
   			ClassLoader cl = ExecutionUtils.class.getClass().getClassLoader(); 
   			BinaryClassLoader jcl = new BinaryClassLoader(jar, cl);
   			String mainClass = jcl.getMainClassName();
   			
   			if (mainClass != null) {
   				Class<?> c = jcl.loadClass(jcl.getMainClassName());
   				ClassLoaderUtils.invokeClass(c, new String[] {});
   			} else {
   				log.error("Main class not defined at jar");
   			}
    	} catch (Exception e) {
    		log.warn(e.getMessage(), e);
    	}
	}
}
