/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
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

package com.openkm.extension.dao.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="OKM_EXTENSION_MD_VALUE")
public class ExtensionMetadataValue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="EMV_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="EMV_TABLE")
	private String table;
	
	@Column(name="EMV_COL00")
	private String col00;
	
	@Column(name="EMV_COL01")
	private String col01;
	
	@Column(name="EMV_COL02")
	private String col02;
	
	@Column(name="EMV_COL03")
	private String col03;
	
	@Column(name="EMV_COL04")
	private String col04;
	
	@Column(name="EMV_COL05")
	private String col05;
	
	@Column(name="EMV_COL06")
	private String col06;
	
	@Column(name="EMV_COL07")
	private String col07;
	
	@Column(name="EMV_COL08")
	private String col08;
	
	@Column(name="EMV_COL09")
	private String col09;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getCol00() {
		return col00;
	}

	public void setCol00(String col00) {
		this.col00 = col00;
	}

	public String getCol01() {
		return col01;
	}

	public void setCol01(String col01) {
		this.col01 = col01;
	}

	public String getCol02() {
		return col02;
	}

	public void setCol02(String col02) {
		this.col02 = col02;
	}

	public String getCol03() {
		return col03;
	}

	public void setCol03(String col03) {
		this.col03 = col03;
	}

	public String getCol04() {
		return col04;
	}

	public void setCol04(String col04) {
		this.col04 = col04;
	}

	public String getCol05() {
		return col05;
	}

	public void setCol05(String col05) {
		this.col05 = col05;
	}

	public String getCol06() {
		return col06;
	}

	public void setCol06(String col06) {
		this.col06 = col06;
	}

	public String getCol07() {
		return col07;
	}

	public void setCol07(String col07) {
		this.col07 = col07;
	}

	public String getCol08() {
		return col08;
	}

	public void setCol08(String col08) {
		this.col08 = col08;
	}

	public String getCol09() {
		return col09;
	}

	public void setCol09(String col09) {
		this.col09 = col09;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id="); sb.append(id);
		sb.append(", table="); sb.append(table);
		sb.append(", col00="); sb.append(col00);
		sb.append(", col01="); sb.append(col01);
		sb.append(", col02="); sb.append(col02);
		sb.append(", col03="); sb.append(col03);
		sb.append(", col04="); sb.append(col04);
		sb.append(", col05="); sb.append(col05);
		sb.append(", col06="); sb.append(col06);
		sb.append(", col07="); sb.append(col07);
		sb.append(", col08="); sb.append(col08);
		sb.append(", col09="); sb.append(col09);
		sb.append("}");
		return sb.toString();
	}
}
