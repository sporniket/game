/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Model for a pointer, providing basic informations..
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN
 *
 */
public class Pointer
{
	public static enum State
	{
		CLIC,
		NOT_PRESSED,
		PRESSED,
		/**
		 * If no state (and hence no position) is available, e.g. touch screen.
		 */
		UNDEFINED,
		WHEEL;
	}

	/**
	 * When {@link #getState()} is {@link State#CLIC}, the number of clic (simple, double, etc...) ; When {@link #getState()} is
	 * {@link State#WHEEL}, the scroll value (scroll amount * wheel rotation).
	 */
	private final int myCount;

	/**
	 * 0:left button ; 1:middle button ; 2:right button
	 */
	private final int myIndex;

	/**
	 * Device State.
	 * 
	 * If state is {@link State#UNDEFINED}, the stored position is not valid.
	 */
	private final State myState;

	/**
	 * x position.
	 */
	private final int myX;

	/**
	 * y position.
	 */
	private final int myY;

	public Pointer(State state, int x, int y, int index, int count)
	{
		myState = state;
		myX = x;
		myY = y;
		myIndex = index;
		myCount = count;
	}

	public int getCount()
	{
		return myCount;
	}

	public int getIndex()
	{
		return myIndex;
	}

	public State getState()
	{
		return myState;
	}

	public int getX()
	{
		return myX;
	}

	public int getY()
	{
		return myY;
	}

}
