/**
 * 
 */
package com.sporniket.libre.game.papi;

import java.io.File;


/**
 * Interface for "sending a message", i.e. the user share something with friends, or the game developper communicate to the user.
 * 
 * The implementation will basically call an external program (platform dependant) that can do the task.
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
public interface MessageSender
{
	/**
	 * Open a web page (e.g. an application store), usually using a web browser.
	 * 
	 * @param url
	 *            the location of the page.
	 * @throws MessageSenderException
	 */
	void openWebPage(String url) throws MessageSenderException;

	/**
	 * Send a message to the user friends, using Social Applications (Facebook, Twitter, regular e-mail client, etc...).
	 * 
	 * @param subject
	 * @param message
	 * @throws MessageSenderException
	 */
	void shareMessage(String subject, String message) throws MessageSenderException;

	/**
	 * Send a message to the user friends with an attached file, usually a picture, using Social Applications (Facebook, Twitter, regular e-mail client, etc...).
	 * 
	 * @param subject
	 * @param message
	 * @param attachement
	 * @throws MessageSenderException
	 */
	void shareMessage(String subject, String message, File attachement) throws MessageSenderException;
}
