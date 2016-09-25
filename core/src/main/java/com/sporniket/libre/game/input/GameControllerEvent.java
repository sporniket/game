/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Input event from a game controller (gamepad, joystick, steering wheel,...).
 * @author dsporn
 *
 */
public class GameControllerEvent extends InputEvent
{

	public GameControllerEvent(InputTranslator source)
	{
		super(source);
	}

}
