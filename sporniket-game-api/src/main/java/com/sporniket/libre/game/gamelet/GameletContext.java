package com.sporniket.libre.game.gamelet;

import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;

public class GameletContext
{

	private GameControllerStateProvider myGameControllers;

	private PointerStateProvider myPointers;

	public GameletContext()
	{
		super();
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

	public void setGameControllers(GameControllerStateProvider gameControllers)
	{
		myGameControllers = gameControllers;
	}

	public void setPointers(PointerStateProvider pointers)
	{
		myPointers = pointers;
	}

}