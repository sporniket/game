/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sporniket.libre.game.canvas.CanvasCallback;
import com.sporniket.libre.game.canvas.CanvasException;

/**
 * Regenerator that reload an image.
 * 
 * The image dimension should be queried to create the managed buffered image at the right size.
 * 
 * @author dsporn
 *
 */
public class ImageReloader
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
			catch (MalformedURLException _exception)
			{
				throw new CanvasException(_exception);
			}
		}
	}

	public Dimension getImageDimension() throws CanvasException
	{
		try
		{
			BufferedImage _source = getImage();
			return new Dimension(_source.getWidth(), _source.getHeight());
		}
		catch (IOException _exception)
		{
			throw new CanvasException(_exception);
		}
	}

	public CanvasCallback<BufferedImage> getImageReloader() throws CanvasException
	{
		try
		{
			Method _regenerator = getClass().getMethod("reload", String.class, BufferedImage.class);
			return new CanvasCallback<BufferedImage>(this, _regenerator);
		}
		catch (NoSuchMethodException | SecurityException _exception)
		{
			throw new CanvasException(_exception);
		}

	}

	public void reload(String guid, BufferedImage destination) throws CanvasException
	{
		BufferedImage _source;
		try
		{
			_source = getImage();
			destination.createGraphics().drawImage(_source, 0, 0, null);
			setCache(null); // dereference so that the source image may be collected.
		}
		catch (IOException _exception)
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
		BufferedImage _image = ImageIO.read(getUrl());
		setCache(_image);
		return _image;
	}

	private void setCache(BufferedImage cache)
	{
		myCache = cache;
	}
}
