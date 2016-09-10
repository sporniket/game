package com.sporniket.libre.game.gamelet;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;

public abstract class GameletControler<ContextType extends GameletContext, GameletType extends Gamelet<ContextType>> implements
		GameletListener<ContextType>
{
	static final int INITIAL_CAPACITY__GAMELET_REGISTRY = 10;

	protected ContextType myContext;

	private final Map<String, GameletType> myGameletRegistry = new HashMap<String, GameletType>(INITIAL_CAPACITY__GAMELET_REGISTRY);

	private final Deque<GameletType> myRunningStack = new ArrayDeque<GameletType>(INITIAL_CAPACITY__GAMELET_REGISTRY);

	public GameletControler()
	{
		super();
	}

	public ContextType getContext()
	{
		return myContext;
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