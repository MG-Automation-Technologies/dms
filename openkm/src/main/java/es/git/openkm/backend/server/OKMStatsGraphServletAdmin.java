/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.backend.server;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.StatsInfo;
import es.git.openkm.core.RepositoryInfo;

/**
 * Stats graphical servlet
 */
public class OKMStatsGraphServletAdmin extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(OKMStatsGraphServletAdmin.class);
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Documents by context";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			response.setContentType("image/png");
			StatsInfo si = RepositoryInfo.getDocumentsByContext();
			String[] sizes = si.getSizes();
			DefaultPieDataset dataset = new DefaultPieDataset();
			
			for (int i=0; i<sizes.length; i++) {
				dataset.setValue(sizes[i], new Integer(sizes[i]));
			}
			
			JFreeChart chart = ChartFactory.createPieChart(TITLE, dataset, true, true, true);
			OutputStream out = response.getOutputStream();
			ChartUtilities.writeChartAsPNG(out, chart, 300, 300);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
