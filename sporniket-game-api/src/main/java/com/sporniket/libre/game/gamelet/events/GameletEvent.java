/**
 * 
 */
package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;

/**
 * Enclosing class for gamelet events.
 * 
 * @author dsporn
 *
 */
public abstract class GameletEvent
{
	private final Gamelet mySource;

	public GameletEvent(Gamelet source)
	{
		mySource = source;
	}

	public Gamelet getSource()
	{
		return mySource;
	}
}
