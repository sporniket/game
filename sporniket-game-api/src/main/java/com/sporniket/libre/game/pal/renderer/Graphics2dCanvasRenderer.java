/**
 * 
 */
package com.sporniket.libre.game.pal.renderer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.*;
import java.awt.image.*;

/**
 * Renderer working on a standard graphics 2d Canvas
 * 
 * @author dsporn
 *
 */
public class Graphics2dCanvasRenderer
{
	private static final class Blitter
	{
		/**
		 * The generic copy operation.
		 * 
		 * @param from
		 *            the source image.
		 * @param xFrom
		 *            x source.
		 * @param yFrom
		 *            y source.
		 * @param w
		 *            w source.
		 * @param h
		 *            h source.
		 * @param to
		 *            destination canvas.
		 * @param xTo
		 *            x destination.
		 * @param yTo
		 *            y destination.
		 * @param observer
		 *            observer that would need to update a display.
		 */
		public void copyBloc(Image from, int xFrom, int yFrom, int w, int h, Graphics2D to, int xTo, int yTo, ImageObserver observer)
		{
			int xf2 = xFrom + w;
			int yf2 = yFrom + h;
			int xt2 = xTo + w;
			int yt2 = yTo + h;
			to.drawImage(from, xFrom, yFrom, xf2, yf2, xTo, yTo, xt2, yt2, observer);
		}
	}

	/**
	 * 
	 */
	public Graphics2dCanvasRenderer()
	{
		// TODO Auto-generated constructor stub
	}

}
