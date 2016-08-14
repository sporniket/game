/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import com.sporniket.libre.game.api.types.BoxArea;
import com.sporniket.libre.game.api.types.xy.area.BoundsArea;
import com.sporniket.libre.game.canvas.Point;

/**
 * Collision detector for a rectangular area from (left,top) to (right,bottom), inclusive.
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
public class CollisionDetectorOnArea extends BoundsArea implements CollisionDetector
{

	/**
	 * Instanciate a fully specified detector.
	 * 
	 * @param left
	 *            left limit, inclusive.
	 * @param top
	 *            top limit, inclusive.
	 * @param right
	 *            right limit, inclusive.
	 * @param bottom
	 *            bottom limit, inclusive.
	 */
	public CollisionDetectorOnArea(int left, int top, int right, int bottom)
	{
		super();
		setLeft(left);
		setTop(top);
		setRight(right);
		setBottom(bottom);
	}

	/**
	 * Constructor using the specified {@link BoxArea}
	 * 
	 * @param area
	 *            the area definition.
	 */
	public CollisionDetectorOnArea(BoundsArea area)
	{
		this(area.getLeft(), area.getTop(), area.getRight(), area.getBottom());
	}

	/**
	 * Test for a collision between a hitter and an object to hit.
	 * 
	 * The detection will be shifted by <code>toHit</code> value. (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.collision.CollisionDetector#isHit(Point, Point)
	 */

	public boolean isHit(Point toHit, Point hitter)
	{
		int _x = hitter.getX() - toHit.getX();
		int _y = hitter.getY() - toHit.getY();
		
		return isInside(new Point().withX(_x).withY(_y));
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
