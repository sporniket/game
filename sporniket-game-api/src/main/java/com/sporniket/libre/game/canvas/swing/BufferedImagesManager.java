/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.sporniket.libre.game.canvas.Box;
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
	public void copy(int canvasIdFrom, Box boxSpecs, int canvasIdTo, Point position)
	{
		Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics();
		_to.setComposite(AlphaComposite.SrcOver);

		copyToGraphics(canvasIdFrom, boxSpecs, _to, position, getImageObserver());
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
		Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics();
		_to.setComposite(AlphaComposite.Src);

		copyToGraphics(canvasIdFrom, boxSpecs, _to, position, getImageObserver());
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

	@Override
	protected BufferedImage createCanvasData(int width, int height)
	{
		BufferedImage _image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		return _image;
	}

	private ImageObserver getImageObserver()
	{
		return myImageObserver;
	}

	private void setImageObserver(ImageObserver imageObserver)
	{
		myImageObserver = imageObserver;
	}

	void copyToGraphics(int canvasIdFrom, Box boxSpecs, Graphics2D to, Point position, ImageObserver observer)
	{
		BufferedImage _from = getCanvasRegistry().get(canvasIdFrom).getCanvas();
		Box _toBox = new Box().withX(position.getX()).withY(position.getY()).withWidth(boxSpecs.getWidth())
				.withHeight(boxSpecs.getHeight());

		to.drawImage(_from, _toBox.getX(), _toBox.getY(), _toBox.getX2(), _toBox.getY2(), boxSpecs.getX(), boxSpecs.getY(),
				boxSpecs.getX2(), boxSpecs.getY2(), observer);
	}
}
