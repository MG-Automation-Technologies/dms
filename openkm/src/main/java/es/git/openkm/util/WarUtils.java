package es.git.openkm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.AppVersion;

public class WarUtils {
	private static Logger log = LoggerFactory.getLogger(WarUtils.class);
	private static AppVersion appVersion = null;
	
	/**
	 * 
	 */
	public static AppVersion getAppVersion() {
		return appVersion;
	}
	
	/**
	 * 
	 */
	public static synchronized void readAppVersion(ServletContext sc) {
		String appServerHome = sc.getRealPath("/");
		File manifestFile = new File(appServerHome, "META-INF/MANIFEST.MF");
		FileInputStream fis = null;
		appVersion = new AppVersion();
		
		try {
			fis = new FileInputStream(manifestFile);
			Manifest mf = new Manifest();
			mf.read(fis);
			Attributes atts = mf.getMainAttributes();
			String impVersion = atts.getValue("Implementation-Version");
			String impBuild = atts.getValue("Implementation-Build");
			
			if (impVersion != null && impBuild != null) {
				if (impVersion.indexOf('.') > 0) {
					String[] version = impVersion.split(".");
					appVersion.setMajor(version[0]);
					appVersion.setMinor(version[1]);
					appVersion.setBuild(impBuild);
				} else {
					log.warn("Implementation-Version: "+impVersion);
					log.warn("Implementation-Build: "+impBuild);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}
}
