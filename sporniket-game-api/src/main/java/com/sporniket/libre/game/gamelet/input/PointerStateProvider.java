package com.sporniket.libre.game.gamelet.input;


/**
 * Interfaces for using pointers (mouse, fingers, etc...).
 * @author dsporn
 * @deprecated
 */
public interface PointerStateProvider
{
	/**
	 * Get the number of supported pointers (==> support multitouch).
	 * @return
	 */
	int getPointerCount();
	
	/**
	 * Return one pointer per device/touch.
	 * @return
	 */
	Pointer[] getStates();
}