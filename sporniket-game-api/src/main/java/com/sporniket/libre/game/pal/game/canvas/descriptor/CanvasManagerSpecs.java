/**
 * 
 */
package com.sporniket.libre.game.pal.game.canvas.descriptor;

/**
 * Specifications of canvas to create.
 * 
 * @author dsporn
 *
 */
public class CanvasManagerSpecs
{
	/**
	 * Canvas names of screen sized offscreen to create for the buffering, names SHOULD NOT collide.
	 */
	private String[] myBufferingNames;

	/**
	 * Specifications of all the canvas to create (name, type and definition among offscreens, sized offscreens, single image,
	 * variable image, ...), does not include the canvas for the buffering.
	 */
	private String[] myCanvasSpecs;

	public String[] getBufferingNames()
	{
		return myBufferingNames;
	}

	public String[] getCanvasSpecs()
	{
		return myCanvasSpecs;
	}

	public void setBufferingNames(String[] bufferingNames)
	{
		myBufferingNames = bufferingNames;
	}

	public void setCanvasSpecs(String[] canvasSpecs)
	{
		myCanvasSpecs = canvasSpecs;
	}

}
