package com.sporniket.libre.game.gamelet;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;

public abstract class GameletControler implements GameletListener
{
	static final int INITIAL_CAPACITY__GAMELET_REGISTRY = 10;

	protected GameletContext myContext;

	private final Map<String, Gamelet> myGameletRegistry = new HashMap<String, Gamelet>(INITIAL_CAPACITY__GAMELET_REGISTRY);

	private final Deque<Gamelet> myRunningStack = new ArrayDeque<Gamelet>(INITIAL_CAPACITY__GAMELET_REGISTRY);

	public GameletControler()
	{
		super();
	}

	public GameletContext getContext()
	{
		return myContext;
	}

	@Override
	public void onBackward(Backward event) throws GameletException
	{
		getRunningStack().removeLast();
	}

	@Override
	public void onForward(Forward event) throws GameletException
	{
		String _name = event.getName();
		if (!getGameletRegistry().containsKey(_name))
		{
			throw new GameletNotFoundException(_name);
		}
		Gamelet _gamelet = getGameletRegistry().get(_name);
		getRunningStack().addLast(_gamelet);

	}

	public void registerGamelet(String name, Gamelet gamelet) throws GameletException
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
		Gamelet _currentGamelet = getRunningStack().peekLast();
		_currentGamelet.run(elapsedTime, getContext());
	}

	public void setContext(GameletContext context)
	{
		myContext = context;
	}

	private Map<String, Gamelet> getGameletRegistry()
	{
		return myGameletRegistry;
	}

	private Deque<Gamelet> getRunningStack()
	{
		return myRunningStack;
	}

}