/**
 * 
 */
package com.sporniket.libre.game.canvas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author dsporn
 *
 */
@Deprecated
public class CanvasCallback<CanvasType>
{

	private final Object myCallbackHolder;

	private final Method myCallback;

	Object getCallbackHolder()
	{
		return myCallbackHolder;
	}

	Method getCallback()
	{
		return myCallback;
	}

	public CanvasCallback(Object callbackHolder, Method callback)
	{
		myCallbackHolder = callbackHolder;
		myCallback = callback;
	}

	public void execute(String guid, CanvasType canvas) throws CanvasException
	{
		if (null != getCallback())
		{
			try
			{
				getCallback().invoke(getCallbackHolder(), guid, canvas);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException _exception)
			{
				throw new CanvasException(_exception);
			}
		}

	}
}
