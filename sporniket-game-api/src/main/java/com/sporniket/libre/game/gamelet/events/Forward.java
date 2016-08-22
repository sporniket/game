package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.CanvasGamelet;

/**
 * When receiving a forward event, the controler MUST give control to the named gamelet.
 * 
 * @author dsporn
 *
 */
public class Forward<CanvasType> extends GameletEvent<CanvasType>
{
	private final String myName;

	public Forward(CanvasGamelet<CanvasType> source, String name)
	{
		super(source);
		myName = name;
	}

	public String getName()
	{
		return myName;
	}
}