/**
 * 
 */
package com.sporniket.libre.game.api.types;

import com.sporniket.libre.game.api.types.Position.Vector;

/**
 * Rectangular Area, defined by its top, bottom right and left borders, borders are included.
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
public class BoxArea implements Area
{

	/**
	 * Factory for creating a fully specified BoxArea.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public static BoxArea create(int left, int top, int right, int bottom)
	{
		BoxArea _result = new BoxArea();
		_result.setLeft(left);
		_result.setTop(top);
		_result.setRight(right);
		_result.setBottom(bottom);
		return _result;
	}

	private int myBottom = 0;

	private int myLeft = 0;

	private int myRight = 0;

	private int myTop = 0;

	/**
	 * @return the bottom
	 */
	public int getBottom()
	{
		return myBottom;
	}

	/**
	 * @return the left
	 */
	public int getLeft()
	{
		return myLeft;
	}

	/**
	 * @return the right
	 */
	public int getRight()
	{
		return myRight;
	}

	/**
	 * @return the top
	 */
	public int getTop()
	{
		return myTop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.types.Area#isInside(com.sporniket.libre.game.api.types.Position.Vector)
	 */
	
	public boolean isInside(Vector position)
	{
		boolean _isOk__x = (position.x >= getLeft()) && (position.x <= getRight());
		boolean _isOk__y = (position.y >= getTop()) && (position.y <= getBottom());
		return _isOk__x && _isOk__y;
	}

	/**
	 * @param bottom
	 *            the bottom to set
	 */
	public void setBottom(int bottom)
	{
		myBottom = bottom;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(int left)
	{
		myLeft = left;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(int right)
	{
		myRight = right;
	}

	/**
	 * @param top
	 *            the top to set
	 */
	public void setTop(int top)
	{
		myTop = top;
	}

}
