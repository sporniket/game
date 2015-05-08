/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

/**
 * Description of a resource.
 * 
 * <p>
 * A resource is usually a file, and available {@link ResourceNameProvider} will deals with files.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class ResourceDescriptor
{
	/**
	 * Usually just the filename without the extension (see {@link #myType}), but the resource might be placed in a subdirectory.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final String myPath;

	/**
	 * The resource type, it will usually be the file extension.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final String myType;

	/**
	 * @param path
	 * @param type
	 * @since 0-SNAPSHOT
	 */
	public ResourceDescriptor(String path, String type)
	{
		myPath = path;
		myType = type;
	}

	/**
	 * Get path.
	 * 
	 * @return the path
	 * @since 0-SNAPSHOT
	 */
	public String getPath()
	{
		return myPath;
	}

	/**
	 * Get type.
	 * 
	 * @return the type
	 * @since 0-SNAPSHOT
	 */
	public String getType()
	{
		return myType;
	}
}
