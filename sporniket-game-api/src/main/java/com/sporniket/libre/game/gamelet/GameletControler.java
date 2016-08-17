/**
 * 
 */
package com.sporniket.libre.game.gamelet;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.game.gamelet.events.Render;

/**
 * A gamelet controler references a list of gamelets, and run them in a running stack, waiting for events to put a new gamelet on
 * the stack or remove it from the stack and going back to the previous gamelet.
 * 
 * @author dsporn
 *
 */
public class GameletControler<CanvasType> implements GameletListener<CanvasType>
{
	private static final int INITIAL_CAPACITY__GAMELET_REGISTRY = 10;

	/**
	 * Index of the current canvas name to use.
	 */
	private int myCanvasBufferingCurrentIndex = -1;

	/**
	 * List of the names of the canvas that are used for the screen buffering (double, triple, ...), must
	 */
	private String[] myCanvasBufferingList;

	private GameletContext<CanvasType> myContext;

	/**
	 * Canvas id of the canvas into which drawing is done, initialized with a negative (invalid) value.
	 */
	private int myCurrentCanvas = -1;

	/**
	 * Canvas id of the canvas ready to display, initialized with a negative (invalid) value.
	 */
	private int myCurrentDisplayedCanvas = -1;

	private final Map<String, Gamelet<CanvasType>> myGameletRegistry = new HashMap<String, Gamelet<CanvasType>>(
			INITIAL_CAPACITY__GAMELET_REGISTRY);

	private final Deque<Gamelet<CanvasType>> myRunningStack = new ArrayDeque<Gamelet<CanvasType>>(
			INITIAL_CAPACITY__GAMELET_REGISTRY);

	public int getCanvasBufferingCurrentIndex()
	{
		return myCanvasBufferingCurrentIndex;
	}

	public String[] getCanvasBufferingList()
	{
		return myCanvasBufferingList;
	}

	public GameletContext<CanvasType> getContext()
	{
		return myContext;
	}

	public int getCurrentCanvas()
	{
		return myCurrentCanvas;
	}

	public int getCurrentDisplayedCanvas()
	{
		return myCurrentDisplayedCanvas;
	}

	@Override
	public void onBackward(Backward<CanvasType> event) throws GameletException
	{
		getRunningStack().removeLast();
	}

	@Override
	public void onForward(Forward<CanvasType> event) throws GameletException
	{
		String _name = event.getName();
		if (!getGameletRegistry().containsKey(_name))
		{
			throw new GameletNotFoundException(_name);
		}
		Gamelet<CanvasType> _gamelet = getGameletRegistry().get(_name);
		getRunningStack().addLast(_gamelet);

	}

	@Override
	public void onRender(Render<CanvasType> event) throws GameletException
	{
		// buffering management
		int _canvasBufferingCurrentIndex = (getCanvasBufferingCurrentIndex() + 1) % getCanvasBufferingList().length;
		setCanvasBufferingCurrentIndex(_canvasBufferingCurrentIndex);

		CanvasManager<CanvasType> _canvasManager = getContext().getCanvasManager();
		setCurrentCanvas(_canvasManager.getCanvasId(getCanvasBufferingList()[_canvasBufferingCurrentIndex]));
		event.getSource().render(_canvasManager, getCurrentCanvas(), getCurrentDisplayedCanvas());

		setCurrentDisplayedCanvas(getCurrentCanvas());
	}

	public void registerGamelet(String name, Gamelet<CanvasType> gamelet) throws GameletException
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
		Gamelet<CanvasType> _currentGamelet = getRunningStack().peekLast();
		_currentGamelet.run(elapsedTime, getContext());
	}

	public void setCanvasBufferingCurrentIndex(int canvasBufferingCurrentIndex)
	{
		myCanvasBufferingCurrentIndex = canvasBufferingCurrentIndex;
	}

	public void setCanvasBufferingList(String[] canvasBufferingList)
	{
		myCanvasBufferingList = canvasBufferingList;
	}

	public void setContext(GameletContext<CanvasType> context)
	{
		myContext = context;
	}

	public void setCurrentCanvas(int currentCanvas)
	{
		myCurrentCanvas = currentCanvas;
	}

	public void setCurrentDisplayedCanvas(int currentDisplayedCanvas)
	{
		myCurrentDisplayedCanvas = currentDisplayedCanvas;
	}

	private Map<String, Gamelet<CanvasType>> getGameletRegistry()
	{
		return myGameletRegistry;
	}

	private Deque<Gamelet<CanvasType>> getRunningStack()
	{
		return myRunningStack;
	}

}
