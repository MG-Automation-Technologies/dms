/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
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

package com.openkm.principal.auth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

/**
 * CompleteAuthValidator
 * 
 * @author jllort
 *
 */
public class CompleteAuthValidator implements AuthValidator {
	
	private static Logger log = LoggerFactory.getLogger(CompleteAuthValidator.class);
	
	private static final String PROPERTY_MINIMUM_PASSWORD_LENGTH = "application.auth.validator.minimum.password.length";
	private static final String PROPERTY_MAXIMUM_PASSWORD_LENGTH = "application.auth.validator.maximum.password.length";
	private static final String PROPERTY_MINIMUM_PASSWORD_LOWERCASE_CHARACTERS = "application.auth.validator.minimum.password.lowercase.characters";
	private static final String PROPERTY_MINIMUM_PASSWORD_UPPERCASE_CHARACTERS = "application.auth.validator.minimum.password.uppercase.characters";
	private static final String PROPERTY_MINIMUM_PASSWORD_NUMBERS = "application.auth.validator.minimum.password.numbers";
	private static final String PROPERTY_MINIMUM_PASSWORD_SPECIAL_CHARACTERS = "application.auth.validator.minimum.password.special.characters";
	
	private static final String PROPERTY_MINIMUM_PASSWORD_LENGTH_ERROR = "application.auth.validator.error.minimum.password.length";
	private static final String PROPERTY_MAXIMUM_PASSWORD_LENGTH_ERROR = "application.auth.validator.error.maximum.password.length";	
	private static final String PROPERTY_MINIMUM_PASSWORD_LOWERCASE_CHARACTERS_ERROR = "application.auth.validator.error.minimum.password.lowercase.characters";
	private static final String PROPERTY_MAXIMUM_PASSWORD_UPPERCASE_CHARACTERS_ERROR = "application.auth.validator.error.maximum.password.uppercase.characters";
	private static final String PROPERTY_MINIMUM_PASSWORD_NUMBERS_ERROR = "application.auth.validator.error.minimum.password.numbers";
	private static final String PROPERTY_MINIMUM_PASSWORD_SPECIAL_CHARACTERS_ERROR = "application.auth.validator.error.minimum.password.special.characters";
	
	private int MINIMUM_PASSWORD_LENGTH = 0;
	private int MAXIMUM_PASSWORD_LENGTH = 0;
	private int MINIMUM_PASSWORD_LOWERCASE_CHARACTERS = 0;
	private int MINIMUM_PASSWORD_UPPERCASE_CHARACTERS = 0;
	private int MINIMUM_PASSWORD_NUMBERS = 0;
	private int MINIMUM_PASSWORD_SPECIAL_CHARACTERS = 0;
	private String MINIMUM_ERROR_PASSWORD_LENGHT = "Password error, lenght is too short";
	private String MAXIMUM_ERROR_PASSWORD_LENGHT = "Password error, lenght is too long";
	private String MINIMUM_ERROR_PASSWORD_LOWERCASE_CHARACTERS = "Password error, number of lowercase characters is too low";
	private String MINIMUM_ERROR_PASSWORD_UPPERCASE_CHARACTERS = "Password error, number of uppercase characters is too low";
	private String MINIMUM_ERROR_PASSWORD_NUMBERS = "Password error, number of numeric characters is too low";
	private String MINIMUM_ERROR_PASSWORD_SPECIAL_CHARACTERS = "Password error, number of special characters is too low";
	
	private String password = "";
	
	@Override
	public void Validate(String password) throws AuthException {
		this.password = password;
		// Loading variables
		load();
		
		// Validating 
		validateLength();
		int numLowercase = validateLowercaseCharacters();
		int numUppercase = validateUppercaseCharacters();
		int numNumeric = validateNumbers();
		validateSpecialCharacters(numLowercase+numUppercase+ numNumeric);
	}
	
	/**
	 * validateLength
	 * 
	 * @throws AuthException
	 */
	private void validateLength() throws AuthException {
		if (MINIMUM_PASSWORD_LENGTH>0) {
			if (password.length()<MINIMUM_PASSWORD_LENGTH) {
				throw new AuthException(MINIMUM_ERROR_PASSWORD_LENGHT); 
			}
		}
		
		if (MAXIMUM_PASSWORD_LENGTH>0) {
			if (password.length()>MAXIMUM_PASSWORD_LENGTH) {
				throw new AuthException(MAXIMUM_ERROR_PASSWORD_LENGHT); 
			}
		}
	}
	
	/**
	 * validateLowercaseCharacters
	 * 
	 * @return
	 * @throws AuthException
	 */
	private int validateLowercaseCharacters() throws AuthException {
		int count = 0;
		if (MINIMUM_PASSWORD_LOWERCASE_CHARACTERS>0) {
			char[] charArray = password.toCharArray();
			int aValue = Character.getNumericValue('a');
			int zValue = Character.getNumericValue('z');
			
			for (int i=0; i<charArray.length; i++) {
				if (Character.getNumericValue(charArray[i])>= aValue && Character.getNumericValue(charArray[i])<=zValue) {
					count ++;
				}
			}
			
			if (MINIMUM_PASSWORD_LOWERCASE_CHARACTERS>count) {
				throw new AuthException(MINIMUM_ERROR_PASSWORD_LOWERCASE_CHARACTERS); 
			}
		}
		return count;
	}
	
