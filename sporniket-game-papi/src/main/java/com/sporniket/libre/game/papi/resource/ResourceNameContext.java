/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import java.util.Locale;

import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Context used by a {@link ResourceNameProvider} to compute a resource name.
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
public class ResourceNameContext
{
	/**
	 * System locale, to choose a localized resource.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final Locale myLocale;

	/**
	 * {@link ScreenFeatureSet}, used to choose the graphic file according to the resolution.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final ScreenFeatureSet myScreenFeatureSet;

	/**
	 * @param screenFeatureSet
	 * @param locale
	 * @since 0-SNAPSHOT
	 */
	public ResourceNameContext(ScreenFeatureSet screenFeatureSet, Locale locale)
	{
		myScreenFeatureSet = screenFeatureSet;
		myLocale = locale;
	}

	/**
	 * Get locale.
	 * 
	 * @return the locale
	 * @since 0-SNAPSHOT
	 */
	public Locale getLocale()
	{
		return myLocale;
	}

	/**
	 * Get screenFeatureSet.
	 * 
	 * @return the screenFeatureSet
	 * @since 0-SNAPSHOT
	 */
	public ScreenFeatureSet getScreenFeatureSet()
	{
		return myScreenFeatureSet;
	}
}
