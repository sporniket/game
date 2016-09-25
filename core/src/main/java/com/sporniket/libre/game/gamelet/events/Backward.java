package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;
import com.sporniket.libre.game.gamelet.GameletContext;

/**
 * When receiving a backward event, the controler MUST give back control to the gamelet that previously send the forward event.
 * 
 * @author dsporn
 *
 */
public class Backward<ContextType extends GameletContext> extends GameletEvent<ContextType>
{

	public Backward(Gamelet<ContextType> source)
	{
		super(source);
	}

}