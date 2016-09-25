/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Listener of {@link InputTranslator}, will be notified when there is an input event.
 * 
 * @author dsporn
 *
 */
public interface InputTranslatorListener
{
	/**
	 * Process {@link GameControllerEvent}.
	 * @param event the event to process.
	 */
	default void onGameControllerEvent(GameControllerEvent event)
	{
		//do nothing by default
	};

	/**
	 * Process {@link KeyboardEvent}.
	 * @param event the event to process.
	 */
	default void onKeyboardEvent(KeyboardEvent event)
	{
		//do nothing by default
	};

	/**
	 * Process {@link PointerEvent}.
	 * @param event the event to process.
	 */
	default void onPointerEvent(PointerEvent event)
	{
		//do nothing by default
	};

	/**
	 * Process {@link SensorEvent}.
	 * @param event the event to process.
	 */
	default void onSensorEvent(SensorEvent event)
	{
		//do nothing by default
	};
}
