/**
 *
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.sporniket.libre.game.canvas.Box;
import com.sporniket.libre.game.canvas.CanvasDescriptor;
import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.canvas.Point;

/**
 * The swing implementation of {@link CanvasManager} uses {@link BufferedImage}.
 *
 * @author dsporn
 *
 */
public class BufferedImagesManager extends CanvasManager<BufferedImage> implements ImageObserver
{
	private ImageObserver myImageObserver = this;

	public BufferedImagesManager(int screenWidth, int screenHeight)
	{
		super(screenWidth, screenHeight);
	}

	@Override
	public void clear(Box boxSpecs, int canvasIdTo, Point position)
	{
		// TODO Auto-generated method stub
		final Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics();
		_to.setComposite(AlphaComposite.Clear);

		_to.fillRect(position.getX(), position.getY(), boxSpecs.getWidth(), boxSpecs.getHeight());
	}

	@Override
	public void copy(int canvasIdFrom, Box boxSpecs, int canvasIdTo, Point position)
	{
		final Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics();
		_to.setComposite(AlphaComposite.SrcOver);

		copyToGraphics(canvasIdFrom, boxSpecs, _to, position, getImageObserver());
	}

	void copyToGraphics(int canvasIdFrom, Box boxSpecs, Graphics2D to, Point position, ImageObserver observer)
	{
		final BufferedImage _from = getCanvasRegistry().get(canvasIdFrom).getCanvas();
		final Box _toBox = new Box().withX(position.getX()).withY(position.getY()).withWidth(boxSpecs.getWidth())
				.withHeight(boxSpecs.getHeight());

		to.drawImage(_from, _toBox.getX(), _toBox.getY(), _toBox.getX2(), _toBox.getY2(), boxSpecs.getX(), boxSpecs.getY(),
				boxSpecs.getX2(), boxSpecs.getY2(), observer);
	}

	@Override
	protected BufferedImage createCanvasData(int width, int height)
	{
		final BufferedImage _image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		return _image;
	}

	private ImageObserver getImageObserver()
	{
		return myImageObserver;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
	{
		// do nothing
		return false;
	}

	@Override
	public void replace(int canvasIdFrom, Box boxSpecs, int canvasIdTo, Point position)
	{
		final Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics();
		_to.setComposite(AlphaComposite.Src);

		copyToGraphics(canvasIdFrom, boxSpecs, _to, position, getImageObserver());
	}

	@Override
	public void setFiller(int canvas, Color color)
	{
		CanvasDescriptor<BufferedImage> _canvas = getCanvasRegistry().get(canvas);
		ColorFiller _filler = new ColorFiller(color);
		_canvas.setFiller(_filler);
		_filler.attachTo(_canvas);
	}

	@Override
	public void setFiller(int canvas, String picture) throws CanvasException
	{
		CanvasDescriptor<BufferedImage> _canvas = getCanvasRegistry().get(canvas);
		ImageReloader _filler = new ImageReloader(picture);
		_canvas.setFiller(_filler);
		_filler.attachTo(_canvas);
	}

	private void setImageObserver(ImageObserver imageObserver)
	{
		myImageObserver = imageObserver;
	}

	/**
	 * If one has an ImageObserver interested, use this.
	 *
	 * @param imageObserver
	 *            the image observer.
	 * @return this manager.
	 */
	public BufferedImagesManager withImageObserver(ImageObserver imageObserver)
	{
		setImageObserver(imageObserver);
		return this;
	}
}
