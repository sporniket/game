/**
 * 
 */
package com.sporniket.libre.game.canvas;


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

	private CanvasCallback<CanvasType> myRegenerator;

	private String myGuid;

	private int myWidth;

	private int myHeight;

	public CanvasType getCanvas()
	{
		return myCanvas;
	}

	void setCanvas(CanvasType canvas)
	{
		myCanvas = canvas;
	}

	String getGuid()
	{
		return myGuid;
	}

	void setGuid(String guid)
	{
		myGuid = guid;
	}

	void regenerate() throws CanvasException
	{
		if (null != getRegenerator())
		{
			getRegenerator().execute(getGuid(), getCanvas());
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

	public int getWidth()
	{
		return myWidth;
	}

	void setWidth(int width)
	{
		myWidth = width;
	}

	public int getHeight()
	{
		return myHeight;
	}

	void setHeight(int height)
	{
		myHeight = height;
	}

	CanvasCallback<CanvasType> getRegenerator()
	{
		return myRegenerator;
	}

	void setRegenerator(CanvasCallback<CanvasType> regenerator)
	{
		myRegenerator = regenerator;
	}

}
