/**
 *
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
	private static final Color DEFAULT__COLOR = Color.black;

	private static final Font DEFAULT__FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

	private Color myColor = DEFAULT__COLOR;

	private Color myColorOpaque = DEFAULT__COLOR;

	private Font myFont = DEFAULT__FONT;

	private ImageObserver myImageObserver = this;

	private boolean myTransparentMode;

	public BufferedImagesManager(int screenWidth, int screenHeight)
	{
		super(screenWidth, screenHeight);
	}

	@Override
	public void clear(Box boxSpecs, int canvasIdTo, Point position)
	{
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

	@Override
	public void disableTransparentMode()
	{
		myTransparentMode = false;
	}

	@Override
	public void drawBox(int canvasId, Box box)
	{
		final Graphics2D _to = prepareGraphicsForDrawing(canvasId);

		_to.drawRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
	}

	@Override
	public void drawFilledBox(int canvasId, Box box)
	{
		final Graphics2D _to = prepareGraphicsForDrawing(canvasId);

		_to.fillRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
	}

	@Override
	public void drawLine(int canvasId, Point from, Point to)
	{
		final Graphics2D _to = prepareGraphicsForDrawing(canvasId);

		_to.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
	}

	@Override
	public void drawText(int canvasId, String text, Point at)
	{
		final Graphics2D _to = prepareGraphicsForDrawing(canvasId);

		final FontRenderContext _frc = _to.getFontRenderContext();
		final LineMetrics _lineMetrics = getFont().getLineMetrics(text, _frc);
		final int _ascent = new BigDecimal(_lineMetrics.getAscent()).setScale(0, RoundingMode.CEILING).intValue();
		_to.drawString(text, at.getX(), at.getY() + _ascent);

	}

	@Override
	public void enableTransparentMode()
	{
		myTransparentMode = true;
	}

	@Override
	public Color getColor()
	{
		return isTransparentModeEnabled() ? myColor : myColorOpaque;
	}

	@Override
	public Font getFont()
	{
		return myFont;
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
	public boolean isTransparentModeEnabled()
	{
		return myTransparentMode;
	}

	/**
	 * Get a fully setup graphics for the target canvas.
	 *
	 * The color, font and composite mode are set according to {@link #getColor()}, {@link #getFont()} and
	 * {@link #isTransparentModeEnabled()}.
	 *
	 * @param canvasId
	 *            the target canvas.
	 * @return the {@link Graphics2D} to draw into.
	 */
	private Graphics2D prepareGraphicsForDrawing(int canvasId)
	{
		final Graphics2D _to = getCanvasRegistry().get(canvasId).getCanvas().createGraphics();
		_to.setComposite(isTransparentModeEnabled() ? AlphaComposite.SrcOver : AlphaComposite.Src);
		_to.setColor(getColor());
		_to.setFont(getFont());
		return _to;
	}

	@Override
	public void replace(int canvasIdFrom, Box boxSpecs, int canvasIdTo, Point position)
	{
		final Graphics2D _to = getCanvasRegistry().get(canvasIdTo).getCanvas().createGraphics();
		_to.setComposite(AlphaComposite.Src);

		copyToGraphics(canvasIdFrom, boxSpecs, _to, position, getImageObserver());
	}

	@Override
	public void setColor(Color color)
	{
		myColor = color;
		if (null == myColor)
		{
			myColor = DEFAULT__COLOR;
		}
		myColorOpaque = (myColor.getAlpha() < 255) ? new Color(myColor.getRGB()) : myColor;
	}

	@Override
	public void setFiller(int canvas, Color color)
	{
		final CanvasDescriptor<BufferedImage> _canvas = getCanvasRegistry().get(canvas);
		final ColorFiller _filler = new ColorFiller(color);
		_canvas.setFiller(_filler);
		_filler.attachTo(_canvas);
	}

	@Override
	public void setFiller(int canvas, String picture) throws CanvasException
	{
		final CanvasDescriptor<BufferedImage> _canvas = getCanvasRegistry().get(canvas);
		final ImageReloader _filler = new ImageReloader(picture);
		_canvas.setFiller(_filler);
		_filler.attachTo(_canvas);
	}

	@Override
	public void setFont(Font font)
	{
		myFont = font;
		if (null == myFont)
		{
			myFont = DEFAULT__FONT;
		}
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
