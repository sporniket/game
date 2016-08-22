package com.sporniket.libre.game.gamelet.events;

import com.sporniket.libre.game.gamelet.Gamelet;

/**
 * When receiving a render event, the controler MUST call the rendering of the Gamelet.
 * 
 * @author dsporn
 *
 */
public class Render extends GameletEvent
{

	public Render(Gamelet source)
	{
		super(source);
	}

}