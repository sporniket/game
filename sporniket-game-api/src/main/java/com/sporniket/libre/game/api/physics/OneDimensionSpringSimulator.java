/**
 * 
 */
package com.sporniket.libre.game.api.physics;

/**
 * Model for simulating springs movement.
 * 
 * The equilibrium may be changed to simulate several identical springs at the same time.
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
public abstract class OneDimensionSpringSimulator implements AccelerationField, Cloneable
{

	/**
	 * Point of equilibrium of the spring (no force), 0 by default.
	 */
	private int myEquilibrium = 0;

	/**
	 * @return the equilibrium
	 */
	public int getEquilibrium()
	{
		return myEquilibrium;
	}

	/**
	 * @param equilibrium the equilibrium to set
	 */
	public void setEquilibrium(int equilibrium)
	{
		myEquilibrium = equilibrium;
	}

	/**
	 * Cache of the horizontal acceleration
	 */
	private int[] myField;

	/**
	 * Maximal distance from the equilibrium.
	 */
	private int myRange;
	
	/**
	 * Precomputation of 2*myRange
	 */
	private int myDoubleRange ;

	/**
	 * @return the doubleRange
	 */
	private int getDoubleRange()
	{
		return myDoubleRange;
	}

	/**
	 * Initialize the simulator
	 * @param equilibrium
	 * @param range
	 */
	public OneDimensionSpringSimulator(int range, double springFactor)
	{
		myRange = range;
		myDoubleRange = 2*range ;
		initField(springFactor);
	}
	
	/**
	 * Constructor that retrieve the Acceleration field of the given reference.
	 * @param reference
	 */
	public OneDimensionSpringSimulator(OneDimensionSpringSimulator reference)
	{
		myRange = reference.myRange ;
		myDoubleRange = reference.myDoubleRange ;
		myField = reference.myField ;
	}

	/**
	 * @return the range
	 */
	private int getRange()
	{
		return myRange;
	}

	/**
	 * Return the value corresponding to the position.
	 * @param position
	 * @return
	 */
	protected int getValue(int position)
	{
		int _localPosition = position - getEquilibrium() + getDoubleRange() ;
		if (_localPosition < 0 || _localPosition >= myField.length)
		{
			return 0 ;
		}
		return myField[_localPosition];
	}

	/**
	 * @param springFactor
	 */
	private void initField(double springFactor)
	{
		myField = new int[4*getRange()];
		for (int _i = 0; _i < myField.length; _i++)
		{
			myField[_i]=(int) ((getDoubleRange() - _i)*springFactor) ;
		}
	}

}
