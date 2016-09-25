/**
 * 
 */
package com.sporniket.libre.game.canvas.descriptor;

/**
 * Specifications of the canvas.
 * 
 * A game can support multiple canvas size and thus the game may ask the user to choose one.
 * 
 * @author dsporn
 *
 */
public class CanvasSpecs
{
	/**
	 * Canvas height.
	 */
	private int myHeight;

	/**
	 * Displayable label.
	 */
	private String myLabel;

	/**
	 * Prefix to find image and sprite resources.
	 */
	private String myPrefix;

	/**
	 * Canvas width.
	 */
	private int myWidth;

	public CanvasSpecs(String label, int width, int height, String prefix)
	{
		myLabel = label;
		myWidth = width;
		myHeight = height;
		myPrefix = prefix;
	}

	public int getHeight()
	{
		return myHeight;
	}

	public String getLabel()
	{
		return myLabel;
	}

	public String getPrefix()
	{
		return myPrefix;
	}

	public int getWidth()
	{
		return myWidth;
	}

	public void setHeight(int height)
	{
		myHeight = height;
	}

	public void setLabel(String label)
	{
		myLabel = label;
	}

	public void setPrefix(String prefix)
	{
		myPrefix = prefix;
	}

	public void setWidth(int width)
	{
		myWidth = width;
	}

}
