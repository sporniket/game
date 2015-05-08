/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import com.sporniket.libre.game.papi.TimeAbstractionLayerInterface;

/**
 * Implementation of {@link TimeAbstractionLayerInterface} using system timer.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Swing</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Swing</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 *
 */
public class SystemTimer implements TimeAbstractionLayerInterface
{

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.sgpapi.TimeAbstractionLayerInterface#getClock()
	 */
	
	public long getClock()
	{
		return System.currentTimeMillis();
	}

}
