/**
 * 
 */
package com.sporniket.libre.game.gamelet;

import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.game.gamelet.events.GameletEvent;
import com.sporniket.libre.game.gamelet.events.Render;

/**
 * Model of a gamelet.
 * 
 * @author dsporn
 *
 */
public abstract class Gamelet<CanvasType>
{
	private static final int INITIAL_CAPACITY__LISTENERS = 10;

	/**
	 * <code>true</code> when the gamelet sends the backward event.
	 */
	private boolean myFinished;

	private final List<GameletListener<CanvasType>> myListeners = new ArrayList<GameletListener<CanvasType>>(
			INITIAL_CAPACITY__LISTENERS);

	/**
	 * Add a listener interested in {@link GameletEvent}.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void addListener(GameletListener<CanvasType> listener)
	{
		if (null != listener)
		{
			getListeners().add(listener);
		}
	}

	/**
	 * method to call before quitting.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void exit() throws GameletException
	{
		doExit();
	}

	public void fireBackwardEvent(Backward<CanvasType> event) throws GameletException
	{
		for (GameletListener<CanvasType> _listener : getListeners())
		{
			_listener.onBackward(event);
		}
	}

	public void fireForwardEvent(Forward<CanvasType> event) throws GameletException
	{
		for (GameletListener<CanvasType> _listener : getListeners())
		{
			_listener.onForward(event);
		}
	}

	public void fireRenderEvent(Render<CanvasType> event) throws GameletException
	{
		for (GameletListener<CanvasType> _listener : getListeners())
		{
			_listener.onRender(event);
		}
	}

	/**
	 * method to call before the first run.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void init() throws GameletException
	{
		doInit();
	}

	public boolean isFinished()
	{
		return myFinished;
	}

	/**
	 * Remove a listener interested in {@link GameletEvent}.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void removeListener(GameletListener<CanvasType> listener)
	{
		if (null != listener && getListeners().contains(listener))
		{
			getListeners().remove(listener);
		}
	}

	/**
	 * The rendering of the gamelet.
	 * 
	 * @param canvasManager
	 *            the canvas manager.
	 * @param cidDestination
	 *            the canvas id of the canvas into which the rendering will be made.
	 * @param cidPreviousRender
	 *            the canvas id of the previous rendering, may be invalid (negative).
	 */
	public abstract void render(CanvasManager<CanvasType> canvasManager, int cidDestination, int cidPreviousRender);

	/**
	 * method to call before running again the gamelet.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void rewind() throws GameletException
	{
		doRewind();
		setFinished(false);
	}

	/**
	 * Execute the gamelet (poll events, change state, send events, return).
	 * 
	 * @param elapsedTime
	 *            the elapsed time since the last call to run.
	 * @param context
	 *            the game context.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void run(long elapsedTime, GameletContext<CanvasType> context) throws GameletException
	{
		if (isFinished())
		{
			throw new GameletException(new IllegalStateException("Gamelet is finished."));
		}
		doRun(elapsedTime, context);
	}

	/**
	 * Extension point : specific exit code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doExit() throws GameletException;

	/**
	 * Extension point : specific init code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doInit() throws GameletException;

	/**
	 * Extension point : specific before any run code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doRewind() throws GameletException;

	/**
	 * Extension point : specific run code.
	 * 
	 * @param elapsedTime
	 *            the elapsed time since the last call to run.
	 * @param context
	 *            the game context.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doRun(long elapsedTime, GameletContext<CanvasType> context) throws GameletException;

	private List<GameletListener<CanvasType>> getListeners()
	{
		return myListeners;
	}

	private void setFinished(boolean finished)
	{
		myFinished = finished;
	}
}
