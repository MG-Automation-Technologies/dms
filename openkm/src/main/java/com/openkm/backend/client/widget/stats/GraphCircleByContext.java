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

package com.openkm.backend.client.widget.stats;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.googlecode.gchart.client.GChart;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.util.Util;

public class GraphCircleByContext extends GChart {
	
	public final static int VALUE_NUMERIC = 0;
	public final static int VALUE_SIZE = 1;
	
	public GraphCircleByContext(double[] pieMarketShare, String[] pieValues, String[] pieTypes, String charTitle, String keyType, int valueType)  {
		FlexTable table = new FlexTable();
		String[] pieColors = {"green", "red", "maroon", "yellow"};
		long total = 0;
		  
		setChartSize(150, 120);
		setChartTitle("<b>" + charTitle + "</b>");
		setChartTitleThickness(32);
		setLegendVisible(true);
		getXAxis().setAxisVisible(false);
		getYAxis().setAxisVisible(false);
		getXAxis().setAxisMin(0);
		getXAxis().setAxisMax(10);
		getXAxis().setTickCount(0);
		getYAxis().setAxisMin(0);
		getYAxis().setAxisMax(10);
		getYAxis().setTickCount(0);
		
		// this line orients the center of the first slice (apple) due east
		setInitialPieSliceOrientation(0.75 - pieMarketShare[0]/2);
		
		for (int i=0; i < pieMarketShare.length; i++) {
			// Only we add on graphics when values are greater than 0
			if (!pieValues[i].equals("0")) {
				addCurve();
				getCurve().addPoint(5,5);
				getCurve().getSymbol().setSymbolType(SymbolType.PIE_SLICE_OPTIMAL_SHADING);
				getCurve().getSymbol().setBorderColor("white");
				getCurve().getSymbol().setBackgroundColor(pieColors[i]);

				// next two lines define pie diameter as 6 x-axis model units
				getCurve().getSymbol().setModelWidth(6);
				getCurve().getSymbol().setHeight(0);
				getCurve().getSymbol().setFillSpacing(2);
				getCurve().getSymbol().setFillThickness(3);
				getCurve().getSymbol().setHovertextTemplate(pieTypes[i]);
				getCurve().getSymbol().setPieSliceSize((pieMarketShare[i]<0.03)?0.03:pieMarketShare[i]);
				getCurve().getPoint().setAnnotationText(getCurve().getSymbol().getHovertextTemplate());
				getCurve().getPoint().setAnnotationLocation(AnnotationLocation.OUTSIDE_PIE_ARC);
			}
			// Create aditional table information on foot
			int rows = table.getRowCount();
			table.setHTML(rows, 0, "<b>" + pieTypes[i] + ":</b>");
			switch (valueType) {
			 	case VALUE_NUMERIC:
			 		table.setHTML(rows, 1, pieValues[i] + " " +Main.i18n(keyType));
			 		break;
			 	
			 	case VALUE_SIZE:
			 		table.setHTML(rows, 1, Util.formatSize(Long.parseLong(pieValues[i])));
			 		break;
			 }
			
			table.setHTML(rows, 2, "( " + Math.round(100*pieMarketShare[i])+"% )");
			table.getCellFormatter().setHorizontalAlignment(rows, 1, HasAlignment.ALIGN_RIGHT);
			total += Long.parseLong(pieValues[i]);
		 }
		 int rows = table.getRowCount();
		 table.setHTML(rows, 0, "<b>" +Main.i18n("stats.context.total") + ":</b>");
		 switch (valueType) {
		 	case VALUE_NUMERIC:
		 		table.setHTML(rows, 1, total + " " + Main.i18n(keyType));
		 		break;
		 	
		 	case VALUE_SIZE:
		 		table.setHTML(rows, 1, Util.formatSize(total));
		 		break;
		 }
		 table.setHTML(rows, 2, "");
		 table.getCellFormatter().setHorizontalAlignment(rows, 1, HasAlignment.ALIGN_RIGHT);
		 table.addStyleName("okm-NoWrap");
		 setChartFootnotes(table);
		 setChartFootnotesThickness(100);
		 update();
	}
}