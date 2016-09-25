/**
 * 
 */
package com.sporniket.libre.game.api.types;

import com.sporniket.libre.game.InvalidValueException;


/**
 * Integer integer that can be changed adding or substracting <i>ticks</i>.
 * 
 * The number of tick needed can be changed as needed.
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
public class ProgressiveValue extends Number implements Comparable<Integer> {
	private static final Integer DEFAULT_TICK = 0;

	private static final Integer DEFAULT_VALUE = 0;

	private static final Integer DEFAULT_TICK_TRESHOLD = 1000;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7723302060360288739L;

	private Integer myTick = DEFAULT_TICK;
	private Integer myTickTreshold = DEFAULT_TICK_TRESHOLD;
	private Integer myValue = DEFAULT_VALUE ;

	/**
	 * @return the value
	 */
	private Integer getValue() {
		return myValue;
	}

	/**
	 * @param value the value to set
	 */
	private void setValue(Integer value) {
		myValue = value;
	}

	/**
	 * 
	 */
	public ProgressiveValue() {
		this(0, DEFAULT_TICK_TRESHOLD, DEFAULT_TICK);
	}

	/**
	 * @param value
	 */
	public ProgressiveValue(Integer value) {
		this(new Integer(value), DEFAULT_TICK_TRESHOLD, DEFAULT_TICK);
	}

	/**
	 * @param value
	 */
	public ProgressiveValue(Integer value, Integer tickTreshold) {
		this(new Integer(value), new Integer(tickTreshold), DEFAULT_TICK);
	}

	/**
	 * @param tick
	 * @param tickTreshold
	 */
	public ProgressiveValue(Integer value, Integer tickTreshold, Integer tick) {
		setValue(value);
		setTickTreshold(tickTreshold);
		setTick(tick);
	}

	/**
	 * @return the tick
	 */
	private Integer getTick() {
		return myTick;
	}
	
	/**
	 * @return the tickTreshold
	 */
	private Integer getTickTreshold() {
		return myTickTreshold;
	}

	/**
	 * @param tick the tick to set
	 */
	private void setTick(Integer tick) {
		myTick = tick;
	}

	/**
	 * @param tickTreshold the tickTreshold to set
	 */
	private void setTickTreshold(Integer tickTreshold) {
		if (tickTreshold <= 0)
		{
			throw new InvalidValueException("TickTreshold MUST be strictly greater than 0.");
		}
		myTickTreshold = tickTreshold;
	}
	
	public void addTicks(Integer ticks)
	{
		//setTick(getTick()+ticks));
		myTick += ticks;
		if (0 > getTick().intValue())
		{
			while (0 > getTick().intValue())
			{
				--myValue ;
				myTick += getTickTreshold();
			}
		}
		else 
		{
			while (getTick() > getTickTreshold())
			{
				++myValue ;
				myTick -= getTickTreshold();
			}
		}
	}

	public int compareTo(Integer value) {
		return getValue().compareTo(value);
	}

	
	public double doubleValue() {
		return getValue().doubleValue();
	}

	
	public float floatValue() {
		return getValue().floatValue();
	}

	
	public int intValue() {
		return getValue().intValue();
	}

	
	public long longValue() {
		return getValue().longValue();
	}
	
}
