/**
 * 
 */
package com.sporniket.libre.game.papi.types;

/**
 * Models of a game controller.
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
public class Joystick {
	
	/**
	 * Direction reported by an axis.
	 * @author David SPORN
	 *
	 */
	public enum Direction {
		NEGATIVE(-1), NEUTRAL(0), POSITIVE(1);
		private int myValue = 0;

		/**
		 * @param value
		 */
		private Direction(int value) {
			setValue(value);
		}

		/**
		 * @return the value
		 */
		private int getValue() {
			return myValue;
		}

		/**
		 * @param value the value to set
		 */
		private void setValue(int value) {
			myValue = value;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return Integer.toString(getValue());
		}
		
		/**
		 * Evaluate a direction according to a value.
		 * @param value
		 * @return
		 */
		public static Direction evaluateDirection(int value)
		{
			if (value > 0)
			{
				return POSITIVE ;
			}
			else if (value < 0)
			{
				return NEGATIVE ;
			}
			else
			{
				return NEUTRAL ;
			}
		}
		
	}
	
	/**
	 * Maximal number of managed buttons.
	 */
	public static final Integer MAX__BUTTON = 32;
	
	/**
	 * Maximal number of managed axises.
	 */
	public static final Integer MAX__AXIS = 8;
	
	/**
	 * Button press values.
	 * 
	 * values may be variable if the button are pressure sensitives.
	 */
	private Integer[] myButtonValues = new Integer[MAX__BUTTON];
	
	/**
	 * Button "is pressed" predicate.
	 */
	private Boolean[] myButtonsIsPressed = new Boolean[MAX__BUTTON];
	
	/**
	 * Axis values.
	 * 
	 * Values may be variables for gesture sensitivity.
	 */
	private Integer[] myAxisValues = new Integer[MAX__AXIS];
	
	/**
	 * Axis direction
	 */
	private Direction[] myAxisDirections = new Direction[MAX__AXIS];

	/**
	 * 
	 */
	private Joystick() {
		//TODO init arrays
		initValues();
	}

	private void initValues() {
		for (int _i = 0; _i < MAX__AXIS; _i++) {
			getAxisValues()[_i] = 0 ;
			getAxisDirections()[_i] = Direction.NEUTRAL ;
		}
		for (int _i = 0; _i < MAX__BUTTON; _i++) {
			getButtonValues()[_i] = 0 ;
			getButtonsIsPressed()[_i] = false ;
		}
	}

	/**
	 * @return the buttonValues
	 */
	public Integer[] getButtonValues() {
		return myButtonValues;
	}

	/**
	 * @return the buttonsIsPressed
	 */
	public Boolean[] getButtonsIsPressed() {
		return myButtonsIsPressed;
	}

	/**
	 * @return the axisValues
	 */
	public Integer[] getAxisValues() {
		return myAxisValues;
	}

	/**
	 * @return the axisDirections
	 */
	public Direction[] getAxisDirections() {
		return myAxisDirections;
	}
	
	
}
