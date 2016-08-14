/**
 * 
 */
package com.sporniket.libre.game.canvas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Canvas description, with the actual canvas, a callback and a globally unique identifier.
 * 
 * The callback is called when the canvas has been freed and reallocated. It accepts the canvas guid and the target canvas as
 * argument.
 * 
 * @author dsporn
 *
 */
public class CanvasDescriptor<CanvasType>
{
	private CanvasType myCanvas;

	private Object myCallbackHolder;

	private Method myCallback;

	private String myGuid;

	public CanvasType getCanvas()
	{
		return myCanvas;
	}

	void setCanvas(CanvasType canvas)
	{
		myCanvas = canvas;
	}

	Object getCallbackHolder()
	{
		return myCallbackHolder;
	}

	void setCallbackHolder(Object callbackHolder)
	{
		myCallbackHolder = callbackHolder;
	}

	Method getCallback()
	{
		return myCallback;
	}

	void setCallback(Method callback)
	{
		myCallback = callback;
	}

	String getGuid()
	{
		return myGuid;
	}

	void setGuid(String guid)
	{
		myGuid = guid;
	}

	void regenerate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if (null != getCallback() && null != getCallbackHolder())
		{
			getCallback().invoke(getCallbackHolder(), getGuid(), getCanvas());
		}
	}

	/**
	 * @return <code>true</code> if the canvas is not set.
	 */
	boolean isDisposed()
	{
		return null == getCanvas();
	}

	/**
	 * Make the descriptor "disposed", meaning the canvas cannot be used anymore.
	 */
	void dispose()
	{
		setCanvas(null);
	}

}
