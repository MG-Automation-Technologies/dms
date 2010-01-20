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

package com.openkm.backend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author jllort
 *
 */
public class GWTStatsInfo implements IsSerializable {
	private static final long serialVersionUID = -6769199508952083208L;
	
	private double[] percents;
	private String[] sizes;

	public double[] getPercents() {
		return percents;
	}

	public void setPercents(double[] percents) {
		this.percents = percents;
	}

	public String[] getSizes() {
		return sizes;
	}

	public void setSizes(String[] sizes) {
		this.sizes = sizes;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("sizes=["); for (int i=0; i<sizes.length-1; i++) sb.append(sizes[i]+","); sb.append(sizes[sizes.length-1]+"]");
		sb.append(", percents=["); for (int i=0; i<percents.length-1; i++) sb.append(percents[i]+","); sb.append(percents[percents.length-1]+"]");
		sb.append("]");
		return sb.toString();
	}
}
