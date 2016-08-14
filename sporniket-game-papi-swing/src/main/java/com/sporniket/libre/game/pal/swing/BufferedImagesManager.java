/**
 * 
 */
package com.sporniket.libre.game.pal.swing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.sporniket.libre.game.api.canvas.Box;
import com.sporniket.libre.game.api.canvas.BoxCopyMachine;
import com.sporniket.libre.game.api.canvas.CanvasManager;
import com.sporniket.libre.game.api.canvas.Point;

/**
 * @author dsporn
 *
 */
public class BufferedImagesManager extends CanvasManager<BufferedImage> implements BoxCopyMachine, ImageObserver
{
	public BufferedImagesManager(int screenWidth, int screenHeight)
	{
		super(screenWidth, screenHeight);
	}

	@Override
	protected BufferedImage createCanvasData(int width, int height)
	{
		BufferedImage _image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		return _image;
	}

	@Override
	public void copy(int canvasIdFrom, Box boxSpecs, int canvasIdTo, Point position)
	{
		BufferedImage _from = getCanvasRegistry().get(canvasIdFrom).getCanvas() ;
		Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics() ;
		
		Box _toBox = new Box().withX(position.getX()).withY(position.getY()).withWidth(boxSpecs.getWidth()).withHeight(boxSpecs.getHeight());
		
		_to.drawImage(_from, _toBox.getX(), _toBox.getY(), _toBox.getX2(), _toBox.getY2(), boxSpecs.getX(), boxSpecs.getY(), boxSpecs.getX2(), boxSpecs.getY2(), this) ;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
	{
		//do nothing
		return false;
	}
}
