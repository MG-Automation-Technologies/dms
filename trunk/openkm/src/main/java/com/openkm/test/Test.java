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
		cfg.configure(new File("/path/to/openkm/src/main/resources/hibernate.cfg.xml"));
		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
		SchemaExport se = new SchemaExport(cfg);
		se.setOutputFile("/tmp/schema.sql");
		se.setFormat(true);
		se.create(false, false);		
	}
}
