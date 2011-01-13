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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

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
     * 
     * @return 0 - Return
     *         1 - StdOut
     *         2 - StdErr
     */
	public static Object[] runScript(File script) throws EvalError {
		Object[] ret = new Object[3];
		FileReader fr = null;
		
		try {
        	if (script.exists() && script.canRead()) {
        		ByteArrayOutputStream baosOut = new ByteArrayOutputStream();
    			PrintStream out = new PrintStream(baosOut);
    			ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
    			PrintStream err = new PrintStream(baosErr);
           		Interpreter i = new Interpreter(null, out, err, false);
        		fr = new FileReader(script);
				
				ret[0] = i.eval(fr);
				
				out.flush();
				ret[1] = baosOut.toString();
				err.flush();
				ret[2] = baosErr.toString();
        	} else {
        		log.warn("Unable to read script: {}", script.getPath());
        	}
        } catch (IOException e) {
        	log.warn(e.getMessage(), e);
        } finally {
        	IOUtils.closeQuietly(fr);
        }
        
        log.debug("runScript: {}", Arrays.toString(ret));
        return ret;
    }
	
	/**
     * Execute script
     * 
     * @return 0 - Return
     *         1 - StdOut
     *         2 - StdErr
     */
	public static Object[] runScript(String script) throws EvalError {
		Object[] ret = new Object[3];
		ByteArrayOutputStream baosOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baosOut);
		ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
		PrintStream err = new PrintStream(baosErr);
       	Interpreter i = new Interpreter(null, out, err, false);
		
		ret[0] = i.eval(script);
		
		out.flush();
		ret[1] = baosOut.toString();
		err.flush();
		ret[2] = baosErr.toString();
		
		log.debug("runScript: {}", Arrays.toString(ret));
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
    	
    	log.debug("runJar: {}", ret!=null?ret.toString():"null");
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
    	
    	log.debug("runJar: {}", ret!=null?ret.toString():"null");
        return ret;
	}
    
    /**
     * Execute command line
     */
    public static int runCmd(String cmd[], String stdout, String stderr) {
    	log.debug("runCmd({})", Arrays.toString(cmd));
    	StringBuilder sb = new StringBuilder();
    	BufferedReader br = null;
    	String line;
    	int ret = 0;
    	
    	try {
    		long start = System.currentTimeMillis();
    		ProcessBuilder pb = new ProcessBuilder(cmd);
    		Process process = pb.start();
    		br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    		
    		while ((line = br.readLine()) != null) {
    			sb.append(line);
    		}
    		
    		process.waitFor();
    		
    		// Check return code
    		if (process.exitValue() != 0) {
    			log.warn("Abnormal program termination: {}", process.exitValue());
    		} else {
    			log.debug("Normal program termination");
    		}
    		
    		process.destroy();
    		log.debug("Elapse time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
    		stderr = IOUtils.toString(process.getErrorStream());
    		ret = process.exitValue();
    		stderr = sb.toString();
    		stderr = IOUtils.toString(process.getErrorStream());
    	} catch (Exception e) {
			log.warn(Arrays.toString(cmd));
			log.warn("Failed to extract OCR text", e);
    	} finally {
    		IOUtils.closeQuietly(br);
    	}
    	
    	return ret;
    }
}
