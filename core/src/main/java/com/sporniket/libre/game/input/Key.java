/**
 *
 */
package com.sporniket.libre.game.input;

/**
 * Model for a recorded keyboard key state.
 *
 * This model is only used for pressed and released key events.
 *
 * @author dsporn
 *
 */
public class Key
{
	public static enum State
	{
		PRESSED,
		RELEASED;
	}

	private final long myPhysicalCode;

	private final State myState;

	public Key(long physicalCode, State state)
	{
		super();
		myPhysicalCode = physicalCode;
		myState = state;
	}

	public long getPhysicalCode()
	{
		return myPhysicalCode;
	}

	public State getState()
	{
		return myState;
	}

}
