/**
 * 
 */
package com.sporniket.libre.game.papi;

/**
 * Abstraction layer to contain picture data (offscreen data, picture file data, etc...).
 * 
 * The physical data are contained in a "RawData" type that depends on the platform.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
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
public abstract class Surface<RawData> {
	/**
	 * Raw data suitable for the platform.
	 */
	private RawData myData = null ;
	
	/**
	 * Height in pixels.
	 */
	private int myHeight = 0 ;
	
	/**
	 * Width in pixels.
	 */
	private int myWidth = 0 ;

	/**
	 * @return the data
	 */
	public RawData getData() {
		return myData;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return myHeight;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return myWidth;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(RawData data) {
		myData = data;
		
		updateDimension() ;
	}

	/**
	 * @param height the height to set
	 */
	protected void setHeight(int height) {
		myHeight = height;
	}

	/**
	 * @param width the width to set
	 */
	protected void setWidth(int width) {
		myWidth = width;
	}

	/**
	 * retrieve the surface dimension from the raw data.
	 */
	protected abstract void updateDimension();
	
}
