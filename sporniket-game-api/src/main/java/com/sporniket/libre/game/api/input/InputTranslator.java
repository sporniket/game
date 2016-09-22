/**
 * 
 */
package com.sporniket.libre.game.api.input;

import java.util.ArrayList;
import java.util.List;

/**
 * An InputTranslator basically emit game input events that are notified to {@link InputTranslatorListener}, typically from native
 * events but there may be other use cases.
 * 
 * @author dsporn
 *
 */
public abstract class InputTranslator
{
	
	/**
	 * the translator listeners.
	 */
	// most of the time there will be one listener.
	private final List<InputTranslatorListener> myListeners = new ArrayList<InputTranslatorListener>(5); 

	/**
	 * Register the listener.
	 * 
	 * @param listener
	 *            the listener to notify of game input events.
	 */
	public void addListener(InputTranslatorListener listener)
	{
		if (!getListeners().contains(listener))
		{
			getListeners().add(listener);
		}
	}

	/**
	 * Register several listeners at once.
	 * 
	 * @param listeners
	 *            the list of listeners to notify of game input events.
	 */
	public void addListeners(InputTranslatorListener... listeners)
	{
		for (InputTranslatorListener _listener : listeners)
		{
			addListener(_listener);
		}
	}

	/**
	 * Unregister a listener.
	 * 
	 * @param listener
	 *            the listener that will not be notified any more.
	 */
	public void removeListener(InputTranslatorListener listener)
	{
		if (getListeners().contains(listener))
		{
			getListeners().remove(listener);
		}
	}

	protected void fireGameControllerEvent(GameControllerEvent event)
	{
		for (InputTranslatorListener _listener : getListeners())
		{
			_listener.onGameControllerEvent(event);
		}
	}

	protected void fireKeyboardEvent(KeyboardEvent event)
	{
		for (InputTranslatorListener _listener : getListeners())
		{
			_listener.onKeyboardEvent(event);
		}
	}

	protected void firePointerEvent(PointerEvent event)
	{
		for (InputTranslatorListener _listener : getListeners())
		{
			_listener.onPointerEvent(event);
		}
	}

	protected void fireSensorEvent(SensorEvent event)
	{
		for (InputTranslatorListener _listener : getListeners())
		{
			_listener.onSensorEvent(event);
		}
	}

	private List<InputTranslatorListener> getListeners()
	{
		return myListeners;
	}
}
