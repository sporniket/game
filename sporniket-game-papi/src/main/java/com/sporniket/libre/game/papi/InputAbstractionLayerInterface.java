package com.sporniket.libre.game.papi;

import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.gamelet.input.KeyboardStateProvider;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;


/**
 * Layer that provide access to the input peripherals.
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
public interface InputAbstractionLayerInterface
{
	/**
	 * Tells whether {@link GameControllerStateProvider} may be used.
	 * @return
	 */
	boolean isGameControllerAware();
	
	/**
	 * Tells whether {@link KeyboardStateProvider} may be used.
	 * @return
	 */
	boolean isKeyboardAware();
	
	/**
	 * Tells whether {@link PointerStateProvider} may be used.
	 * @return
	 */
	boolean isPointerAware();
	
	/**
	 * update all the input states.
	 */
	void update();
	
	/**
	 * @return a provider of game controller states.
	 * @throws UnsupportedOperationException if it cannot provide.
	 */
	GameControllerStateProvider getGameControllerStateProvider() throws UnsupportedOperationException ;
	
	/**
	 * @return a provider of pointer states.
	 * @throws UnsupportedOperationException if it cannot provide.
	 */
	PointerStateProvider getPointerStateProvider() throws UnsupportedOperationException ;
	
	/**
	 * @return a provider of keyboard states.
	 * @throws UnsupportedOperationException if it cannot provide.
	 */
	KeyboardStateProvider getKeyboardStateProvider() throws UnsupportedOperationException ;
}
