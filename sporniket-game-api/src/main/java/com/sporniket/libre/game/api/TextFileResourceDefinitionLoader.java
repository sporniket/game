/**
 * 
 */
package com.sporniket.libre.game.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.SequenceBank;
import com.sporniket.libre.game.api.sprite.SequenceInstanceBank;
import com.sporniket.libre.game.api.sprite.SpriteBank;

/**
 * Base Loader for a text description of the resources.
 * 
 * The text line is processed line by line.
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
public abstract class TextFileResourceDefinitionLoader implements
		ResourceDefinitionLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.sgapi.ResourceDefinitionLoader#
	 * loadSpriteAndActorDefinitions(java.io.File,
	 * com.sporniket.libre.game.sgapi.sprite.SpriteBank,
	 * com.sporniket.libre.game.sgapi.sprite.SequenceBank,
	 * com.sporniket.libre.game.sgapi.sprite.SequenceInstanceBank,
	 * com.sporniket.libre.game.sgapi.sprite.ActorBank)
	 */
	public void loadSpriteAndActorDefinitions(File file, SpriteBank sprites,
			SequenceBank sequences, SequenceInstanceBank sequenceInstances,
			ActorBank actors) throws IOException {
		InputStream _is = new FileInputStream(file);
		loadSpriteAndActorDefinitions(_is, sprites, sequences,
				sequenceInstances, actors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.sgapi.ResourceDefinitionLoader#
	 * loadSpriteAndActorDefinitions(java.io.InputStream,
	 * com.sporniket.libre.game.sgapi.sprite.SpriteBank,
	 * com.sporniket.libre.game.sgapi.sprite.SequenceBank,
	 * com.sporniket.libre.game.sgapi.sprite.SequenceInstanceBank,
	 * com.sporniket.libre.game.sgapi.sprite.ActorBank)
	 */
	public void loadSpriteAndActorDefinitions(InputStream inputStream,
			SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors)
			throws IOException {
		InputStreamReader _isr = new InputStreamReader(inputStream);
		BufferedReader _br = new BufferedReader(_isr);
		LineNumberReader _lnr = new LineNumberReader(_br);
		doLoadSpriteAndActorDefinitions(_lnr, sprites, sequences,
				sequenceInstances, actors);
	}

	/**
	 * Read the text file line by line.
	 * @param data
	 * @param sprites
	 * @param sequences
	 * @param sequenceInstances
	 * @param actors
	 * @throws IOException
	 */
	private void doLoadSpriteAndActorDefinitions(LineNumberReader data,
			SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors)
			throws IOException {
		for (String _currentLine = data.readLine() ; null != _currentLine ; _currentLine = data.readLine()) {
			if (_currentLine.trim().length() == 0)
			{
				continue ;
			}
			readLineForSpriteAndActorDefinitions(_currentLine, sprites,
					sequences, sequenceInstances, actors);
		}
	}

	/**
	 * Read a line of data.
	 * 
	 * Subclasses MUST implements this.
	 * 
	 * @param data
	 * @param sprites
	 * @param sequences
	 * @param sequenceInstances
	 * @param actors
	 */
	protected abstract void readLineForSpriteAndActorDefinitions(String data,
			SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors);
}
