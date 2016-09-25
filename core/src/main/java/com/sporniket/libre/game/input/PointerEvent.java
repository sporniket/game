/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Input event for a pointer (mouse or touch event).
 * 
 * @author dsporn
 *
 */
public class PointerEvent extends InputEvent
{
	private final Pointer myPointer ;

	public PointerEvent(InputTranslator source, Pointer pointer)
	{
		super(source);
		myPointer = pointer ;
	}

	public Pointer getPointer()
	{
		return myPointer;
	}

}
