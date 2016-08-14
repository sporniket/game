/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import com.sporniket.libre.game.canvas.Point;

/**
 * Provide default implementation for some methods of {@link CollisionDetector}.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * api</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 *
 */
class CollisionDetectorDefaultMethodImplementation
{
	/**
	 * cache of the zero Vector.
	 */
	private static final Point ZERO = new Point();
	
	/**
	 * Default implementation for {@link CollisionDetector#isHit(Point)} : the hitter is the (0,0) location.
	 * @param detector the detector to use.
	 * @param hitter the coordinates to test.
	 * @return true if the detector is hit by the hitter.
	 */
	public static boolean isHitWithObjectToHitOnZero(CollisionDetector detector, Point hitter)
	{
		return detector.isHit(ZERO, hitter);
	}
}
