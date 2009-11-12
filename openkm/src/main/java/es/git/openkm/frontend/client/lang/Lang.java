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

package es.git.openkm.frontend.client.lang;

import java.util.HashMap;

/**
 * Language
 * 
 * @author jllort
 *
 */
public class Lang {
	
	// Languages
	public static final String LANG_es_ES = "es-ES"; 
	public static final String LANG_ca_ES = "ca-ES";
	public static final String LANG_gl_ES = "gl-ES";
	public static final String LANG_en_GB = "en-GB";
	public static final String LANG_fr_FR = "fr-FR";
	public static final String LANG_pt_BR = "pt-BR";
	public static final String LANG_nl_NL = "nl-NL";
	public static final String LANG_fa_FA = "fa-FA";
	public static final String LANG_de_DE = "de-DE";
	public static final String LANG_it_IT = "it-IT";
	public static final String LANG_zh_CN = "zh-CN";
	public static final String LANG_sv_SE = "sv-SE";
	public static final String LANG_sr_BA = "sr-BA";
	public static final String LANG_tr_TR = "tr-TR";
	public static final String LANG_ja_JP = "ja-JP";
	public static final String LANG_ro_RO = "ro-RO";
	public static final String LANG_pl_PL = "pl-PL";
	public static final String LANG_hu_HU = "hu-HU";
	public static final String LANG_el_GR = "el-GR";
	public static final String LANG_zh_TW = "zh-TW";
	public static final String LANG_lv_LV = "lv-LV";
	public static final String LANG_mk_MK = "mk-KM";
	public static final String LANG_co_ES = "co-ES";
	public static final String LANG_ru_RU = "ru-RU";
	public static final String LANG_bs_BA = "bs-BA";

	public static HashMap<String, String> getLang(String lang) {
		HashMap<String, String> hLang = new HashMap<String, String>();
		
		if (LANG_es_ES.equalsIgnoreCase(lang) || LANG_es_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2)))  {
			hLang = Lang_es_ES.lang;
		} else if (LANG_ca_ES.equalsIgnoreCase(lang) || LANG_ca_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_ca_ES.lang;
		} else if (LANG_gl_ES.equalsIgnoreCase(lang) || LANG_gl_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_gl_ES.lang;
		} else if (LANG_en_GB.equalsIgnoreCase(lang) || LANG_en_GB.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_en_GB.lang;
		} else if (LANG_fr_FR.equalsIgnoreCase(lang) || LANG_fr_FR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_fr_FR.lang;
		} else if (LANG_pt_BR.equalsIgnoreCase(lang) || LANG_pt_BR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_pt_BR.lang;
		} else if (LANG_nl_NL.equalsIgnoreCase(lang) || LANG_nl_NL.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_nl_NL.lang;
		} else if (LANG_fa_FA.equalsIgnoreCase(lang) || LANG_fa_FA.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_fa_FA.lang;
		} else if (LANG_de_DE.equalsIgnoreCase(lang) || LANG_de_DE.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_de_DE.lang;
		} else if (LANG_it_IT.equalsIgnoreCase(lang) || LANG_it_IT.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_it_IT.lang;
		} else if (LANG_zh_CN.equalsIgnoreCase(lang) || (LANG_zh_CN.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2)) && !LANG_zh_TW.equalsIgnoreCase(lang) )) {
			hLang = Lang_zh_CN.lang;
		} else if (LANG_sv_SE.equalsIgnoreCase(lang) || LANG_sv_SE.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_sv_SE.lang;
		} else if (LANG_sr_BA.equalsIgnoreCase(lang) || LANG_sr_BA.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_sr_BA.lang;
		} else if (LANG_tr_TR.equalsIgnoreCase(lang) || LANG_tr_TR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_tr_TR.lang;
		} else if (LANG_ja_JP.equalsIgnoreCase(lang) || LANG_ja_JP.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_ja_JP.lang;
		} else if (LANG_ro_RO.equalsIgnoreCase(lang) || LANG_ro_RO.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_ro_RO.lang;
		} else if (LANG_pl_PL.equalsIgnoreCase(lang) || LANG_pl_PL.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_pl_PL.lang;
		} else if (LANG_hu_HU.equalsIgnoreCase(lang) || LANG_hu_HU.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_hu_HU.lang;
		} else if (LANG_el_GR.equalsIgnoreCase(lang) || LANG_el_GR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_el_GR.lang;
		} else if (LANG_zh_TW.equalsIgnoreCase(lang) || LANG_zh_TW.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_zh_TW.lang;
		} else if (LANG_lv_LV.equalsIgnoreCase(lang) || LANG_lv_LV.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_lv_LV.lang;
		} else if (LANG_mk_MK.equalsIgnoreCase(lang) || LANG_mk_MK.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_mk_MK.lang;
		} else if (LANG_co_ES.equalsIgnoreCase(lang) || LANG_co_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_co_ES.lang;
		} else if (LANG_ru_RU.equalsIgnoreCase(lang) || LANG_ru_RU.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_ru_RU.lang;
		} else if (LANG_bs_BA.equalsIgnoreCase(lang) || LANG_bs_BA.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_bs_BA.lang;
		} else {
			hLang = Lang_en_GB.lang;
		}
		
		return hLang;
	}
}
