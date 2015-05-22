/**
 * 
 */
package com.sporniket.libre.game.api.types.xy.area;

import com.sporniket.libre.game.api.types.xy.geometry.Bounds;
import com.sporniket.libre.game.api.types.xy.geometry.Point;

/**
 * @author dsporn
 *
 */
public class BoundsArea extends Bounds implements Area
{

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.types.xy.area.Area#isInside(com.sporniket.libre.game.api.types.xy.geometry.Point)
	 */
	@Override
	public boolean isInside(Point position)
	{
		boolean _isOk__x = (position.getX() >= getLeft()) && (position.getX() <= getRight());
		boolean _isOk__y = (position.getY() >= getTop()) && (position.getY() <= getBottom());
		return _isOk__x && _isOk__y;
	}

}
