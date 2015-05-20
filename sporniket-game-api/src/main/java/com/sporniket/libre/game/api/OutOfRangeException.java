/**
 * 
 */
package com.sporniket.libre.game.api;

/**
 * Out of range exception
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
public class OutOfRangeException extends RuntimeException
{

	private static final long serialVersionUID = 6986197707338369860L;

	public OutOfRangeException()
	{
	}

	public OutOfRangeException(String message)
	{
		super(message);
	}

	public OutOfRangeException(Throwable cause)
	{
		super(cause);
	}

	public OutOfRangeException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
