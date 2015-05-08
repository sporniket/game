/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource that can be different according to the {@link ResourceNameContext#getLocale()}.
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
public class ResourceLocalizable extends ResourceDescriptor
{
	/**
	 * Initial capacity of the list of supported locales.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final int INITIAL_CAPACITY__SUPPORTED_LOCALES = 10;

	/**
	 * List of supported locales. If empty, there is no localized version. If the list is not empty, then one MUST scan each
	 * supported locale with the context locale ; if there is a match, then the resource will be localized for this locale.
	 * Otherwise, the resource corresponding to the last locale of the list will be used.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final List<String> mySupportedLocales = new ArrayList<String>(INITIAL_CAPACITY__SUPPORTED_LOCALES);

	/**
	 * Resource that is not localized.
	 * 
	 * @param path
	 * @param type
	 * @since 0-SNAPSHOT
	 */
	public ResourceLocalizable(String path, String type)
	{
		super(path, type);
	}

	/**
	 * Localized resource (if the provided list is not empty).
	 * 
	 * @param path
	 * @param type
	 * @param supportedLocales
	 * @since 0-SNAPSHOT
	 */
	public ResourceLocalizable(String path, String type, String[] supportedLocales)
	{
		this(path, type);
		for (String supportedLocale : supportedLocales)
		{
			getPrivateSupportedLocales().add(supportedLocale);
		}
	}

	/**
	 * Get the list, for internal use.
	 * 
	 * @return the list of supported locales.
	 * @since 0-SNAPSHOT
	 */
	private List<String> getPrivateSupportedLocales()
	{
		return mySupportedLocales;
	}

	/**
	 * Get a copy of the list of supported locales, by descending order of specificity, i.e the last one is the default locale if no
	 * Locale matches the Locale of the context.
	 * 
	 * @return a copy of the list of supported locales.
	 * @since 0-SNAPSHOT
	 */
	public List<String> getSupportedLocales()
	{
		return new ArrayList<String>(mySupportedLocales);
	}

	/**
	 * Predicate.
	 * 
	 * @return whether the object depends on a Locale, or not.
	 * @since 0-SNAPSHOT
	 */
	public boolean isLocalized()
	{
		return !getPrivateSupportedLocales().isEmpty();
	}
}
