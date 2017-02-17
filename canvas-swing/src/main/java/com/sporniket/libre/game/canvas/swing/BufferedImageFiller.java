package com.sporniket.libre.game.canvas.swing;

import java.awt.image.BufferedImage;

import com.sporniket.libre.game.canvas.CanvasDescriptor;
import com.sporniket.libre.game.canvas.CanvasFiller;

public abstract class BufferedImageFiller implements CanvasFiller<BufferedImage>
{

	private int myPreferredHeight;

	private int myPreferredWidth;

	private CanvasDescriptor<BufferedImage> myTarget;

	public BufferedImageFiller()
	{
		super();
	}

	protected void assertThatCanvasIsAccessible()
	{
		if (null == getTarget())
		{
			throw new IllegalStateException("Filler not attached to a canvas.");
		}
		if (getTarget().isDisposed())
		{
			throw new IllegalStateException("The canvas has been disposed, recreate one first.");
		}

	}

	@Override
	public void attachTo(CanvasDescriptor<BufferedImage> canvas)
	{
		setTarget(canvas);
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

	protected CanvasDescriptor<BufferedImage> getTarget()
	{
		return myTarget;
	}

	protected void setPreferredHeight(int preferredHeight)
	{
		myPreferredHeight = preferredHeight;
	}

	protected void setPreferredWidth(int preferredWidth)
	{
		myPreferredWidth = preferredWidth;
	}

	private void setTarget(CanvasDescriptor<BufferedImage> target)
	{
		myTarget = target;
	}

}