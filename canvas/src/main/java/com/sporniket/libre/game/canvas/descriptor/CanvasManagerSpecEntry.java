/**
 *
 */
package com.sporniket.libre.game.canvas.descriptor;

/**
 * Base class for a canvas specification entry.
 *
 * @author dsporn
 *
 */
public abstract class CanvasManagerSpecEntry
{
	private String myName;

	public CanvasManagerSpecEntry()
	{
		// javabeans convention
	}

	public CanvasManagerSpecEntry(String name)
	{
		myName = name;
	}

	public String getName()
	{
		return myName;
	}

	public void setName(String name)
	{
		myName = name;
	}
}
