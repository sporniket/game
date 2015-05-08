/**
 * 
 */
package com.sporniket.libre.game.papi.log;


/**
 * Factory to get a logger.
 * 
 * The factory to use will be setted by the platform implementation.
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
 * 
 * @author David SPORN 
 *
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public abstract class LogFactory
{
	/**
	 * Logger that dispose the message, as long as a factory is not provided.
	 * @since 0-SNAPSHOT
	 */
	private static final Logger DEFAULT_LOGGER = new MessageSinkLogger() ;
	private static LogFactory theFactory = null ;
	
	public static void setFactory(LogFactory factory)
	{
		if (null == factory)
		{
			throw new NullPointerException("factory MUST be not null") ;
		}
		theFactory = factory ;
	}
	
	public static Logger getLogger(String name)
	{
		if (null != theFactory)
		{
			return theFactory.getLoggerInstance(name);
		}
		else
		{
			return DEFAULT_LOGGER ;
		}
	}
	
	public abstract Logger getLoggerInstance(String name);

}
