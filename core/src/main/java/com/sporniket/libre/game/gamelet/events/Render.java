package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;
import com.sporniket.libre.game.gamelet.GameletContext;

/**
 * When receiving a render event, the controler MUST call the rendering of the Gamelet.
 * 
 * @author dsporn
 *
 */
public class Render<ContextType extends GameletContext> extends GameletEvent<ContextType>
{

	public Render(Gamelet<ContextType> source)
	{
		super(source);
	}

}