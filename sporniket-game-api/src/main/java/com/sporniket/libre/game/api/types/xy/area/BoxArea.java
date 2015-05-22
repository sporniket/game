/**
 * 
 */
package com.sporniket.libre.game.api.types.xy.area;

import com.sporniket.libre.game.api.types.xy.geometry.Bounds;
import com.sporniket.libre.game.api.types.xy.geometry.Box;
import com.sporniket.libre.game.api.types.xy.geometry.Point;

/**
 * @author dsporn
 *
 */
public class BoxArea extends Box implements Area
{
	private Bounds myBounds = new Bounds();

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.types.xy.area.Area#isInside(com.sporniket.libre.game.api.types.xy.geometry.Point)
	 */
	@Override
	public boolean isInside(Point position)
	{
		boolean _isOk__x = (position.getX() >= getBounds().getLeft()) && (position.getX() <= getBounds().getRight());
		boolean _isOk__y = (position.getY() >= getBounds().getTop()) && (position.getY() <= getBounds().getBottom());
		return _isOk__x && _isOk__y;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.types.xy.geometry.Box#setX(java.lang.Integer)
	 */
	@Override
	public void setX(Integer x)
	{
		super.setX(x);
		getBounds().withLeft(getX()).withRight(getX()+getWidth()-1);
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.types.xy.geometry.Box#setY(java.lang.Integer)
	 */
	@Override
	public void setY(Integer y)
	{
		super.setY(y);
		getBounds().withTop(getY()).withBottom(getY()+getHeight()-1);
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.types.xy.geometry.Box#setWidth(java.lang.Integer)
	 */
	@Override
	public void setWidth(Integer width)
	{
		super.setWidth(width);
		getBounds().setRight(getX()+getWidth()-1);
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.api.types.xy.geometry.Box#setHeight(java.lang.Integer)
	 */
	@Override
	public void setHeight(Integer height)
	{
		super.setHeight(height);
		getBounds().setBottom(getY()+getHeight()-1);
	}

	/**
	 * @return the bounds
	 */
	protected Bounds getBounds()
	{
		return myBounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	protected void setBounds(Bounds bounds)
	{
		myBounds = bounds;
	}

}
