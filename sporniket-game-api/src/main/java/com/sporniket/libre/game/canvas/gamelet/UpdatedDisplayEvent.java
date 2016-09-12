/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

/**
 * Event to notify that the canvas to display is ready.
 * 
 * @author dsporn
 *
 */
public class UpdatedDisplayEvent
{
	private final int myCanvasId;

	public UpdatedDisplayEvent(int canvasId)
	{
		myCanvasId = canvasId;
	}

	/**
	 * Get the id of the canvas to display.
	 * @return the id of the canvas to display.
	 */
	public int getCanvasId()
	{
		return myCanvasId;
	}
}
