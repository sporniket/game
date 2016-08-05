/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import com.sporniket.libre.game.api.canvas.Point;

/**
 * Collision detector between two objects, the active zone is a rectangle defined by two radius, the rectangle might be offset.
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
public class CollisionDetectorOnRectangle implements CollisionDetector
{
	int myRx;

	int myRy;

	int myOffsetX;

	int myOffsetY;

	/**
	 * <code>myMinusRx = -myRx</code>, optimisation for the test.
	 */
	int myMinusRx;

	/**
	 * <code>myMinusRy = -myRy</code>, optimisation for the test.
	 */
	int myMinusRy;

	/**
	 * @param rx
	 *            horizontal radius.
	 * @param ry
	 *            vertical radius.
	 */
	public CollisionDetectorOnRectangle(int rx, int ry)
	{
		myRx = rx;
		myRy = ry;
		myMinusRx = -myRx;
		myMinusRy = -myRy;
	}

	/**
	 * @param rx
	 *            horizontal radius.
	 * @param ry
	 *            vertical radius.
	 * @param offsetX
	 *            x of the rectangle center.
	 * @param offsetY
	 *            y of the rectangle center.
	 */
	public CollisionDetectorOnRectangle(int rx, int ry, int offsetX, int offsetY)
	{
		this(rx, ry);
		myOffsetX = offsetX;
		myOffsetY = offsetY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniketstudio.earthdefender.timeattack.CollisionDetector#isHit(com.sporniket.libre.game.api.types.Position.Vector,
	 * com.sporniket.libre.game.api.types.Position.Vector)
	 */

	public boolean isHit(Point toHit, Point hitter)
	{
		int _deltaX = toHit.getX() - hitter.getX() - myOffsetX;
		int _deltaY = toHit.getY() - hitter.getY() - myOffsetY;

		return ((_deltaX <= myRx) && (_deltaX >= myMinusRx) && (_deltaY <= myRy) && (_deltaY >= myMinusRy));
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
