/**
 *
 */
package com.sporniket.libre.game.canvas.descriptor;

import java.awt.Color;

/**
 * Fully specified canvas entry.
 *
 * @author dsporn
 *
 */
public class CanvasManagerSpecEntryOffscreen extends CanvasManagerSpecEntry
{
	private Color myColor;

	private int myHeight;

	private int myWidth;

	public Color getColor()
	{
		return myColor;
	}

	public int getHeight()
	{
		return myHeight;
	}

	public int getWidth()
	{
		return myWidth;
	}

	public void setColor(Color color)
	{
		myColor = color;
	}

	public void setHeight(int height)
	{
		myHeight = height;
	}

	public void setWidth(int width)
	{
		myWidth = width;
	}

}
