/**
 * 
 */
package com.sporniket.libre.game.pal.dao;

import java.util.Map;

/**
 * "DAO for preferences" part of the DAO.
 * @author dsporn
 *
 */
public interface PreferencesAccessObject
{
	/**
	 * @return the full list of saved preferrences
	 */
	Map<String, String> loadPreferences();
	
	/**
	 * @param preferences the preferences to save.
	 */
	void savePreferences(Map<String, String> preferences) ;
}
