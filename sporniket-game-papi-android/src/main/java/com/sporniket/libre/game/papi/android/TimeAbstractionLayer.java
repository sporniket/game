/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import android.os.SystemClock;

import com.sporniket.libre.game.papi.TimeAbstractionLayerInterface;

/**
 * Use android {@link android.os.SystemClock} to get the system time.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Android</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Android</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 *
 */
public class TimeAbstractionLayer implements TimeAbstractionLayerInterface
{

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.TimeAbstractionLayerInterface#getClock()
	 */
	public long getClock()
	{
		return SystemClock.elapsedRealtime();
	}

}
