/**
 * 
 */
package com.sporniket.libre.game.pal.game.canvas.descriptor;

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
	private final int myHeight;

	/**
	 * Displayable label.
	 */
	private final String myLabel;

	/**
	 * Prefix to find image and sprite resources.
	 */
	private final String myPrefix;

	/**
	 * Canvas width.
	 */
	private final int myWidth;

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

}