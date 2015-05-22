/**
 * 
 */
package com.sporniket.libre.game.api.ui;

import java.util.LinkedList;
import java.util.List;

import com.sporniket.libre.game.api.collision.CollisionDetector;
import com.sporniket.libre.game.api.types.xy.geometry.Point;

/**
 * Base class for creating interactive area.
 * 
 * An interactive area may have children, to built a structured area, so that it is possible to express the relationship of some areas functionnaly and/or visually.
 * 
 * This approach assumes that other things like redrawing widget is done elsewhere.
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
public class UiArea<Value>
{
	/**
	 * Detector that will determine that a Position is inside the area.
	 */
	private CollisionDetector myDetector ;
	
	/**
	 * Value associated to the area, may be null.
	 */
	private Value myValue ;
	
	/**
	 * Children registry.
	 */
	private List<UiArea<Value>> myChildren = new LinkedList<UiArea<Value>>();

	/**
	 * @return the value
	 */
	public Value getValue()
	{
		return myValue;
	}

	/**
	 * @param value
	 * @param detector
	 */
	public UiArea(Value value, CollisionDetector detector)
	{
		super();
		myValue = value;
		myDetector = detector;
	}
	
	/**
	 * Add a child area.
	 * @param child
	 * @return
	 */
	public UiArea<Value> addChild(UiArea<Value> child){
		myChildren.add(child);
		return this ;
	}
	
	/**
	 * Find the interactive area under the specified position, and return the associated value.
	 * 
	 * Children supersedes the value of the parent area.
	 * 
	 * @param position
	 * @return a value, might be null (no area)
	 */
	public Value findValue(Point position)
	{
		Value _result = null;
		if (myDetector.isHit(position))
		{
			_result = getValue() ;
			Value _resultChild ;
			for (UiArea<Value> _child : myChildren)
			{
				_resultChild = _child.findValue(position);
				if (null != _resultChild)
				{
					_result = _resultChild;
					break ;
				}
			}
		}
		return _result ;
	}
	
}
