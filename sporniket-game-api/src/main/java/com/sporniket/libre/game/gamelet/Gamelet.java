package com.sporniket.libre.game.gamelet;

import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.game.gamelet.events.GameletEvent;
import com.sporniket.libre.game.gamelet.events.Render;

public abstract class Gamelet<ContextType extends GameletContext>
{
	/**
	 * Lifecycle status of a gamelet.
	 * 
	 * <p>
	 * The gamelet lifecycle is following :
	 * <ol>
	 * <li>{@link #CREATED} : the gamelet has just been created, the next call MUST be {@link Gamelet#doInit(GameletContext)}.
	 * <li>{@link #RUNNABLE} : one can call {@link Gamelet#doRun(long, GameletContext)}.
	 * <li>{@link #FINISHED} : a {@link Backward} event has been fired, one can call {@link Gamelet#doRewind(GameletContext)} to
	 * replay the gamelet or call {@link Gamelet#doExit(GameletContext)}
	 * <li>{@link #DISPOSABLE} : the gamelet cannot be run anymore, it is ready for game exit.
	 * </ol>
	 * 
	 * @author dsporn
	 *
	 */
	private static enum State
	{
		CREATED,
		DISPOSABLE,
		FINISHED,
		RUNNABLE;
	}

	private static final int INITIAL_CAPACITY__LISTENERS = 10;

	private final List<GameletListener<ContextType>> myListeners = new ArrayList<GameletListener<ContextType>>(
			INITIAL_CAPACITY__LISTENERS);

	private State myState = State.CREATED;

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
		setState(State.DISPOSABLE);
	}

	/**
	 * Method to call before the first run.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void doInit(ContextType context) throws GameletException
	{
		if (State.CREATED != getState())
		{
			throw new GameletException(new IllegalStateException(getState().toString()));
		}
		init(context);
		setState(State.RUNNABLE);
	}

	/**
	 * method to call before running again the gamelet.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void doRewind(ContextType context) throws GameletException
	{
		if (State.FINISHED != getState())
		{
			throw new GameletException(new IllegalStateException(getState().toString()));
		}
		rewind(context);
		setState(State.RUNNABLE);
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
		if (State.RUNNABLE != getState())
		{
			throw new GameletException(new IllegalStateException(getState().toString()));
		}
		run(elapsedTime, context);
	}

	/**
	 * Game state query.
	 * 
	 * @return <code>true</code> when the gamelet cannot be runned anymore (temporarily or definitively).
	 */
	public boolean isFinished()
	{
		return State.FINISHED == getState() || State.DISPOSABLE == getState();
	}

	/**
	 * Game state query.
	 * 
	 * @return <code>true</code> when the gamelet MUST be initialized (call {@link #doInit(GameletContext)}.
	 */
	public boolean isInitRequired()
	{
		return State.CREATED == getState();
	}

	/**
	 * Game state query.
	 * 
	 * @return <code>true</code> when the gamelet may be run again provided one call {@link #rewind(GameletContext)} before.
	 */
	public boolean isRewindable()
	{
		return State.FINISHED == getState();
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
		setState(State.FINISHED);
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

	private List<GameletListener<ContextType>> getListeners()
	{
		return myListeners;
	}

	private State getState()
	{
		return myState;
	}

	private void setState(State state)
	{
		myState = state;
	}

}