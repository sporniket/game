/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

/**
 * Interface for computing a ressource name (usually a file name).
 * 
 * <p>
 * The actual resource name depends of a context (e.g.: locale, screen definition, etc...)
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
public interface ResourceNameProvider
{
	/**
	 * Get the resource name that fits the context.
	 * 
	 * @param resource
	 *            the resource to get the name of.
	 * @param context
	 *            the context
	 * @return the actual resource name.
	 * @since 0-SNAPSHOT
	 */
	String getName(ResourceDescriptor resource, ResourceNameContext context);
}
