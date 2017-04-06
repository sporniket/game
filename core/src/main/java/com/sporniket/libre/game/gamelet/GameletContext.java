package com.sporniket.libre.game.gamelet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.game.input.Key;
import com.sporniket.libre.game.input.Pointer;

public class GameletContext
{

	/**
	 * Storage for value that can be exchanged and/or persisted.
	 */
	private final Map<String, Object> myData = new HashMap<>();

	/**
	 * Storage for a log of {@link Key} recorded since the last game loop.
	 */
	private final List<Key> myKeyboardLog = new ArrayList<>(50);

	/**
	 * Store a log of {@link Pointer} recorded since the last game loop.
	 */
	private final List<Pointer> myPointerLog = new ArrayList<>(50);

	/**
	 * Storage for a log of typed chars recorded since the last game loop.
	 */
	private final List<Character> myTypedCharsLog = new ArrayList<>(50);

	public GameletContext()
	{
		super();
	}

	public Map<String, Object> getData()
	{
		return myData;
	}

	public List<Key> getKeyboardLog()
	{
		return myKeyboardLog;
	}

	public List<Pointer> getPointerLog()
	{
		return myPointerLog;
	}

	public List<Character> getTypedCharsLog()
	{
		return myTypedCharsLog;
	}

	public void setKeyboardLog(List<Key> newLog)
	{
		if (newLog != myKeyboardLog)
		{
			myKeyboardLog.clear();
			myKeyboardLog.addAll(newLog);
		}
	}

	public void setPointerLog(List<Pointer> newLog)
	{
		if (newLog != myPointerLog)
		{
			myPointerLog.clear();
			myPointerLog.addAll(newLog);
		}
	}

	public void setTypedCharsLog(List<Character> newLog)
	{
		if (newLog != myTypedCharsLog)
		{
			myTypedCharsLog.clear();
			myTypedCharsLog.addAll(newLog);
		}
	}

}