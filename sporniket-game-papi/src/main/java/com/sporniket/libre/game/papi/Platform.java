package com.sporniket.libre.game.papi;

/**Interface declaring a platform life cycle steps for hosting games.
 * 
 * <p>The {@link #checkIn(Game)} MUST occurs before {@link #run()}.
 * <p>The interface specify steps in the life cycle. How these steps are organised is implemented in {@link PlatformBase}.
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
 * @see PlatformBase
 *
 */
public interface Platform extends GameHost
{
//
//	void exit();
//
//	void init();
//
//	void update(); // clearJoysticksState() ; updateJoysticksState() ;
//
//	void run();

}