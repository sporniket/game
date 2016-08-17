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
public abstract class GameletEvent<CanvasType>
{
	private final Gamelet<CanvasType> mySource;

	public GameletEvent(Gamelet<CanvasType> source)
	{
		mySource = source;
	}

	public Gamelet<CanvasType> getSource()
	{
		return mySource;
	}
}
