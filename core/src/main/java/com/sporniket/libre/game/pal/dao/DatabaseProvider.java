/**
 * 
 */
package com.sporniket.libre.game.pal.dao;

import java.sql.Connection;

/**
 * Database part of the DAO.
 * @author dsporn
 *
 */
public interface DatabaseProvider
{
	/**
	 * @return a connection to a default database.
	 */
	Connection getDatabase() ;
	
	/**
	 * @param id an identifier, connection url, etc...
	 * @return a connection to a specific database.
	 */
	Connection getDatabase(String id);
}
