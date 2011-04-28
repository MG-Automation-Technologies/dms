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

package es.git.openkm.backend.client.bean;

import java.util.Collection;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GWTMetaData implements IsSerializable {

	public static final int INPUT = 1;
	public static final int TEXT_AREA = 2;
	public static final int SELECT = 3;
	public static final int SELECT_MULTI = 4;
	
	private int type;
	
	private Collection<String> values;
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public Collection<String> getValues() {
		return values;
	}
	
	public void setValues(Collection<String> values) {
		this.values = values;
	}

}