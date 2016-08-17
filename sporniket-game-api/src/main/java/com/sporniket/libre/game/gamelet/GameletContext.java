/**
 * 
 */
package com.sporniket.libre.game.gamelet;

import com.sporniket.libre.game.canvas.CanvasManager;

/**
 * A GameletContext give access to game support classes.
 * 
 * @author dsporn
 *
 */
public class GameletContext<CanvasType>
{
	CanvasManager<CanvasType> myCanvasManager;

	public CanvasManager<CanvasType> getCanvasManager()
	{
		return myCanvasManager;
	}

	public void setCanvasManager(CanvasManager<CanvasType> canvasManager)
	{
		myCanvasManager = canvasManager;
	}
}
