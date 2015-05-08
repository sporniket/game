/**
 * 
 */
package com.sporniket.libre.game.papi.types;

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
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
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
public class Pointer implements Cloneable
{
	public static enum State {
		/**
		 * As long as it remains pressed.
		 */
		PRESSED, 
		/**
		 * As long as it remains released.
		 */
		RELEASED, 
		/**
		 * If no state (and hence no position) is available, e.g. touch screen. 
		 */
		UNDEFINED;
	}
	
	private static final State DEFAULT__STATE = State.UNDEFINED;
	/**
	 * x position.
	 */
	private int myX;
	/**
	 * y position.
	 */
	private int myY;
	/**
	 * Device State.
	 * 
	 * If state is {@link State#UNDEFINED}, the stored position is not valid.
	 */
	private State myState = DEFAULT__STATE;
	/**
	 * @return the x
	 */
	public int getX()
	{
		return myX;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		myX = x;
	}
	/**
	 * @return the y
	 */
	public int getY()
	{
		return myY;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		myY = y;
	}
	/**
	 * @return the state
	 */
	public State getState()
	{
		return myState;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(State state)
	{
		myState = state;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	
	
}
