/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import com.sporniket.libre.game.canvas.Point;

/**
 * Base class for detectors watching a border (horizontal or vertical)
 * 
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
// FIXME create a detector on limits (on the left, on the right)
public abstract class CollisionDetectorOnBorder implements CollisionDetector
{
	/**
	 * Border value.
	 */
	private int myBorder;

	/**
	 * @return the border
	 */
	protected int getBorder()
	{
		return myBorder;
	}

	/**
	 * @param border
	 *            the border value.
	 */
	public CollisionDetectorOnBorder(int border)
	{
		super();
		myBorder = border;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.collision.CollisionDetector#isHit(com.sporniket.libre.game.api.types.Position.Vector)
	 */

	public boolean isHit(Point hitter)
	{
		return CollisionDetectorDefaultMethodImplementation.isHitWithObjectToHitOnZero(this, hitter);
	}

}
