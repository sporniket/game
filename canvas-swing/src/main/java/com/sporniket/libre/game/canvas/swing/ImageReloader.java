/**
 *
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sporniket.libre.game.canvas.CanvasException;

/**
 * Filler that copy an image into the canvas.
 *
 * The image dimension should be queried to create the managed buffered image at the right size.
 *
 * @author dsporn
 *
 */
public class ImageReloader extends BufferedImageFiller
{

	private static final String PROTOCOL__CLASSPATH = "classpath:";

	private BufferedImage myCache;

	private final URL myUrl;

	public ImageReloader(String url) throws CanvasException
	{
		if (url.startsWith(PROTOCOL__CLASSPATH))
		{
			myUrl = getClass().getClassLoader().getResource(url.substring(PROTOCOL__CLASSPATH.length()));
		}
		else
		{
			try
			{
				myUrl = new URL(url);
			}
			catch (final MalformedURLException _exception)
			{
				throw new CanvasException(_exception);
			}
		}
		try
		{
			prefetchImage();
		}
		catch (IOException _exception)
		{
			throw new CanvasException(_exception);
		}
	}

	@Override
	public void fill() throws CanvasException
	{
		assertThatCanvasIsAccessible();
		BufferedImage _source;
		try
		{
			_source = getImage();
			final Graphics2D _g2 = getTarget().getCanvas().createGraphics();
			_g2.setComposite(AlphaComposite.SrcOver);
			_g2.drawImage(_source, 0, 0, null);
			setCache(null); // dereference so that the source image may be collected.
		}
		catch (final IOException _exception)
		{
			throw new CanvasException(_exception);
		}

	}

	private BufferedImage getCache()
	{
		return myCache;
	}

	private BufferedImage getImage() throws IOException
	{
		return (null == getCache()) ? prefetchImage() : getCache();
	}

	private URL getUrl()
	{
		return myUrl;
	}

	private BufferedImage prefetchImage() throws IOException
	{
		final BufferedImage _image = ImageIO.read(getUrl());
		setCache(_image);
		setPreferredHeight(_image.getHeight());
		setPreferredWidth(_image.getWidth());
		return _image;
	}

	private void setCache(BufferedImage cache)
	{
		myCache = cache;
	}
}
