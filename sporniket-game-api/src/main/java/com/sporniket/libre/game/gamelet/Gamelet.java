package com.sporniket.libre.game.gamelet;

import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.game.gamelet.events.GameletEvent;
import com.sporniket.libre.game.gamelet.events.Render;

public abstract class Gamelet<ContextType extends GameletContext>
{

	private static final int INITIAL_CAPACITY__LISTENERS = 10;

	/**
	 * <code>true</code> when the gamelet sends the backward event.
	 */
	private boolean myFinished;

	private final List<GameletListener<ContextType>> myListeners = new ArrayList<GameletListener<ContextType>>(INITIAL_CAPACITY__LISTENERS);

	public Gamelet()
	{
		super();
	}

	/**
	 * Add a listener interested in {@link GameletEvent}.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void addListener(GameletListener<ContextType> listener)
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
	public void doExit(ContextType context) throws GameletException
	{
		exit(context);
	}

	/**
	 * Method to call before the first run.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void doInit(ContextType context) throws GameletException
	{
		init(context);
	}

	/**
	 * method to call before running again the gamelet.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void doRewind(ContextType context) throws GameletException
	{
		rewind(context);
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
	public void doRun(long elapsedTime, ContextType context) throws GameletException
	{
		if (isFinished())
		{
			throw new GameletException(new IllegalStateException("Gamelet is finished."));
		}
		run(elapsedTime, context);
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
	public void removeListener(GameletListener<ContextType> listener)
	{
		if (null != listener && getListeners().contains(listener))
		{
			getListeners().remove(listener);
		}
	}

	/**
	 * Extension point : specific exit code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void exit(ContextType context) throws GameletException;

	/**
	 * Notify listener of a {@link Backward} event.
	 * 
	 * @param event
	 *            the event to notify.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected void fireBackwardEvent(Backward<ContextType> event) throws GameletException
	{
		for (GameletListener<ContextType> _listener : getListeners())
		{
			_listener.onBackward(event);
		}
	}

	/**
	 * Notify listener of a {@link Forward} event.
	 * 
	 * @param event
	 *            the event to notify.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected void fireForwardEvent(Forward<ContextType> event) throws GameletException
	{
		for (GameletListener<ContextType> _listener : getListeners())
		{
			_listener.onForward(event);
		}
	}

	/**
	 * Notify listener of a {@link Render} event.
	 * 
	 * @param event
	 *            the event to notify.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected void fireRenderEvent(Render<ContextType> event) throws GameletException
	{
		for (GameletListener<ContextType> _listener : getListeners())
		{
			_listener.onRender(event);
		}
	}

	/**
	 * Extension point : specific init code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void init(ContextType context) throws GameletException;

	/**
	 * Extension point : specific before any run code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void rewind(ContextType context) throws GameletException;

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
	protected abstract void run(long elapsedTime, ContextType context) throws GameletException;

	protected void setFinished(boolean finished)
	{
		myFinished = finished;
	}

	private List<GameletListener<ContextType>> getListeners()
	{
		return myListeners;
	}

}