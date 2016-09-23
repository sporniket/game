package com.sporniket.libre.game.gamelet;

import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.input.Pointer;

public class GameletContext
{

	private GameControllerStateProvider myGameControllers;

	/**
	 * Store a log of {@link Pointer} recorded since the last call.
	 */
	private final List<Pointer> myPointerLog = new ArrayList<Pointer>(50);

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

	public List<Pointer> getPointerLog()
	{
		return myPointerLog;
	}

	public void setGameControllers(GameControllerStateProvider gameControllers)
	{
		myGameControllers = gameControllers;
	}

	public void setPointerLog(List<Pointer> newLog)
	{
		if (newLog != myPointerLog)
		{
			myPointerLog.clear();
			myPointerLog.addAll(newLog);
		}
	}

}