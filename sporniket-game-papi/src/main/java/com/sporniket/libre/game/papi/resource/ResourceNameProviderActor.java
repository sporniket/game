/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import java.io.File;
import java.text.MessageFormat;

import com.sporniket.libre.game.papi.profile.ScreenDefinition;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Actor definitions will be defined by {@link ScreenDefinition}, x and y factor of the {@link ScreenFeatureSet}.
 * 
 * <p>
 * This is not perfect, because the independant unit system means that, for instance, a qHD and FHD screen featureset could have
 * shared the same actor file if the FHD {@link ScreenFeatureSet} have an x and y factor of 2.
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
public class ResourceNameProviderActor implements ResourceNameProvider
{
	/**
	 * Default instance.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final ResourceNameProviderActor INSTANCE = new ResourceNameProviderActor();

	/**
	 * Get instance.
	 * 
	 * @return the instance
	 * @since 0-SNAPSHOT
	 */
	public static ResourceNameProviderActor getInstance()
	{
		return INSTANCE;
	}

	private MessageFormat myFormat = new MessageFormat("data" + File.separator + "{0}--{1}-{2}-{3}.{4}");

	/**
	 * 
	 * @since 0-SNAPSHOT
	 */
	public ResourceNameProviderActor()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.resource.ResourceNameProvider#getName(com.sporniket.libre.game.papi.resource.Resource,
	 * com.sporniket.libre.game.papi.resource.ResourceNameContext)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public String getName(ResourceDescriptor resource, ResourceNameContext context)
	{
		ScreenFeatureSet _screenFeatureSet = context.getScreenFeatureSet();
		ScreenDefinition _graphicsDefinition = _screenFeatureSet.getGraphicsDefinition();
		Object[] _params =
		{
				resource.getPath(),
				_graphicsDefinition.toString(),
				_screenFeatureSet.getXfactor(),
				_screenFeatureSet.getYfactor(),
				resource.getType()
		};
		return myFormat.format(_params);
	}
}
