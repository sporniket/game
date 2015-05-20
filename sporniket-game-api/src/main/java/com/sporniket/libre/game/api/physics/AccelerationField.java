/**
 * 
 */
package com.sporniket.libre.game.api.physics;

import com.sporniket.libre.game.api.types.Position.Vector;

/**
 * Interface of an "acceleration field", that can provide, for a position, an associated acceleration.
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
public interface AccelerationField
{
	/**Compute the acceleration vector associated with the position
	 * @param position the position of the object to accelerate.
	 * @return MAY be <code>null</code> if the position is out of the field but SHOULD be vector (0,0) instead.
	 */
	Vector getAcceleration(Vector position);
}
