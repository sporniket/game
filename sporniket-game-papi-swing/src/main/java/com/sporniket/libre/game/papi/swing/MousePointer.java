/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import com.sporniket.libre.game.gamelet.input.Pointer;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;
import com.sporniket.libre.game.gamelet.input.Pointer.State;

/**
 * Implementation of PointerStateProvider returning mouse position.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Swing</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Swing</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 *
 */
public class MousePointer extends MouseInputAdapter implements PointerStateProvider
{

	private Pointer[] myPointers = new Pointer[1];
	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.sgpapi.InputAbstractionLayerInterface.PointerStateProvider#getPointerCount()
	 */
	
	public int getPointerCount()
	{
		return 1;
	}

	
	public Pointer[] getStates()
	{
		try
		{
			Pointer[] result = {(Pointer) (myPointers[0].clone())};
			return result;
		}
		catch (CloneNotSupportedException _exception)
		{
			_exception.printStackTrace();
			Pointer[] result = {new Pointer()};
			return result;
			
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	
	public void mouseDragged(MouseEvent e)
	{
		mousePressed(e);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseMoved(MouseEvent)
	 */
	
	public void mouseMoved(MouseEvent e)
	{
		mouseReleased(e);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mousePressed(MouseEvent)
	 */
	
	public void mousePressed(MouseEvent e)
	{
		myPointers[0].setState(State.PRESSED);
		myPointers[0].setX(e.getX());
		myPointers[0].setY(e.getY());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(MouseEvent)
	 */
	
	public void mouseReleased(MouseEvent e)
	{
		myPointers[0].setState(State.RELEASED);
		myPointers[0].setX(e.getX());
		myPointers[0].setY(e.getY());
	}
	
	
	

}
