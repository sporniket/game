/**
 * 
 */
package com.sporniket.libre.game.api.types;

import com.sporniket.libre.game.api.types.canvas.Box;

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
 * @deprecated replaced by {@link Box}
 *
 */
public class BlocDefinition extends Box implements Cloneable{
	/**
	 * @return the left
	 * @deprecated
	 */
	public int getLeft() {
		return getX();
	}
	/**
	 * @param left the left to set
	 * @deprecated
	 */
	public void setLeft(int left) {
		setX(left);
	}
	/**
	 * @return the top
	 * @deprecated
	 */
	public int getTop() {
		return getY();
	}
	/**
	 * @param top the top to set
	 * @deprecated
	 */
	public void setTop(int top) {
		setY(top);
	}
}
