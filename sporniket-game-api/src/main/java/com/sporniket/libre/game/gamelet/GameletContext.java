/**
 * 
 */
package com.sporniket.libre.game.gamelet;

import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;

/**
 * A GameletContext give access to game support classes.
 * 
 * @author dsporn
 *
 */
public class GameletContext<CanvasType>
{
	private GameControllerStateProvider myGameControllers;

	private PointerStateProvider myPointers;

	CanvasManager<CanvasType> myCanvasManager;

	/**
	 * The canvas manager used to do game rendering.
	 * 
	 * @return the canvas manager.
	 */
	public CanvasManager<CanvasType> getCanvasManager()
	{
		return myCanvasManager;
	}

	/**
	 * Give access to the game controllers.
	 * 
	 * @return access to the game controller states.
	 */
	public GameControllerStateProvider getGameControllers()
	{
		return myGameControllers;
	}

	/**
	 * Give access to the pointer (mouse or finger touches)
	 * 
	 * @return the pointers.
	 */
	public PointerStateProvider getPointers()
	{
		return myPointers;
	}

	public void setCanvasManager(CanvasManager<CanvasType> canvasManager)
	{
		myCanvasManager = canvasManager;
	}

	public void setGameControllers(GameControllerStateProvider gameControllers)
	{
		myGameControllers = gameControllers;
	}

	public void setPointers(PointerStateProvider pointers)
	{
		myPointers = pointers;
	}
}
