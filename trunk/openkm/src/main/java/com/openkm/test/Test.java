package com.openkm.test;

import java.io.File;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class Test {
	/**
	 * Only for testing purposes
	 */
	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		cfg.configure(new File("/home/pavila/Desarrollo/workspaces/openkm/openkm/hibernate.cfg.xml"));
		SchemaExport se = new SchemaExport(cfg);
		se.setOutputFile("/home/pavila/Desarrollo/workspaces/openkm/openkm/salida.sql");
		se.setFormat(true);
		se.create(false, false);		
	}
}
