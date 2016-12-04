/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.gamelet.events.Render;

/**
 * Default implementation of {@link CanvasGamelet} that do nothing (empty
 * implementation of abstract methods), to avoid boiler plate code.
 * 
 * @author dsporn
 *
 */
public class DefaultCanvasGamelet<CanvasType> extends CanvasGamelet<CanvasType> {
	private final Render<CanvasGameletContext<CanvasType>> MY_RENDER_EVENT = new Render<CanvasGameletContext<CanvasType>>(
			this);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.canvas.gamelet.CanvasGamelet#render(com.
	 * sporniket.libre.game.canvas.CanvasManager, int, int)
	 */
	@Override
	public void render(CanvasGameletContext<CanvasType> context, int cidDestination, int cidPreviousRender) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doExit()
	 */
	@Override
	protected void exit(CanvasGameletContext<CanvasType> context) throws GameletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doInit()
	 */
	@Override
	protected void init(CanvasGameletContext<CanvasType> context) throws GameletException {
	}

	/**
	 * Signal that a call to {@link #render(CanvasGameletContext, int, int)}
	 * MUST occur.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected void requestRender() throws GameletException {
		fireRenderEvent(MY_RENDER_EVENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doRewind()
	 */
	@Override
	protected void rewind(CanvasGameletContext<CanvasType> context) throws GameletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.gamelet.Gamelet#doRun(long,
	 * com.sporniket.libre.game.gamelet.GameletContext)
	 */
	@Override
	protected void run(long elapsedTime, CanvasGameletContext<CanvasType> context) throws GameletException {
	}

}
