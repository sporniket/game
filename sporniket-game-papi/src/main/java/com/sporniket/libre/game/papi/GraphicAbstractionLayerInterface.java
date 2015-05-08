/**
 * 
 */
package com.sporniket.libre.game.papi;

import java.io.File;
import java.io.IOException;

import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.SpriteBank;
import com.sporniket.libre.game.api.types.CopyMode;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Interface for an abstraction layer for graphic operations.
 * 
 * Dimension are expressed in a pixel independant unit.
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
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
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
public interface GraphicAbstractionLayerInterface
{
	/**
	 * Create a file and save the current screen (lastly commited using {@link #commitDisplay()}).
	 * 
	 * @return a {@link File} to the captured screen
	 * @throws IOException
	 * @since 0-SNAPSHOT
	 */
	File captureScreen() throws IOException;

	/**
	 * Change the data file that is used by the given picture.
	 * 
	 * <ul>
	 * <li>This mechanism allow to save memory, as there is only one picture instead of two.</li>
	 * <li>This mechanism allow to write games that want to use the same {@link PictureHandler} but having different look according
	 * the situation (e.g. a quizz or a tile-based rendering engine).</li>
	 * </ul>
	 * 
	 * @param picture
	 *            the {@link PictureHandler} that will be changed.
	 * @param ressource
	 *            location of the resource, depends of the implementation. Usually a path to a file.
	 * @param useBlackAsTransparentColor
	 * @return the modified {@link PictureHandler}.
	 * @throws IOException
	 * @since 0-SNAPSHOT
	 * @see #loadPicture(String, boolean)
	 */
	PictureHandler changePictureSource(PictureHandler picture, String ressource, boolean useBlackAsTransparentColor)
			throws IOException;

	/**
	 * Display the screen that has been build until now.
	 */
	void commitDisplay();

	/**
	 * Copy a part of a picture to the screen (uses {@link CopyMode#NORMAL} as mode.).
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dx
	 * @param dy
	 */
	void copyPicture(PictureHandler source, int x, int y, int width, int height, int dx, int dy);

	/**
	 * Copy a part of a picture to the screen.
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dx
	 * @param dy
	 * @param mode
	 */
	void copyPicture(PictureHandler source, int x, int y, int width, int height, int dx, int dy, CopyMode mode);

	/**
	 * Copy a part of a picture to another one (uses {@link CopyMode#NORMAL} as mode.).
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param destination
	 * @param dx
	 * @param dy
	 */
	void copyPicture(PictureHandler source, int x, int y, int width, int height, PictureHandler destination, int dx, int dy);

	/**
	 * Copy a part of a picture to another one.
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param destination
	 * @param dx
	 * @param dy
	 * @param mode
	 */
	void copyPicture(PictureHandler source, int x, int y, int width, int height, PictureHandler destination, int dx, int dy,
			CopyMode mode);

	/**
	 * Create an offscreen picture of the same size than the screen.
	 * 
	 * @return
	 */
	PictureHandler createOffscreenPicture();

	/**
	 * Create an offscreen picture of the specified size.
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	PictureHandler createOffscreenPicture(int width, int height);

	/**
	 * Displays active actors from the bank.
	 * 
	 * @param actorBank
	 * @param spriteBank
	 * @param picture
	 */
	void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture);

	/**
	 * Displays active actors from the bank to the specified picture.
	 * 
	 * @param actorBank
	 * @param spriteBank
	 * @param picture
	 */
	void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture, PictureHandler destination);

	/**
	 * Display the specified picture at a given position (uses {@link CopyMode#NORMAL} as mode.).
	 * 
	 * @param picture
	 * @param x
	 * @param y
	 */
	void displayPicture(PictureHandler picture, int x, int y);

	/**
	 * Display the specified picture.
	 * 
	 * @param picture
	 * @param x
	 * @param y
	 * @param mode
	 */
	void displayPicture(PictureHandler picture, int x, int y, CopyMode mode);

	/**
	 * Return the features of the screen, so that the game can adjust its rendering.
	 * 
	 * @return a {@link ScreenFeatureSet}
	 */
	ScreenFeatureSet getScreenFeatures();

	/**
	 * Load a picture.
	 * 
	 * @param ressource
	 *            location of the resource, depends of the implementation. Usually a path to a file.
	 * @param useBlackAsTransparentColor
	 * @return
	 * @throws IOException
	 */
	PictureHandler loadPicture(String ressource, boolean useBlackAsTransparentColor) throws IOException;
}
