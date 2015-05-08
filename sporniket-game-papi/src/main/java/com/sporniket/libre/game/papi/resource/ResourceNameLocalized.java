/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import java.util.List;
import java.util.Locale;


/**
 * Base class for a {@link ResourceNameProvider} that can be localized.
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
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * @author David SPORN 
 *
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public abstract class ResourceNameLocalized implements ResourceNameProvider
{

	private String findMatchingLocale(Locale locale, List<String> supportedLocales)
	{
		String _fullLocale = locale.toString() ;
		String _languageOnly = locale.getLanguage();
		String _result = null;
		boolean _found = false ;
		//first try using the full locale description.
		for(String _supportedLocale:supportedLocales)
		{
			_result = _supportedLocale ;
			if(_supportedLocale.equals(_fullLocale))
			{
				_found = true ;
				break;
			}
		}
		//then try using the language only, if the specific locale was not found.
		if(!_found)
		{
			for(String _supportedLocale:supportedLocales)
			{
				_result = _supportedLocale ;
				if(_supportedLocale.startsWith(_languageOnly))
				{
					_found = true ;
					break;
				}
			}
		}
		return _result;
	}
	
	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.ResourceNameProvider#getName(com.sporniket.libre.game.papi.Resource, com.sporniket.libre.game.papi.ResourceNameContext)
	 * @since 0-SNAPSHOT
	 */
	public String getName(ResourceDescriptor resource, ResourceNameContext context)
	{
		String _result ;
		if (resource instanceof ResourceLocalizable)
		{
			_result = getName((ResourceLocalizable)resource, context) ;
		}
		else
		{
			_result = getName__notLocalized(resource, context);
		}
		return _result;
	}

	/**
	 * @param resource
	 * @param context
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private String getName(ResourceLocalizable resource, ResourceNameContext context)
	{
		String _result ;
		if (!resource.isLocalized())
		{
			_result = getName__notLocalized(resource, context);
		}
		else
		{
			String _locale = findMatchingLocale(context.getLocale(), resource.getSupportedLocales()) ;
			_result = getName__localized(resource, context, _locale);
		}
		return _result ;
	}

	protected abstract String getName__localized(ResourceLocalizable resource, ResourceNameContext context, String matchingLocale) ;

	protected abstract String getName__notLocalized(ResourceDescriptor resource, ResourceNameContext context) ;
	
}
