/**
 * 
 */
package com.sporniket.libre.game.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Simple locator, that try to load a file from a folder.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * api</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 *
 */
public class SimpleFolderResourceDefinitionLocator implements ResourceDefinitionLocator
{
	private File myFolder;

	/**
	 * @return the folder
	 */
	private File getFolder()
	{
		return myFolder;
	}

	/**
	 * @param folder the folder to set
	 */
	private void setFolder(File folder)
	{
		myFolder = folder;
	}

	/**
	 * @param folder
	 */
	public SimpleFolderResourceDefinitionLocator(File folder)
	{
		super();
		setFolder(folder);
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.ResourceDefinitionLocator#getInputStream(java.lang.String)
	 */
	
	public InputStream getInputStream(String location) throws IOException
	{
		return new FileInputStream(new File(getFolder(), location));
	}

}
