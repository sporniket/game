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
public abstract class CanvasGamelet<CanvasType> extends Gamelet
{
	private static final int INITIAL_CAPACITY__LISTENERS = 10;

	private final List<GameletListener> myListeners = new ArrayList<GameletListener>(INITIAL_CAPACITY__LISTENERS);

	/**
	 * Add a listener interested in {@link GameletEvent}.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void addListener(GameletListener listener)
	{
		if (null != listener)
		{
			getListeners().add(listener);
		}
	}

	public void fireBackwardEvent(Backward event) throws GameletException
	{
		for (GameletListener _listener : getListeners())
		{
			_listener.onBackward(event);
		}
	}

	public void fireForwardEvent(Forward event) throws GameletException
	{
		for (GameletListener _listener : getListeners())
		{
			_listener.onForward(event);
		}
	}

	public void fireRenderEvent(Render event) throws GameletException
	{
		for (GameletListener _listener : getListeners())
		{
			_listener.onRender(event);
		}
	}

	/**
	 * Remove a listener interested in {@link GameletEvent}.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void removeListener(GameletListener listener)
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
	 * Execute the gamelet (poll events, change state, send events, return).
	 * 
	 * @param elapsedTime
	 *            the elapsed time since the last call to run.
	 * @param context
	 *            the game context.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void run(long elapsedTime, CanvasGameletContext<CanvasType> context) throws GameletException
	{
		if (isFinished())
		{
			throw new GameletException(new IllegalStateException("Gamelet is finished."));
		}
		doRun(elapsedTime, context);
	}

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
	protected abstract void doRun(long elapsedTime, CanvasGameletContext<CanvasType> context) throws GameletException;

	private List<GameletListener> getListeners()
	{
		return myListeners;
	}
}
