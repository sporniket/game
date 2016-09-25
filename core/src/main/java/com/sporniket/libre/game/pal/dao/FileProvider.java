/**
 * 
 */
package com.sporniket.libre.game.pal.dao;

import java.io.File;

/**
 * File system part of the DAO.
 * @author dsporn
 *
 */
interface FileProvider
{
	/**
	 * @param fileId Most of the time, a relative path.
	 * @return a file descriptor.
	 */
	File getFile(String fileId);
}
