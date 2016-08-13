/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import com.sporniket.libre.game.api.types.CopyMode;
import com.sporniket.libre.game.api.types.Position;

/**Actor instance, something displayed on screen.
 * An Actor has the following properties :
 * 
 * <dl>
 * 	<dt>Position</dt>
 * 	<dd>2D vector representing.</dd>
 * 	
 * 	<dt>Active</dt>
 * 	<dd>Flag to indicate that the actor should be displayed or not.</dd>
 * 	
 * 	<dt>Sequence</dt>
 * 	<dd>SequenceInstance associated to the Actor.</dd>
 * 	
 * 	<dt>CopyMode</dt>
 * 	<dd>Directive for displaying the graphics, normally or partially.</dd>
 * 	
 * </dl>
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
 * @deprecated lack of abstraction, too limited
 */
public class Actor implements Cloneable {
	public static final CopyMode DEFAULT__COPY_MODE = CopyMode.NORMAL;
	private boolean myActive = false ;
	/**
	 * Copy mode for the display.
	 */
	private CopyMode myCopyMode = DEFAULT__COPY_MODE ;
	private Position myPosition = new Position();

	private SequenceInstance mySequence = null ;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	
	protected Object clone() throws CloneNotSupportedException {
		Actor _clone = new Actor();
		_clone.setActive(isActive());
		_clone.setSequence(getSequence());
		return _clone;
	}
	/**
	 * @return the copyMode
	 */
	public CopyMode getCopyMode()
	{
		return myCopyMode;
	}
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return myPosition;
	}
	/**
	 * @return the sequence
	 */
	public SequenceInstance getSequence() {
		return mySequence;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return myActive;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		myActive = active;
	}
	/**
	 * @param copyMode the copyMode to set
	 */
	public void setCopyMode(CopyMode copyMode)
	{
		myCopyMode = copyMode;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(SequenceInstance sequence) {
		mySequence = sequence;
	}
	
	/**
	 * Utility method to create an actor using a specified sequence.
	 * @param sequence
	 * @return
	 */
	public static Actor createFromSequence(Sequence sequence)
	{
		SequenceInstance _instance = new SequenceInstance();
		_instance.setSequence(sequence);
		//_instance.reset(); //so that the currentSprite will not be out of range
		Actor _actor = new Actor();
		_actor.setSequence(_instance);
		return _actor ;
	}
}
