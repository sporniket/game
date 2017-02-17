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
import com.sporniket.libre.game.canvas.CanvasDescriptor;
import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.canvas.CanvasFiller;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletContext;

/**
 * Regenerator that reload an image.
 *
 * The image dimension should be queried to create the managed buffered image at the right size.
 *
 * @author dsporn
 *
 */
public class ImageReloader implements CanvasFiller<BufferedImage, CanvasGameletContext<BufferedImage>>
{

	private static final String PROTOCOL__CLASSPATH = "classpath:";

	private BufferedImage myCache;

	private int myPreferredHeight;

	private int myPreferredWidth;

	private CanvasDescriptor<BufferedImage> myTarget;

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
	}

	@Override
	public void attachTo(CanvasDescriptor<BufferedImage> canvas)
	{
		setTarget(canvas);
	}

	@Override
	public void fill(CanvasGameletContext<BufferedImage> context) throws CanvasException
	{
		if (null == getTarget())
		{
			throw new IllegalStateException("Filler not attached to a canvas.");
		}
		if (getTarget().isDisposed())
		{
			throw new IllegalStateException("The canvas has been disposed, recreate one first.");
		}
		BufferedImage _source;
		try
		{
			_source = getImage();
			getTarget().getCanvas().createGraphics().drawImage(_source, 0, 0, null);
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

	@Deprecated
	public Dimension getImageDimension() throws CanvasException
	{
		return new Dimension(getPreferredWidth(), getPreferredHeight());
	}

	@Deprecated
	public CanvasCallback<BufferedImage> getImageReloader() throws CanvasException
	{
		try
		{
			final Method _regenerator = getClass().getMethod("reload", String.class, BufferedImage.class);
			return new CanvasCallback<>(this, _regenerator);
		}
		catch (NoSuchMethodException | SecurityException _exception)
		{
			throw new CanvasException(_exception);
		}

	}

	@Override
	public int getPreferredHeight()
	{
		return myPreferredHeight;
	}

	@Override
	public int getPreferredWidth()
	{
		return myPreferredWidth;
	}

	private CanvasDescriptor<BufferedImage> getTarget()
	{
		return myTarget;
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

	@Deprecated
	public void reload(String guid, BufferedImage destination) throws CanvasException
	{
		BufferedImage _source;
		try
		{
			_source = getImage();
			destination.createGraphics().drawImage(_source, 0, 0, null);
			setCache(null); // dereference so that the source image may be collected.
		}
		catch (final IOException _exception)
		{
			throw new CanvasException(_exception);
		}
	}

	private void setCache(BufferedImage cache)
	{
		myCache = cache;
	}

	private void setPreferredHeight(int preferredHeight)
	{
		myPreferredHeight = preferredHeight;
	}

	private void setPreferredWidth(int preferredWidth)
	{
		myPreferredWidth = preferredWidth;
	}

	private void setTarget(CanvasDescriptor<BufferedImage> target)
	{
		myTarget = target;
	}
}
