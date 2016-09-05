/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.gamelet.GameletContext;
import com.sporniket.libre.game.gamelet.GameletException;

/**
 * Default implementation of {@link CanvasGamelet} that do nothing (empty implementation of abstract methods), to avoid boiler plate code.
 * 
 * @author dsporn
 *
 */
public class DefaultCanvasGamelet<CanvasType> extends CanvasGamelet<CanvasType>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.canvas.gamelet.CanvasGamelet#render(com.sporniket.libre.game.canvas.CanvasManager, int, int)
	 */
	@Override
	public void render(CanvasManager<CanvasType> canvasManager, int cidDestination, int cidPreviousRender)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doExit()
	 */
	@Override
	protected void doExit() throws GameletException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doInit()
	 */
	@Override
	protected void doInit() throws GameletException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doRewind()
	 */
	@Override
	protected void doRewind() throws GameletException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doRun(long, com.sporniket.libre.game.gamelet.GameletContext)
	 */
	@Override
	protected void doRun(long elapsedTime, GameletContext context) throws GameletException
	{
	}

}
