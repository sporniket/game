/**
 * 
 */
package test.sporniket.libre.game.api.collision;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.sporniket.libre.game.api.collision.CollisionDetectorOnRectangle;
import com.sporniket.libre.game.api.types.Position.Vector;

/**
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
public class TestCollisionDetectorOnRectangle
{

	/**
	 * Test that a collision is detected on the center of the rectangle.
	 */
	@Test
	public void testCollisionOnCenter()
	{
		CollisionDetectorOnRectangle _detector = new CollisionDetectorOnRectangle(12, 12);
		if (!_detector.isHit(new Vector(120,140), new Vector(120,140)))
		{
			fail("Could not detect collision on center of the area");
		}
	}

}