	/**
	 * validateUppercaseCharacters
	 * 
	 * @return
	 * @throws AuthException
	 */
	private int validateUppercaseCharacters() throws AuthException {
		int count = 0;
		if (MINIMUM_PASSWORD_UPPERCASE_CHARACTERS>0) {
			char[] charArray = password.toCharArray();
			int aValue = Character.getNumericValue('A');
			int zValue = Character.getNumericValue('Z');
			
			for (int i=0; i<charArray.length; i++) {
				if (Character.getNumericValue(charArray[i])>= aValue && Character.getNumericValue(charArray[i])<=zValue) {
					count ++;
				}
			}
			
			if (MINIMUM_PASSWORD_UPPERCASE_CHARACTERS>count) {
				throw new AuthException(MINIMUM_ERROR_PASSWORD_UPPERCASE_CHARACTERS); 
			}
		}
		return count;
	}
	
	/**
	 * validateNumbers
	 *
	 * @return
	 * @throws AuthException
	 */
	private int validateNumbers() throws AuthException {
		int count = 0;
		if (MINIMUM_PASSWORD_NUMBERS>0) {
			char[] charArray = password.toCharArray();
			int aValue = Character.getNumericValue('0');
			int zValue = Character.getNumericValue('9');
			
			for (int i=0; i<charArray.length; i++) {
				if (Character.getNumericValue(charArray[i])>= aValue && Character.getNumericValue(charArray[i])<=zValue) {
					count ++;
				}
			}
			
			if (MINIMUM_PASSWORD_NUMBERS>count) {
				throw new AuthException(MINIMUM_ERROR_PASSWORD_NUMBERS); 
			}
		}
		return count;
	}
	
	/**
	 * validateSpecialCharacters
	 * 
	 * @param nonSpecial
	 * @throws AuthException
	 */
	private void validateSpecialCharacters(int nonSpecial) throws AuthException {
		if (MINIMUM_PASSWORD_SPECIAL_CHARACTERS>0) {
			if (MINIMUM_PASSWORD_SPECIAL_CHARACTERS>(password.length()-nonSpecial)) {
				throw new AuthException(MINIMUM_ERROR_PASSWORD_SPECIAL_CHARACTERS); 
			}
		}
	}
	
	/**
	 * load
	 */
	private void load() {
		Properties config = new Properties();
		String configFile = Config.HOME_DIR+"/"+Config.CONFIG_FILE;
		
		// Read config
		try {
			log.info("** Reading config file " + configFile + " **");
			FileInputStream fis = new FileInputStream(configFile);
			config.load(fis);
			
			MINIMUM_PASSWORD_LENGTH = Integer.valueOf(config.getProperty(PROPERTY_MINIMUM_PASSWORD_LENGTH, String.valueOf(MINIMUM_PASSWORD_LENGTH)));
			MAXIMUM_PASSWORD_LENGTH = Integer.valueOf(config.getProperty(PROPERTY_MAXIMUM_PASSWORD_LENGTH, String.valueOf(MAXIMUM_PASSWORD_LENGTH)));
			MINIMUM_PASSWORD_LOWERCASE_CHARACTERS = Integer.valueOf(config.getProperty(PROPERTY_MINIMUM_PASSWORD_LOWERCASE_CHARACTERS, String.valueOf(MINIMUM_PASSWORD_LOWERCASE_CHARACTERS)));
			MINIMUM_PASSWORD_UPPERCASE_CHARACTERS = Integer.valueOf(config.getProperty(PROPERTY_MINIMUM_PASSWORD_UPPERCASE_CHARACTERS, String.valueOf(MINIMUM_PASSWORD_UPPERCASE_CHARACTERS)));
			MINIMUM_PASSWORD_NUMBERS = Integer.valueOf(config.getProperty(PROPERTY_MINIMUM_PASSWORD_NUMBERS, String.valueOf(MINIMUM_PASSWORD_NUMBERS)));
			MINIMUM_PASSWORD_SPECIAL_CHARACTERS = Integer.valueOf(config.getProperty(PROPERTY_MINIMUM_PASSWORD_SPECIAL_CHARACTERS, String.valueOf(MINIMUM_PASSWORD_SPECIAL_CHARACTERS)));
			
			MINIMUM_ERROR_PASSWORD_LENGHT = config.getProperty(PROPERTY_MINIMUM_PASSWORD_LENGTH_ERROR, MINIMUM_ERROR_PASSWORD_LENGHT);
			MAXIMUM_ERROR_PASSWORD_LENGHT = config.getProperty(PROPERTY_MAXIMUM_PASSWORD_LENGTH_ERROR, MAXIMUM_ERROR_PASSWORD_LENGHT);
			MINIMUM_ERROR_PASSWORD_LOWERCASE_CHARACTERS = config.getProperty(PROPERTY_MINIMUM_PASSWORD_LOWERCASE_CHARACTERS_ERROR, MINIMUM_ERROR_PASSWORD_LOWERCASE_CHARACTERS);
			MINIMUM_ERROR_PASSWORD_UPPERCASE_CHARACTERS = config.getProperty(PROPERTY_MAXIMUM_PASSWORD_UPPERCASE_CHARACTERS_ERROR, MINIMUM_ERROR_PASSWORD_UPPERCASE_CHARACTERS);
			MINIMUM_ERROR_PASSWORD_NUMBERS = config.getProperty(PROPERTY_MINIMUM_PASSWORD_NUMBERS_ERROR, MINIMUM_ERROR_PASSWORD_NUMBERS);
			MINIMUM_ERROR_PASSWORD_SPECIAL_CHARACTERS = config.getProperty(PROPERTY_MINIMUM_PASSWORD_SPECIAL_CHARACTERS_ERROR, MINIMUM_ERROR_PASSWORD_SPECIAL_CHARACTERS);
			
			fis.close();
		} catch (FileNotFoundException e) {
			log.warn("** No "+Config.CONFIG_FILE+" file found, set default config **");
		} catch (IOException e) {
			log.warn("** IOError reading "+Config.CONFIG_FILE+", set default config **");
		} finally {
			
		}
	}
}