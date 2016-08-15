/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

import com.sporniket.libre.game.canvas.CanvasCallback;
import com.sporniket.libre.game.canvas.CanvasException;

/**
 * Collection of canvas regenerators, that fill the canvas with something.
 * 
 * @author dsporn
 *
 */
public class RegeneratorFillers
{

	public static CanvasCallback<BufferedImage> getBlackFiller() throws CanvasException
	{
		try
		{
			Method _regenerator = RegeneratorFillers.class.getMethod("fillBlack", String.class, BufferedImage.class);
			return new CanvasCallback<BufferedImage>(null, _regenerator);
		}
		catch (NoSuchMethodException | SecurityException _exception)
		{
			throw new CanvasException(_exception);
		}
	}

	public static void fillBlack(String guid, BufferedImage canvas)
	{
		Graphics2D _g2 = canvas.createGraphics();
		_g2.setColor(Color.BLACK);
		_g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
}
