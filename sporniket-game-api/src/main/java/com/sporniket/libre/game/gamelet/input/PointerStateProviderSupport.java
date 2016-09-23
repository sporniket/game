/**
 * 
 */
package com.sporniket.libre.game.gamelet.input;


/**
 * Support class for helping managing pointers support.
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
 * @deprecated
 */
public class PointerStateProviderSupport implements PointerStateProvider
{

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface.PointerStateProvider#getPointerCount()
	 */
	public int getPointerCount()
	{
		return mySupportedPointerCount;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface.PointerStateProvider#getStates()
	 */
	public Pointer[] getStates()
	{
		Pointer[] _result = myNextStates ;
		initNextStates();
		return _result;
	}

	/**
	 * The number of simultaneous pointer states.
	 */
	private int mySupportedPointerCount ;
	
	private Pointer[] myNextStates ;

	/**
	 * @param supportedPointerCount
	 */
	public PointerStateProviderSupport(int supportedPointerCount)
	{
		super();
		mySupportedPointerCount = supportedPointerCount;
		initNextStates();
	}

	/**
	 * Prepare {@link #myNextStates} so that it is ready for {@link #recordPressedPointer(int, int, int)} and {@link #recordReleasedPointer(int, int, int)}. 
	 */
	private void initNextStates()
	{
		myNextStates = createPointerStates() ;
	}

	/**
	 * Create an array of undefined pointers.
	 * @return
	 */
	private Pointer[] createPointerStates()
	{
		Pointer[] _result = new Pointer[mySupportedPointerCount];
		for(int _i = 0 ; _i < mySupportedPointerCount ; _i++)
		{
			_result[_i]=new Pointer();
		}
		return _result ;
	}
	
	
	/**
	 * The specified pointer is pressed.
	 * 
	 * @param pointerId the pointer to record.
	 * @param x
	 * @param y
	 */
	public void recordPressedPointer(int pointerId, int x, int y)
	{
		Pointer _pointer = myNextStates[pointerId];
		_pointer.setState(Pointer.State.PRESSED);
		_pointer.setX(x);
		_pointer.setY(y);
	}

	/**
	 * The specified pointer is released.
	 * 
	 * @param pointerId the pointer to record.
	 * @param x
	 * @param y
	 */
	public void recordReleasedPointer(int pointerId, int x, int y)
	{
		Pointer _pointer = myNextStates[pointerId];
		_pointer.setState(Pointer.State.RELEASED);
		_pointer.setX(x);
		_pointer.setY(y);
	}

	/**
	 * The specified pointer is released.
	 * 
	 * @param pointerId the pointer to record.
	 * @param x
	 * @param y
	 */
	public void recordUndefinedPointer(int pointerId, int x, int y)
	{
		Pointer _pointer = myNextStates[pointerId];
		_pointer.setState(Pointer.State.UNDEFINED);
		_pointer.setX(x);
		_pointer.setY(y);
	}


}
