/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package com.openkm.core;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenai.crontabparser.CronTabExpression;
import com.openkm.dao.CronTabDAO;
import com.openkm.dao.bean.CronTab;
import com.openkm.util.ExecutionUtils;
import com.openkm.util.SecureStore;

public class Cron extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(Cron.class);

	public void run() {
		log.info("*** Cron activated ***");
		Calendar cal = Calendar.getInstance();
		
		try {
			for (CronTab ct : CronTabDAO.findAll()) {
				if (ct.isActive()) {
					try {
						CronTabExpression cte = CronTabExpression.parse(ct.getExpression());
						
						if (cte.matches(cal)) {
							log.info("Id: {}, Name: {}, Type: {}", new Object[] {ct.getId(), ct.getName(),
									ct.getType()});
							
							if (CronTab.BSH.equals(ct.getType())) {
								RunnerBsh run = new RunnerBsh(new String(SecureStore.b64Decode(ct.getFileContent())));
								new Thread(run).start();
							} else if (CronTab.JAR.equals(ct.getType())) {
								RunnerJar run = new RunnerJar(SecureStore.b64Decode(ct.getFileContent()));
								new Thread(run).start();
							}
						}
					} catch (ParseException e) {
						log.warn(e.getMessage() + " : " + ct.getExpression());
					} catch (IOException e) {
						log.warn(e.getMessage());
					}
				}
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Inner helper class
	 */
	public class RunnerBsh implements Runnable {
		private String script;
		
		public RunnerBsh(String script) {
			this.script = script;
		}
		
	    public void run() {
	    	if (script != null) {
	    		ExecutionUtils.runScript(script);
	    	}
	    }
	}
	
	/**
	 * Inner helper class
	 */
	public class RunnerJar implements Runnable {
		private byte[] content;
		
		public RunnerJar(byte[] content) {
			this.content = content;
		}
		
	    public void run() {
	    	if (content != null) {
	    		ExecutionUtils.runJar(content);
	    	}
	    }
	}
}
