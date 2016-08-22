/**
 * 
 */
package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.CanvasGamelet;

/**
 * Enclosing class for gamelet events.
 * 
 * @author dsporn
 *
 */
public abstract class GameletEvent<CanvasType>
{
	private final CanvasGamelet<CanvasType> mySource;

	public GameletEvent(CanvasGamelet<CanvasType> source)
	{
		mySource = source;
	}

	public CanvasGamelet<CanvasType> getSource()
	{
		return mySource;
	}
}
