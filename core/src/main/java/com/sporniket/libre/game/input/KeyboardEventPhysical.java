/**
 *
 */
package com.sporniket.libre.game.input;

/**
 * {@link KeyboardEvent} for a physical event (physical key press/release).
 * 
 * @author dsporn
 *
 */
public class KeyboardEventPhysical extends KeyboardEvent
{
	final Key myKey;

	public KeyboardEventPhysical(InputTranslator source, Key key)
	{
		super(source);
		myKey = key;
	}

	public Key getKey()
	{
		return myKey;
	}
}
