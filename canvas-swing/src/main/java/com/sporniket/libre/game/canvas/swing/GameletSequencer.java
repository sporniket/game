package com.sporniket.libre.game.canvas.swing;

import com.sporniket.libre.game.canvas.gamelet.CanvasGamelet;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletContext;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;

/**
 * Gamelet whose sole purpose is to forward to a sequence of other gamelets.
 * 
 * @author dsporn
 *
 */
class GameletSequencer<CanvasType> extends CanvasGamelet<CanvasType>
{
	private int myCurrentIndex;

	private final String[] mySequence;

	/**
	 * @param sequence
	 *            the sequence of gamelets to play.
	 */
	public GameletSequencer(String[] sequence)
	{
		if (null == sequence)
		{
			throw new IllegalArgumentException(new NullPointerException("sequence"));
		}
		mySequence = sequence;
	}

	@Override
	public void render(CanvasGameletContext<CanvasType> context, int cidDestination, int cidPreviousRender)
	{
		// nothing to do
	}

	@Override
	protected void exit(CanvasGameletContext<CanvasType> context) throws GameletException
	{
		// nothing to do
	}

	@Override
	protected void init(CanvasGameletContext<CanvasType> context) throws GameletException
	{
		// nothing to do
	}

	@Override
	protected void rewind(CanvasGameletContext<CanvasType> context) throws GameletException
	{
		myCurrentIndex = 0;
	}

	@Override
	protected void run(long elapsedTime, CanvasGameletContext<CanvasType> context) throws GameletException
	{
		if (getCurrentIndex() >= getSequence().length)
		{
			fireBackwardEvent(new Backward<CanvasGameletContext<CanvasType>>(this));
		}
		else
		{
			String _forwardTo = getSequence()[getCurrentIndex()];
			setCurrentIndex(getCurrentIndex() + 1);
			fireForwardEvent(new Forward<CanvasGameletContext<CanvasType>>(this, _forwardTo));
		}
	}

	private int getCurrentIndex()
	{
		return myCurrentIndex;
	}

	private String[] getSequence()
	{
		return mySequence;
	}

	private void setCurrentIndex(int currentIndex)
	{
		myCurrentIndex = currentIndex;
	}

}