package com.sporniket.libre.game.gamelet;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.game.input.InputProxy;
import com.sporniket.libre.game.input.InputTranslator;
import com.sporniket.libre.game.input.InputTranslatorListener;
import com.sporniket.libre.game.input.Key;
import com.sporniket.libre.game.input.KeyboardEvent;
import com.sporniket.libre.game.input.KeyboardEventPhysical;
import com.sporniket.libre.game.input.KeyboardEventTypedChar;
import com.sporniket.libre.game.input.Pointer;
import com.sporniket.libre.game.input.PointerEvent;

public abstract class GameletControler<ContextType extends GameletContext, GameletType extends Gamelet<ContextType>>
		implements GameletListener<ContextType>, InputTranslatorListener
{
	static final int INITIAL_CAPACITY__GAMELET_REGISTRY = 10;

	protected ContextType myContext;

	private final Map<String, GameletType> myGameletRegistry = new HashMap<String, GameletType>(INITIAL_CAPACITY__GAMELET_REGISTRY);

	private final InputProxy myInputProxy = new InputProxy();

	/**
	 * Storage for a log of {@link Pointer} that will be put into the context before the call to
	 * {@link GameletType#doRun(long, GameletContext)}.
	 */
	private final List<Pointer> myPointerLog = new ArrayList<Pointer>(50);

	/**
	 * Storage for a log of {@link Key} that will be put into the context before the call to
	 * {@link GameletType#doRun(long, GameletContext)}.
	 */
	private final List<Key> myKeyboardLog = new ArrayList<>(50);

	/**
	 * Storage for a log of typed chars that will be put into the context before the call to
	 * {@link GameletType#doRun(long, GameletContext)}.
	 */
	private final List<Character> myTypedCharsLog = new ArrayList<>(50);

	private final Deque<GameletType> myRunningStack = new ArrayDeque<GameletType>(INITIAL_CAPACITY__GAMELET_REGISTRY);

	public GameletControler()
	{
		super();
		getInputProxy().addListener(this);
	}

	public ContextType getContext()
	{
		return myContext;
	}

	/**
	 * Any {@link InputTranslator} must be listened by this proxy.
	 * 
	 * @return the input event proxy.
	 */
	public InputProxy getInputProxy()
	{
		return myInputProxy;
	}

	/**
	 * Predicate to know whether one can call {@link #run(long)};
	 * 
	 * @return <code>true</code> if there is still a gamelet to run.
	 */
	public boolean isRunning()
	{
		return !getRunningStack().isEmpty();
	}

	@Override
	public void onBackward(Backward<ContextType> event) throws GameletException
	{
		getRunningStack().removeLast();
	}

	@Override
	public void onForward(Forward<ContextType> event) throws GameletException
	{
		String _name = event.getName();
		if (!getGameletRegistry().containsKey(_name))
		{
			throw new GameletNotFoundException(_name);
		}
		GameletType _gamelet = getGameletRegistry().get(_name);
		getRunningStack().addLast(_gamelet);
		if (_gamelet.isInitRequired())
		{
			_gamelet.doInit(getContext());
		}
		else
		{
			_gamelet.doRewind(getContext());
		}
	}

	@Override
	public void onPointerEvent(PointerEvent event)
	{
		synchronized (myPointerLog)
		{
			myPointerLog.add(event.getPointer());
		}
	}

	@Override
	public void onKeyboardEvent(KeyboardEvent event)
	{
		if (event instanceof KeyboardEventPhysical)
		{
			synchronized (myKeyboardLog)
			{
				myKeyboardLog.add(((KeyboardEventPhysical) event).getKey());
			}
		}
		else if (event instanceof KeyboardEventTypedChar)
		{
			synchronized (myTypedCharsLog)
			{
				myTypedCharsLog.add(((KeyboardEventTypedChar) event).getChar());
			}
		}
		else
		{
			throw new IllegalArgumentException("Unsupported event type " + event.getClass().getName() + " : " + event.toString());
		}
	}

	public void registerGamelet(String name, GameletType gamelet) throws GameletException
	{
		// sanity check
		if (null == name)
		{
			throw new GameletException(new NullPointerException("name"));
		}
		if (null == gamelet)
		{
			throw new GameletException(new NullPointerException("gamelet"));
		}
		if (getGameletRegistry().containsKey(name))
		{
			throw new GameletException("a gamelet name can be registered only once.");
		}
		if (getGameletRegistry().containsValue(gamelet))
		{
			throw new GameletException("a gamelet can be registered only once.");
		}
		// ok
		getGameletRegistry().put(name, gamelet);
		gamelet.addListener(this);
	}

	public void run(long elapsedTime) throws GameletException
	{
		if (getRunningStack().isEmpty())
		{
			throw new GameletException(new IllegalStateException("nothing to run"));
		}
		// update pointer log in context.
		synchronized (myPointerLog)
		{
			getContext().setPointerLog(myPointerLog);
			myPointerLog.clear();
		}
		//update keyboard logs in context
		synchronized (myKeyboardLog)
		{
			getContext().setKeyboardLog(myKeyboardLog);
			myKeyboardLog.clear();
		}
		synchronized (myTypedCharsLog)
		{
			getContext().setTypedCharsLog(myTypedCharsLog);
			myTypedCharsLog.clear();
		}
		GameletType _currentGamelet = getRunningStack().peekLast();
		_currentGamelet.doRun(elapsedTime, getContext());
	}

	public void setContext(ContextType context)
	{
		myContext = context;
	}

	private Map<String, GameletType> getGameletRegistry()
	{
		return myGameletRegistry;
	}

	private Deque<GameletType> getRunningStack()
	{
		return myRunningStack;
	}

}