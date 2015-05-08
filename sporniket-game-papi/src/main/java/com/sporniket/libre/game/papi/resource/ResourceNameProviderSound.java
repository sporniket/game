package com.sporniket.libre.game.papi.resource;

import java.io.File;
import java.text.MessageFormat;

/**
 * Model for computing a sound resource name.
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
public class ResourceNameProviderSound extends ResourceNameLocalized
{
	/**
	 * Default instance.
	 * @since 0-SNAPSHOT
	 */
	private static final ResourceNameProviderSound INSTANCE = new ResourceNameProviderSound();
	/**
	 * Get instance.
	 * @return the instance
	 * @since 0-SNAPSHOT
	 */
	public static ResourceNameProviderSound getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Message format to build the localized resource name.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final MessageFormat myFormatLocalizedResource = new MessageFormat("sfx" + File.separator + "{0}-{1}.{2}");

	/**
	 * Message format to build the not localized resource name
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final MessageFormat myFormatNotLocalizedResource = new MessageFormat("sfx" + File.separator + "{0}.{2}");

	/**
	 * 
	 * @since 0-SNAPSHOT
	 */
	public ResourceNameProviderSound()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get formatLocalizedResource.
	 * 
	 * @return the formatLocalizedResource
	 * @since 0-SNAPSHOT
	 */
	public MessageFormat getFormatLocalizedResource()
	{
		return myFormatLocalizedResource;
	}

	/**
	 * Get formatNotLocalizedResource.
	 * 
	 * @return the formatNotLocalizedResource
	 * @since 0-SNAPSHOT
	 */
	public MessageFormat getFormatNotLocalizedResource()
	{
		return myFormatNotLocalizedResource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.resource.ResourceNameLocalized#getName__localized(com.sporniket.libre.game.papi.resource.
	 * ResourceLocalizable, com.sporniket.libre.game.papi.resource.ResourceNameContext, java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	@Override
	protected String getName__localized(ResourceLocalizable resource, ResourceNameContext context, String matchingLocale)
	{
		Object[] _params =
		{
				resource.getPath(), matchingLocale, resource.getType()
		};
		return getFormatLocalizedResource().format(_params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sporniket.libre.game.papi.resource.ResourceNameLocalized#getName__notLocalized(com.sporniket.libre.game.papi.resource
	 * .Resource, com.sporniket.libre.game.papi.resource.ResourceNameContext)
	 * 
	 * @since 0-SNAPSHOT
	 */
	@Override
	protected String getName__notLocalized(ResourceDescriptor resource, ResourceNameContext context)
	{
		Object[] _params =
		{
				resource.getPath(), null, resource.getType()
		};
		return getFormatNotLocalizedResource().format(_params);
	}

}
