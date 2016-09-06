/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

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
	public void render(CanvasGameletContext<CanvasType> context, int cidDestination, int cidPreviousRender)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doExit()
	 */
	@Override
	protected void doExit(CanvasGameletContext<CanvasType> context) throws GameletException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doInit()
	 */
	@Override
	protected void doInit(CanvasGameletContext<CanvasType> context) throws GameletException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doRewind()
	 */
	@Override
	protected void doRewind(CanvasGameletContext<CanvasType> context) throws GameletException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doRun(long, com.sporniket.libre.game.gamelet.GameletContext)
	 */
	@Override
	protected void doRun(long elapsedTime, CanvasGameletContext<CanvasType> context) throws GameletException
	{
	}

}
