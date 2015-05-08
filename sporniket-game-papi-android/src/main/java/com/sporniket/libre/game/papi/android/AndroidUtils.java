/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import android.util.Log;
import android.view.View.MeasureSpec;

/**
 * Utility class for android.
 * 
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
// FIXME move to a dedicated library when there will be substantial code here.
public class AndroidUtils
{

	/**
	 * Log as info the specified {@link MeasureSpec} in a readable manner.
	 * 
	 * @param tag
	 *            the tag for android logging system.
	 * @param message
	 *            the starting message of the log.
	 * @param measureSpec
	 *            the measure spec to log.
	 */
	public static void dumpMeasureSpec(String tag, String message, int measureSpec)
	{
		StringBuilder _measureSpec = new StringBuilder(message);
		_measureSpec.append(" : ");
		switch (MeasureSpec.getMode(measureSpec))
		{
			case MeasureSpec.AT_MOST:
				_measureSpec.append("at most ");
				break;
			case MeasureSpec.EXACTLY:
				_measureSpec.append("Exactly ");
				break;
			case MeasureSpec.UNSPECIFIED:
				_measureSpec.append("Unspecified ");
				break;
		}
		_measureSpec.append(MeasureSpec.getSize(measureSpec));
		Log.i(tag, _measureSpec.toString());
	}

}
