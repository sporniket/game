/**
 * 
 */
package com.sporniket.libre.game.papi;

/**
 * Class for identifying a loaded picture.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
public abstract class PictureHandler implements Comparable<PictureHandler>
{
	/**
	 * Class describing some features of the pictures that might be needed to arrange the display.
	 * 
	 * Dimension are expressed in a pixel independant unit.
	 * 
	 * @author dsporn
	 * 
	 */
	public static class Features
	{
		/**
		 * Height.
		 */
		private int myHeight;

		/**
		 * Width.
		 */
		private int myWidth;

		/**
		 * @return the height
		 */
		public int getHeight()
		{
			return myHeight;
		}

		/**
		 * @return the width
		 */
		public int getWidth()
		{
			return myWidth;
		}

		/**
		 * @param height
		 *            the height to set
		 */
		private void setHeight(int height)
		{
			myHeight = height;
		}

		/**
		 * @param width
		 *            the width to set
		 */
		private void setWidth(int width)
		{
			myWidth = width;
		}

	}

	/**
	 * Features for re-use.
	 */
	private final Features myFeatures = new Features();

	/**
	 * Identifying value
	 */
	private Integer myValue;

	public PictureHandler(int value, int width, int height)
	{
		super();
		setValue(value);
		getFeatures().setWidth(width);
		getFeatures().setHeight(height);
	}

	public int compareTo(PictureHandler o)
	{
		return getValue().compareTo(o.getValue());
	}

	/**
	 * @return the features
	 */
	public Features getFeatures()
	{
		return myFeatures;
	}

	public Integer getValue()
	{
		return myValue;
	}

	private void setValue(int value)
	{
		myValue = value;
	}

	/**
	 * Return the color of the specified pixel as an ARGB int (8 bits per pixel,
	 * <code>value = (alpha << 24) | (red << 16) | (green << 8) | blue</code>).
	 * 
	 * @param x
	 *            x coordinate in real pixels
	 * @param y
	 *            y coordinate in real pixels
	 * @return 0 if the pixel is out of bound.
	 */
	public abstract int getPixel(int x, int y);

	/**
	 * Change the features values.
	 * 
	 * @param width
	 *            the new width.
	 * @param height
	 *            the new height.
	 * @since 0-SNAPSHOT
	 */
	public void updateFeatures(int width, int height)
	{
		getFeatures().setWidth(width);
		getFeatures().setHeight(height);
	}
}
