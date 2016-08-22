package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;

/**
 * When receiving a backward event, the controler MUST give back control to the gamelet that previously send the forward event.
 * 
 * @author dsporn
 *
 */
public class Backward extends GameletEvent
{

	public Backward(Gamelet source)
	{
		super(source);
	}

}