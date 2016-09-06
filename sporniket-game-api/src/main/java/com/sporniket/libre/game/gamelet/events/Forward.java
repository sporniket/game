package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;
import com.sporniket.libre.game.gamelet.GameletContext;

/**
 * When receiving a forward event, the controler MUST give control to the named gamelet.
 * 
 * @author dsporn
 *
 */
public class Forward<ContextType extends GameletContext> extends GameletEvent<ContextType>
{
	private final String myName;

	public Forward(Gamelet<ContextType> source, String name)
	{
		super(source);
		myName = name;
	}

	public String getName()
	{
		return myName;
	}
}