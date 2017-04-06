/**
 *
 */
package com.sporniket.libre.game.input;

/**
 * {@link KeyboardEvent} for a typed char.
 * 
 * @author dsporn
 *
 */
public class KeyboardEventTypedChar extends KeyboardEvent
{
	private final char myChar;

	public KeyboardEventTypedChar(InputTranslator source, char c)
	{
		super(source);
		myChar = c;
	}

	public char getChar()
	{
		return myChar;
	}

}
