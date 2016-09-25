/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.InvalidValueException;


/**
 * Model an item of a sequence (list of item that are processed one after
 * another).
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
 * @deprecated will be replaced by another api
 */
public class SequenceItem implements Cloneable {
	/**
	 * Type of item.
	 * STILL: single frame (no animation from this point).
	 * FRAME: frame of an animation.
	 * LOOP: go back to a previous frame.
	 * @author David SPORN
	 *
	 */
	public static enum Type {
		STILL(0), FRAME(1), LOOP(2);
		/**
		 * Registry of value/Keyword.
		 */
		private static final Map<String, Type> CACHE_VALUES = new HashMap<String, Type>();

		static {
			for (int _i = 0; _i < Type.values().length; _i++) {
				Type _type = Type.values()[_i];
				CACHE_VALUES.put(Integer.toString(_type.getValue()), _type);
			}
		}

		/**
		 * Parse the value to find the corresponding keyword instance.
		 * 
		 * @param value
		 * @return
		 */
		public static Type parse(String value) {
			if (CACHE_VALUES.containsKey(value)) {
				return CACHE_VALUES.get(value);
			}
			throw new IllegalArgumentException("[" + value
					+ "] is not a known type.");
		}
		
		private int myValue = -1 ;

		/**
		 * @return the value
		 */
		public int getValue() {
			return myValue;
		}

		/**
		 * @param value
		 */
		private Type(int value) {
			myValue = value;
		}
		
		

	}
	
	/**
	 * Type of item.
	 */
	private SequenceItem.Type myType = Type.STILL ;
	
	/**
	 * Value.
	 * STILL/FRAME: Integer of the frame
	 * LOOP:Integer of an item
	 */
	private Integer myValue = new Integer(0);
	
	/**
	 * Item duration.
	 */
	private Long myDuration = new Long(0);

	/**
	 * @return the type
	 */
	public SequenceItem.Type getType() {
		return myType;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SequenceItem.Type type) {
		myType = type;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return myValue;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		if (value < 0)
		{
			throw new InvalidValueException("Value MUST be greater than or equal to 0.");
		}
		myValue = value;
	}

	/**
	 * @return the duration
	 */
	public Long getDuration() {
		return myDuration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Long duration) {
		myDuration = duration;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	
	public Object clone() throws CloneNotSupportedException {
		SequenceItem _clone = new SequenceItem();
		_clone.setType(getType());
		_clone.setValue(new Integer(getValue()));
		_clone.setDuration(new Long(getDuration()));
		return _clone;
	}
	
	public static void clearItem(SequenceItem item){
		item.setType(Type.STILL);
		item.setValue(new Integer(0));
		item.setDuration(new Long(0));
	}
	
	
}
