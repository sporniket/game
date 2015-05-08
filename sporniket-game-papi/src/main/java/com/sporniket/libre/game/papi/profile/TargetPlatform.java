package com.sporniket.libre.game.papi.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * Target platform designation.
 * 
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
public enum TargetPlatform
{
	/**
	 * ANDROID means "android device" (usually smartphones and tablets) with limited resources and input device (usually only a tactile multi-point screen, no keyboard)
	 */
	ANDROID("android"),
	/**
	 * I386 means "regular computer", with a pointing device (usually a mouse) and a keyboard.
	 */
	I386("i386");

	/**
	 * Registry of value/Keyword.
	 */
	private static final Map<String, TargetPlatform> CACHE_VALUES = new HashMap<String, TargetPlatform>();

	static
	{
		for (int _i = 0; _i < TargetPlatform.values().length; _i++)
		{
			TargetPlatform _enumItem = TargetPlatform.values()[_i];
			CACHE_VALUES.put(_enumItem.toString(), _enumItem);
		}
	}

	/**
	 * Parse the value to find the corresponding keyword instance.
	 * 
	 * @param value
	 * @return
	 */
	public static TargetPlatform parse(String value)
	{
		if (CACHE_VALUES.containsKey(value))
		{
			return CACHE_VALUES.get(value);
		}
		throw new IllegalArgumentException("[" + value + "] is not a TargetPlatform.");
	}

	private String myValue;

	/**
	 * @param value
	 */
	private TargetPlatform(String value)
	{
		myValue = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return myValue;
	}
}