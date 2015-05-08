/**
 * 
 */
package com.sporniket.libre.game.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.SequenceBank;
import com.sporniket.libre.game.api.sprite.SequenceInstanceBank;
import com.sporniket.libre.game.api.sprite.SpriteBank;

/**
 * Description of a class for loading a resource (sprite definition, etc...).
 * 
 * It can load data from a file or more generally from an inputStream.
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
public interface ResourceDefinitionLoader
{
	/**
	 * Load into given bank resource definitions contained by the specified string.
	 * 
	 * The underlying implementation might use a ResourceDefinitionLocation to find the corresponding InputStream
	 * 
	 * @param file
	 * @param sprites
	 * @param sequences
	 * @param sequenceInstances
	 * @param actors
	 * @throws IOException
	 */
	public void loadSpriteAndActorDefinitions(String file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException;

	/**
	 * Load into given bank resource definitions contained by the specified file.
	 * 
	 * @param file
	 * @param sprites
	 * @param sequences
	 * @param sequenceInstances
	 * @param actors
	 * @throws IOException
	 */
	public void loadSpriteAndActorDefinitions(File file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException;

	/**
	 * Load into given bank resource definitions contained by the specified inputStream.
	 * 
	 * @param file
	 * @param sprites
	 * @param sequences
	 * @param sequenceInstances
	 * @param actors
	 * @throws IOException
	 */
	public void loadSpriteAndActorDefinitions(InputStream inputStream, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException;
}
