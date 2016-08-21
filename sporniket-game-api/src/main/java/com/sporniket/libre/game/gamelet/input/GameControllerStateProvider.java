package com.sporniket.libre.game.gamelet.input;


/**
 * Interface for using game controllers.
 * @author dsporn
 *
 */
public interface GameControllerStateProvider
{
	/**
	 * Get the number of controllers present.
	 * @return
	 */
	int getControllerCount();
	
	/**
	 * Return one {@link Joystick} instance per controller.
	 * @return
	 */
	Joystick[] getStates();
}