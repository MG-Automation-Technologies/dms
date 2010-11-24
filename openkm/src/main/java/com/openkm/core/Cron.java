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

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;

import com.kenai.crontabparser.CronTabExpression;
import com.openkm.dao.CronTabDAO;
import com.openkm.dao.bean.CronTab;
import com.openkm.util.ExecutionUtils;
import com.openkm.util.MailUtils;
import com.openkm.util.SecureStore;

public class Cron extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(Cron.class);

	public void run() {
		log.debug("*** Cron activated ***");
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
								RunnerBsh runner = new RunnerBsh(ct.getId(), ct.getMail(),  
										new String(SecureStore.b64Decode(ct.getFileContent())));
								new Thread(runner).start();
							} else if (CronTab.JAR.equals(ct.getType())) {
								RunnerJar runner = new RunnerJar(ct.getId(), ct.getMail(), 
										SecureStore.b64Decode(ct.getFileContent()));
								new Thread(runner).start();
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
	public static class RunnerBsh implements Runnable {
		private String script;
		private String mail;
		private int ctId;
		
		public RunnerBsh(int ctId, String mail, String script) {
			this.script = script;
			this.mail = mail;
			this.ctId = ctId;
		}
		
	    public void run() {
	    	if (script != null) {
	    		try {
					CronTabDAO.setLastBegin(ctId);
				} catch (DatabaseException e) {
					log.warn("Error setting last begin in crontab {}: {}", ctId, e.getMessage());
				}
	    		
				try {
					Object[] ret = ExecutionUtils.runScript(script);
					
					try {
						String msg = "Return: "+ret[0]+"\n\nStdOut: "+ret[1]+"\n\nStdErr: "+ret[2];
						MailUtils.sendMessage(mail, "Cron task executed - Ok", msg);
					} catch (MessagingException e) {
						log.warn("Error sending mail: {}", e.getMessage());
					}
				} catch (EvalError e) {
					try {
						String msg = e.toString();
						MailUtils.sendMessage(mail, "Cron task executed - Error", msg);
					} catch (MessagingException e1) {
						log.warn("Error sending mail: {}", e.getMessage());
					}
				}
				
	    		try {
					CronTabDAO.setLastEnd(ctId);
				} catch (DatabaseException e) {
					log.warn("Error setting last end in crontab {}: {}", ctId, e.getMessage());
				}
	    	}
	    }
	}
	
	/**
	 * Inner helper class
	 */
	public static class RunnerJar implements Runnable {
		private byte[] content;
		private String mail;
		private int ctId;
		
		public RunnerJar(int ctId, String mail, byte[] content) {
			this.content = content;
			this.mail = mail;
			this.ctId = ctId;
		}
		
	    public void run() {
	    	if (content != null) {
	    		try {
					CronTabDAO.setLastBegin(ctId);
				} catch (DatabaseException e) {
					log.warn("Error setting last begin in crontab {}: {}", ctId, e.getMessage());
				}
				
	    		Object ret = ExecutionUtils.runJar(content);
				
				if (ret != null) {
					try {
						MailUtils.sendMessage(mail, "Cron task executed", ret.toString());
					} catch (MessagingException e) {
						log.warn("Error sending mail: {}", e.getMessage());
					}
				}
				
				try {
					CronTabDAO.setLastEnd(ctId);
				} catch (DatabaseException e) {
					log.warn("Error setting last end in crontab {}: {}", ctId, e.getMessage());
				}
	    	}
	    }
	}
}
