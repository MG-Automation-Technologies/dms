/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

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
	public static Object runScript(File script) {
		Object ret = null;
		
		try {
        	FileReader fr = null;
        	
        	if (script.exists() && script.canRead()) {
        		Interpreter i = new Interpreter();
        		fr = new FileReader(script);
				
				try {
					ret = i.eval(fr);
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
        
        log.info("Script output: {}", ret!=null?ret.toString():"null");
        return ret;
    }
	
	/**
     * Execute script
     */
	public static Object runScript(String script) {
		Object ret = null;
		
    	try {
       		Interpreter i = new Interpreter();
				
			try {
				ret = i.eval(script);
			} catch (EvalError e) {
				log.warn(e.getMessage(), e);
			}
        } catch (Exception e) {
        	log.warn(e.getMessage(), e);
        }
        
        log.info("Script output: {}", ret!=null?ret.toString():"null");
        return ret;
    }
    
    /**
     * Execute jar from file
     */
    public static Object runJar(File jar) {
    	Object ret = null;
    	
    	try {
    		if (jar.exists() && jar.canRead()) {
    			ClassLoader cl = ExecutionUtils.class.getClass().getClassLoader(); 
    			JarClassLoader jcl = new JarClassLoader(jar.toURI().toURL(), cl);
    			String mainClass = jcl.getMainClassName();
    			
    			if (mainClass != null) {
    				Class<?> c = jcl.loadClass(jcl.getMainClassName());
    				ret = ClassLoaderUtils.invokeClass(c, new String[] {});
    			} else {
    				log.error("Main class not defined at: {}", jar.getPath());
    			}
    		} else {
    			log.warn("Unable to read jar: {}", jar.getPath());
    		}
    	} catch (Exception e) {
    		log.warn(e.getMessage(), e);
    	}
    	
    	log.info("JAR output: {}", ret!=null?ret.toString():"null");
        return ret;
	}
    
    /**
     * Execute jar
     */
    public static Object runJar(byte[] jar) {
    	Object ret = null;
    	
    	try {
   			ClassLoader cl = ExecutionUtils.class.getClass().getClassLoader(); 
   			BinaryClassLoader jcl = new BinaryClassLoader(jar, cl);
   			String mainClass = jcl.getMainClassName();
   			
   			if (mainClass != null) {
   				Class<?> c = jcl.loadClass(jcl.getMainClassName());
   				ret = ClassLoaderUtils.invokeClass(c, new String[] {});
   			} else {
   				log.error("Main class not defined at jar");
   			}
    	} catch (Exception e) {
    		log.warn(e.getMessage(), e);
    	}
    	
    	log.info("JAR output: {}", ret!=null?ret.toString():"null");
        return ret;
	}
}
