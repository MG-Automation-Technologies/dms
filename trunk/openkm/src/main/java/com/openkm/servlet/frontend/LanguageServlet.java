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

package com.openkm.servlet.frontend;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.LanguageDAO;
import com.openkm.dao.bean.Language;
import com.openkm.dao.bean.Translation;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.OKMLanguageService;

/**
 * LanguageServlet
 * 
 * @author jllort
 *
 */
public class LanguageServlet extends OKMRemoteServiceServlet implements OKMLanguageService {
	private static Logger log = LoggerFactory.getLogger(LanguageServlet.class);
	private static final long serialVersionUID = -879908904295685769L;

	@Override
	public Map<String, String> getFrontEndTranslations(String lang) throws OKMException {
		log.debug("getTranslations({})", lang);
		Map<String,String> translations = new HashMap<String,String>();
		
		try {
			Language language = LanguageDAO.findByPk(lang);
			
			if (language != null) {
				for (Translation translation : language.getTranslations()) {
					if (translation.getTranslationId().getModule().equals(Translation.MODULE_FRONTEND) ||
							translation.getTranslationId().getModule().equals(Translation.MODULE_EXTENSION)) {
						// Module is added module name as starting translation key
						translations.put(translation.getTranslationId().getModule() + "."+
								translation.getTranslationId().getKey(), translation.getText());
					}
				}
			}
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		
		log.debug("getTranslations: {}", translations);
		return translations;
	}
	
	public void getLanguages() {
		
	}
}
