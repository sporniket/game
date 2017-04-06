/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;

import com.sporniket.libre.game.input.InputTranslator;
import com.sporniket.libre.game.input.Key;
import com.sporniket.libre.game.input.Key.State;
import com.sporniket.libre.game.input.KeyboardEvent;
import com.sporniket.libre.game.input.KeyboardEventPhysical;
import com.sporniket.libre.game.input.KeyboardEventTypedChar;

/**
 * Listen to {@link KeyEvent} and translate them into {@link KeyboardEvent}
 * 
 * @author dsporn
 *
 */
public class KeyboardInputTranslator extends InputTranslator implements KeyListener
{

	private long getPhysicalKey(KeyEvent e)
	{
		Long _result = getPrivateFieldValueAsLong(e, "scancode");
		if (null == _result || 0 == _result)
		{
			_result = getPrivateFieldValueAsLong(e, "rawCode");
		}
		return _result;
	}

	private Long getPrivateFieldValueAsLong(final KeyEvent holder, String fieldName)
	{
		try
		{
			Field field = KeyEvent.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.getLong(holder);
		}
		catch (NoSuchFieldException | SecurityException | IllegalAccessException __exception)
		{
			__exception.printStackTrace();
			return null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		fireKeyboardEvent(new KeyboardEventPhysical(this, new Key(getPhysicalKey(e), State.PRESSED)));
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		fireKeyboardEvent(new KeyboardEventPhysical(this, new Key(getPhysicalKey(e), State.RELEASED)));
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		fireKeyboardEvent(new KeyboardEventTypedChar(this, e.getKeyChar()));
	}

}
