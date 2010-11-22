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
		cfg.configure(new File("/path/to/hibernate.cfg.xml"));
		SchemaExport se = new SchemaExport(cfg);
		se.setOutputFile("/path/to/schema.sql");
		se.setFormat(true);
		se.create(false, false);		
	}
}
