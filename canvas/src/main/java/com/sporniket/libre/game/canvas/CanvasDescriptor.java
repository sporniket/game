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

	private CanvasFiller<CanvasType> myFiller;

	private String myGuid;

	private int myHeight;

	private int myWidth;

	/**
	 * Make the descriptor "disposed", meaning the canvas cannot be used anymore.
	 */
	void dispose()
	{
		setCanvas(null);
	}

	public CanvasType getCanvas()
	{
		return myCanvas;
	}

	public CanvasFiller<CanvasType> getFiller()
	{
		return myFiller;
	}

	String getGuid()
	{
		return myGuid;
	}

	public int getHeight()
	{
		return myHeight;
	}

	public int getWidth()
	{
		return myWidth;
	}

	/**
	 * @return <code>true</code> if the canvas is not set.
	 */
	public boolean isDisposed()
	{
		return null == getCanvas();
	}

	void setCanvas(CanvasType canvas)
	{
		myCanvas = canvas;
	}

	public void setFiller(CanvasFiller<CanvasType> filler)
	{
		myFiller = filler;
	}

	void setGuid(String guid)
	{
		myGuid = guid;
	}

	void setHeight(int height)
	{
		myHeight = height;
	}

	void setWidth(int width)
	{
		myWidth = width;
	}

}
