package com.sporniket.libre.game.gamelet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.game.input.Pointer;

public class GameletContext
{

	/**
	 * Storage for value that can be exchanged and/or persisted.
	 */
	private final Map<String, Object> myData = new HashMap<>();

	/**
	 * Store a log of {@link Pointer} recorded since the last call.
	 */
	private final List<Pointer> myPointerLog = new ArrayList<>(50);

	public GameletContext()
	{
		super();
	}

	public Map<String, Object> getData()
	{
		return myData;
	}

	public List<Pointer> getPointerLog()
	{
		return myPointerLog;
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