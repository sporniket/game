/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import com.sporniket.libre.game.api.canvas.Point;

/**
 * Interface for a collision detector, implementation will be dealing with one type of actor.
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
public interface CollisionDetector
{
	/**
	 * Test for a collision between a hitter and an object to hit.
	 * @param toHit position of the object to hit.
	 * @param hitter position of the hitter.
	 * @return true if there has been a collision.
	 */
	boolean isHit(Point toHit, Point hitter) ;
	
	/**
	 * Test for a collision not dependent from the position of the object to hit (put at (0,0) if required).
	 * @param hitter position of the hitter.
	 * @return true if there has been a collision.
	 */
	boolean isHit(Point hitter);
}
