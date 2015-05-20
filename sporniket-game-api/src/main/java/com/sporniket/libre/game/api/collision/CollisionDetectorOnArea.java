/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import com.sporniket.libre.game.api.types.BoxArea;
import com.sporniket.libre.game.api.types.Position.Vector;

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
public class CollisionDetectorOnArea implements CollisionDetector
{

	/**
	 * Bottom limit, inclusive.
	 */
	private int myBottom;

	/**
	 * Left limit, inclusive.
	 */
	private int myLeft;

	/**
	 * Right limit, inclusive.
	 */
	private int myRight;

	/**
	 * Top limit, inclusive.
	 */
	private int myTop;

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
		myLeft = left;
		myTop = top;
		myRight = right;
		myBottom = bottom;
	}

	/**
	 * Constructor using the specified {@link BoxArea}
	 * 
	 * @param area
	 *            the area definition.
	 */
	public CollisionDetectorOnArea(BoxArea area)
	{
		this(area.getLeft(), area.getTop(), area.getRight(), area.getBottom());
	}

	/**
	 * Test for a collision between a hitter and an object to hit.
	 * 
	 * The detection will be shifted by <code>toHit</code> value. (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.collision.CollisionDetector#isHit(com.sporniket.libre.game.api.types.Position.Vector,
	 *      com.sporniket.libre.game.api.types.Position.Vector)
	 */

	public boolean isHit(Vector toHit, Vector hitter)
	{
		int _x = hitter.x - toHit.x;
		int _y = hitter.y - toHit.y;

		boolean _isOk__x = (myLeft <= _x) && (_x <= myRight);
		boolean _isOk__y = (myTop <= _y) && (_y <= myBottom);
		return _isOk__x && _isOk__y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.collision.CollisionDetector#isHit(com.sporniket.libre.game.api.types.Position.Vector)
	 */

	public boolean isHit(Vector hitter)
	{
		return CollisionDetectorDefaultMethodImplementation.isHitWithObjectToHitOnZero(this, hitter);
	}

}
