/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sporniket.libre.game.canvas.CanvasException;

/**
 * @author dsporn
 *
 */
public class ColorFiller extends BufferedImageFiller
{
	Color myColor;

	@Override
	public void fill() throws CanvasException
	{
		assertThatCanvasIsAccessible();
		BufferedImage _canvas = getTarget().getCanvas();
		Graphics2D _g2 = _canvas.createGraphics();
		_g2.setComposite(AlphaComposite.SrcOver);

		_g2.setColor(Color.BLACK);
		_g2.setBackground(Color.BLACK);
		_g2.fillRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
	}

	public Color getColor()
	{
		return myColor;
	}

	public void setColor(Color color)
	{
		myColor = color;
	}

}
