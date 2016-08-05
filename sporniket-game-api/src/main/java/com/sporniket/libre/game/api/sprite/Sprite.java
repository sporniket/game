/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import com.sporniket.libre.game.api.types.BlocDefinition;

/**
 * A Sprite consists of a BlocDefinition and a HotSpot.
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
 * @deprecated replaced by {@link SpriteDefinition}
 */
public class Sprite implements Cloneable{
	private BlocDefinition myBloc = new BlocDefinition();
	
	private Integer myHotSpotX = 0 ;

	private Integer myHotSpotY = 0 ;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Sprite _clone = new Sprite();
		_clone.setBloc(getBloc());
		_clone.setHotSpotX(getHotSpotX());
		_clone.setHotSpotY(getHotSpotY());
		return _clone;
	}

	/**
	 * @return the bloc
	 */
	public BlocDefinition getBloc() {
		return myBloc;
	}

	/**
	 * @return the hotSpotX
	 */
	public Integer getHotSpotX() {
		return myHotSpotX;
	}

	/**
	 * @return the hotSpotY
	 */
	public Integer getHotSpotY() {
		return myHotSpotY;
	}

	/**
	 * @param bloc the bloc to set
	 */
	private void setBloc(BlocDefinition bloc) {
		myBloc = bloc;
	}

	/**
	 * @param hotSpotX the hotSpotX to set
	 */
	public void setHotSpotX(Integer hotSpotX) {
		myHotSpotX = hotSpotX;
	}

	/**
	 * @param hotSpotY the hotSpotY to set
	 */
	public void setHotSpotY(Integer hotSpotY) {
		myHotSpotY = hotSpotY;
	}
	
	
}
