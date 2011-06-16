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

package com.openkm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jllort
 *
 */
public class WebUtils {
	private static Logger log = LoggerFactory.getLogger(WebUtils.class);
	public static final String EMPTY_STRING = "";
	
	/**
	 * Extrae un parámetro de tipo String del request. Si el parámetro no existe devuelve
	 * un String vacio.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @return El valor String del parámetro o un String vacio si no existe.
	 */
	public static final String getString(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		String stringValue = EMPTY_STRING;
		
		if (value != null) {
			try {
				return new String(value.getBytes("ISO-8859-1"), "UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}
		
		return stringValue;
	}

	/**
	 * Extrae un parámetro de tipo String del request. Si el parámetro no existe devuelve
	 * el valor por defecto especificado.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @param Valor per defecto del parámetro.
	 * @return El valor String del parámetro o el valor por defecto si no existe.
	 */
	public static final String getString(HttpServletRequest request, String name, String defaultValue) {
		String value = request.getParameter(name);
		String stringValue = defaultValue;
		
		if (value != null) {
			try {
				return new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}
		
		return stringValue;
	}
	
	/**
	 * Extrae un parámetro de tipo String del request. Si el parámetro no existe devuelve
	 * un String vacio.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @return El valor String del parámetro o un String vacio si no existe.
	 */
	public static final List<String> getStringList(HttpServletRequest request, String name) {
		String[] value = request.getParameterValues(name);
		List<String> stringValue = new ArrayList<String>();
		
		if (value != null) {
			try {
				for (int i=0; i<value.length; i++) {
					stringValue.add(new String(value[i].getBytes("ISO-8859-1"), "UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}
		
		return stringValue;
	}
	
	/**
	 * Extrae un parámetro de tipo entero del request. 
	 * Si el parámetro no existe o no es valido devuelve 0.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @return El valor int del parámetro o 0 si no existe o no es valido.
	 */
	public static final int getInt(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		int intValue = 0;
		
		if (strValue != null && !EMPTY_STRING.equals(strValue)) {
			try {
				intValue = Integer.parseInt(strValue);
			} catch (Throwable t) {
				// Ignore
			}
		}
		
		return intValue;
	}
	
	/**
	 * Extrae un parámetro de tipo entero del request. 
	 * Si el parámetro no existe o no es valido devuelve el valor por defecto especificado.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @param defaultValue Valor per defecto
	 * @return El valor int del parámetro o el valor por defecto especificado si no existe o no es valido.
	 */
	public static final int getInt(HttpServletRequest request, String name, int defaultValue) {
		String strValue = request.getParameter(name);
		int intValue = defaultValue;
		
		if (strValue != null && !EMPTY_STRING.equals(strValue)) {
			try {
				intValue = Integer.parseInt(strValue);
			} catch (Throwable t) {
				// Ignore
			}
		}
		
		return intValue;
	}

	/**
	 * Extrae un parámetro de tipo long del request. 
	 * Si el parámetro no existe o no es valido devuelve 0.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @return El valor int del parámetro o 0 si no existe o no es valido.
	 */
	public static final long getLong(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		long longValue = 0;
		
		if (strValue != null && !EMPTY_STRING.equals(strValue)) {
			try {
				longValue = Long.parseLong(strValue);
			} catch (Throwable t) {
				// Ignore
			}
		}
		
		return longValue;
	}
	
	/**
	 * Extrae un parámetro de tipo float del request. 
	 * Si el parámetro no existe o no es valido devuelve 0.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @return El valor float del parámetro o 0 si no existe o no es valido.
	 */
	public static final float getFloat(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		float floatValue = 0;
		
		if (strValue != null && !EMPTY_STRING.equals(strValue)) {
			try {
				floatValue = Float.parseFloat(strValue);
			} catch (Throwable t) {
				// Ignore
			}
		}
		
		return floatValue;
	}
	
	/**
	 * Extrae un parámetro de tipo booleano del request. 
	 * Si el parámetro existe y no esta vacio devuelve true, en caso contrario devuelve false.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @return true si el parámetro existe y no esta vacio, false en caso contrario.
	 */
	public static final boolean getBoolean(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		return (strValue != null && !strValue.equals(EMPTY_STRING) && !strValue.equals("false"));
	}
	
	/**
	 * Extrae un parámetro de tipo booleano del request. 
	 * Si el parámetro existe y es igual al valor especificado devuelve true, en caso
	 * contrario devuelve false.
	 * @param request Petición de la que extraer el parámetro.
	 * @param name Nombre del parámetro
	 * @param trueValue Valor considerado true.
	 * @return true si el parámetro existe y es igual a trueValue, false en caso contrario.
	 */
	public static final boolean getBoolean(HttpServletRequest request, String name, String trueValue) {
		String strValue = request.getParameter(name);
		return (strValue != null && strValue.equals(trueValue));
	}
	
	/**
	 * Send file to client browser
	 * @throws IOException If there is a communication error.
	 */
	public static void sendFile(HttpServletRequest request, HttpServletResponse response, 
			String fileName, String mimeType, boolean inline, InputStream is) throws IOException {
		log.debug("sendFile({}, {}, {}, {}, {}, {})", new Object[] {request, response, fileName, mimeType, inline, is});
		String agent = request.getHeader("USER-AGENT");
		
		// Disable browser cache
		response.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		response.setHeader("Cache-Control", "max-age=0, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		
		// Set MIME type
		response.setContentType(mimeType);
		
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			log.debug("Agent: Explorer");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (null != agent && -1 != agent.indexOf("Mozilla"))	{
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}
		
		if (inline) {
			response.setHeader("Content-disposition", "inline; filename=\""+fileName+"\"");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=\""+fileName+"\"");
		}

		// Set length
		response.setContentLength(is.available());
		log.debug("File: {}, Length: {}", fileName, is.available());
		
		ServletOutputStream sos = response.getOutputStream();
		IOUtils.copy(is, sos);
		sos.flush();
		sos.close();
	}
}
