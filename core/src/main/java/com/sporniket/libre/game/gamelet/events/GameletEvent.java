/**
 * 
 */
package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;
import com.sporniket.libre.game.gamelet.GameletContext;

/**
 * Enclosing class for gamelet events.
 * 
 * @author dsporn
 *
 */
public abstract class GameletEvent<ContextType extends GameletContext>
{
	private final Gamelet<ContextType> mySource;

	public GameletEvent(Gamelet<ContextType> source)
	{
		mySource = source;
	}

	public Gamelet<ContextType> getSource()
	{
		return mySource;
	}
}
