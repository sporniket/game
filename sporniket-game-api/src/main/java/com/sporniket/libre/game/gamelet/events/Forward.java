package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;

/**
 * When receiving a forward event, the controler MUST give control to the named gamelet.
 * 
 * @author dsporn
 *
 */
public class Forward extends GameletEvent
{
	private final String myName;

	public Forward(Gamelet source, String name)
	{
		super(source);
		myName = name;
	}

	public String getName()
	{
		return myName;
	}
}