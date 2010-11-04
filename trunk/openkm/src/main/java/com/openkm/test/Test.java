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
		//cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		//cfg.configure(new File("/home/jllort/softwareFactoryGalileo/openkm/src/main/resources/hibernate.cfg.xml"));
		cfg.configure(new File("/path/to/hibernate.cfg.xml"));
		SchemaExport se = new SchemaExport(cfg);
		//se.setOutputFile("/home/jllort/Escritorio/schema.sql");
		se.setOutputFile("/path/to/Escritorio/schema.sql");
		se.setFormat(true);
		se.create(false, false);		
	}
}
