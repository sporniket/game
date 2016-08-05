/**
 * 
 */
package com.sporniket.libre.game.api.collision;

import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.game.api.canvas.Point;

/**
 * A collision detector made of several collision detectors.
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
public class CollisionDetectorHolder implements CollisionDetector
{
	private List<CollisionDetector> myDetectors;

	/**
	 * @param detectors
	 *            the detectors to gather.
	 */
	public CollisionDetectorHolder(List<CollisionDetector> detectors)
	{
		super();
		myDetectors = new ArrayList<CollisionDetector>(detectors.size());
		myDetectors.addAll(detectors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniketstudio.earthdefender.timeattack.CollisionDetector#isHit(com.sporniket.libre.game.api.types.Position.Vector,
	 * com.sporniket.libre.game.api.types.Position.Vector)
	 */

	public boolean isHit(Point toHit, Point hitter)
	{
		for (CollisionDetector _detector : myDetectors)
		{
			if (_detector.isHit(toHit, hitter))
			{
				return true;
			}
		}
		return false;
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
