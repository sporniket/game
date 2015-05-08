/**
 * 
 */
package com.sporniket.libre.game.api.types;

/**
 * Definition of a rectangular bloc of a picture.
 *
 * A bloc has the following properties :
 * <dl>
 * <dt>left</dd>
 * <dt>top</dd>
 * <dt>width</dd>
 * <dt>height</dd>
 * </dl>
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * api</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 *
 */
public class BlocDefinition implements Cloneable {
	private int myHeight = 0 ;
	private int myLeft = 0 ;
	private int myTop = 0 ;
	private int myWidth = 0 ;
	/**
	 * @return the height
	 */
	public int getHeight() {
		return myHeight;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		myHeight = height;
	}
	/**
	 * @return the left
	 */
	public int getLeft() {
		return myLeft;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		myLeft = left;
	}
	/**
	 * @return the top
	 */
	public int getTop() {
		return myTop;
	}
	/**
	 * @param top the top to set
	 */
	public void setTop(int top) {
		myTop = top;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return myWidth;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		myWidth = width;
	}
}
