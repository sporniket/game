/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.gamelet.GameletContext;

/**
 * A GameletContext give access to game support classes.
 * 
 * @author dsporn
 *
 */
public class CanvasGameletContext<CanvasType> extends GameletContext
{
	private CanvasManager<CanvasType> myCanvasManager;

	/**
	 * The canvas manager used to do game rendering.
	 * 
	 * @return the canvas manager.
	 */
	public CanvasManager<CanvasType> getCanvasManager()
	{
		return myCanvasManager;
	}

	public void setCanvasManager(CanvasManager<CanvasType> canvasManager)
	{
		myCanvasManager = canvasManager;
	}
}
