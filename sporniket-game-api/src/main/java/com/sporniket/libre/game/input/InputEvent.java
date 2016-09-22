/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Base class for a game input event.
 * @author dsporn
 *
 */
public abstract class InputEvent
{
	private final InputTranslator mySource ;

	public InputEvent(InputTranslator source)
	{
		mySource = source;
	}

	public InputTranslator getSource()
	{
		return mySource;
	}
	
}
