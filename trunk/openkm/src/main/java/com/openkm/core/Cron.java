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

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenai.crontabparser.CronTabExpression;
import com.openkm.dao.CronTabDAO;
import com.openkm.dao.bean.CronTab;
import com.openkm.util.ExecutionUtils;

public class Cron extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(Cron.class);

	public void run() {
		log.info("*** Cron activated ***");
		Calendar cal = Calendar.getInstance();
		
		try {
			for (CronTab ct : CronTabDAO.findAll()) {
				try {
					CronTabExpression cte = CronTabExpression.parse(ct.getExpression());
					
					if (cte.matches(cal)) {
						if (CronTab.BSH.equals(ct.getType())) {
							ExecutionUtils.runScript(new String(ct.getFileContent()));
						} else if (CronTab.JAR.equals(ct.getType())) {
							ExecutionUtils.runJar(ct.getFileContent());
						}
					}
				} catch (ParseException e) {
					log.warn(e.getMessage() + " : " + ct.getExpression());
				}
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
		}
	}
}
