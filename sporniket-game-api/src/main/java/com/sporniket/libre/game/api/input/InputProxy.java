/**
 * 
 */
package com.sporniket.libre.game.api.input;

/**
 * Special {@link InputTranslator} that will concentrate any game input event from several {@link InputTranslator}.
 * 
 * @author dsporn
 *
 */
public final class InputProxy extends InputTranslator implements InputTranslatorListener
{

	@Override
	public void onGameControllerEvent(GameControllerEvent event)
	{
		fireGameControllerEvent(event);
	}

	@Override
	public void onKeyboardEvent(KeyboardEvent event)
	{
		fireKeyboardEvent(event);
	}

	@Override
	public void onPointerEvent(PointerEvent event)
	{
		firePointerEvent(event);
	}

	@Override
	public void onSensorEvent(SensorEvent event)
	{
		fireSensorEvent(event);
	}

}
