package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;

/**
 * When receiving a backward event, the controler MUST give back control to the gamelet that previously send the forward event.
 * 
 * @author dsporn
 *
 */
public class Backward<CanvasType> extends GameletEvent<CanvasType>
{

	public Backward(Gamelet<CanvasType> source)
	{
		super(source);
	}

}