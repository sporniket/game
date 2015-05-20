/**
 * 
 */
package com.sporniket.libre.game.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.game.api.sprite.Actor;
import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.Sequence;
import com.sporniket.libre.game.api.sprite.SequenceBank;
import com.sporniket.libre.game.api.sprite.SequenceInstance;
import com.sporniket.libre.game.api.sprite.SequenceInstanceBank;
import com.sporniket.libre.game.api.sprite.SequenceItem;
import com.sporniket.libre.game.api.sprite.SequenceItem.Type;
import com.sporniket.libre.game.api.sprite.Sprite;
import com.sporniket.libre.game.api.sprite.SpriteBank;

/**
 * Loader for a simple text definition of resources.
 * 
 * <pre>
 * sprite left top width height hotSpotX hotSpotY
 * 
 * 
 * sequence item_number
 * type1 value1 duration1
 * ...
 * typeN valueN durationN
 * 
 * actor sequence_id
 * </pre>
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
public class SimpleTextFileResourceDefinitionLoaderV00 extends TextFileResourceDefinitionLoader
{

	/**
	 * Enumerate all known keyword.
	 * <p>
	 * &copy; Copyright 2010-2013 David Sporn
	 * </p>
	 * <hr>
	 * 
	 * <p>
	 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
	 * 
	 * <p>
	 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of
	 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
	 * your option) any later version.
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
	private static enum Keyword
	{
		ACTOR("actor"),
		SEQUENCE("sequence"),
		SPRITE("sprite");
		/**
		 * Registry of value/Keyword.
		 */
		private static final Map<String, Keyword> CACHE_VALUES = new HashMap<String, SimpleTextFileResourceDefinitionLoaderV00.Keyword>();

		static
		{
			for (int _i = 0; _i < Keyword.values().length; _i++)
			{
				Keyword _keyword = Keyword.values()[_i];
				CACHE_VALUES.put(_keyword.toString(), _keyword);
			}
		}

		/**
		 * Parse the value to find the corresponding keyword instance.
		 * 
		 * @param value
		 *            the value to recognise.
		 * @return a {@link Keyword}.
		 */
		public static Keyword parse(String value)
		{
			if (CACHE_VALUES.containsKey(value))
			{
				return CACHE_VALUES.get(value);
			}
			throw new IllegalArgumentException("[" + value + "] is not a keyword.");
		}

		private String myValue;

		private Keyword(String value)
		{
			myValue = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */

		public String toString()
		{
			return myValue;
		}
	}

	/**
	 * separator to tokenize the statements.
	 */
	//FIXME use a regexp matching any sequence of space.
	private static final String SEPARATOR = " ";

	/**
	 * Sequence being build.
	 */
	private Sequence myCurrentSequence = null;

	/**
	 * Flag to know we are reading a sequence.
	 */
	private boolean myInsideSequence = false;

	/**
	 * Locator to use the loader specifying the resource definition by a String.
	 */
	private ResourceDefinitionLocator myResourceDefinitionLocator;

	/**
	 * Number of lines left, of sequence definition.
	 */
	private int mySequenceLineToRead = 0;

	/**
	 * @param sequenceLineToRead
	 *            the sequenceLineToRead to set
	 */
	private void decSequenceLineToRead()
	{
		if (0 < mySequenceLineToRead)
		{
			--mySequenceLineToRead;
		}
		else
		{
			if (0 != mySequenceLineToRead)
			{
				mySequenceLineToRead = 0;
			}
		}
		if (0 == mySequenceLineToRead)
		{
			setInsideSequence(false);
		}
	}

	/**
	 * @return the currentSequence
	 */
	private Sequence getCurrentSequence()
	{
		return myCurrentSequence;
	}

	/**
	 * @return the myResourceDefinitionLocator
	 */
	public ResourceDefinitionLocator getResourceDefinitionLocator()
	{
		return myResourceDefinitionLocator;
	}

	/**
	 * @return the sequenceLineToRead
	 */
	private int getSequenceLineToRead()
	{
		return mySequenceLineToRead;
	}

	/**
	 * @return the insideSequence
	 */
	private boolean isInsideSequence()
	{
		return myInsideSequence;
	}

	public void loadSpriteAndActorDefinitions(String file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		if (null == getResourceDefinitionLocator())
		{
			throw new IOException("no resource definition locator provided...");
		}
		loadSpriteAndActorDefinitions(getResourceDefinitionLocator().getInputStream(file), sprites, sequences, sequenceInstances,
				actors);
	}

	private void readActor(String[] tokens, SequenceBank sequences, SequenceInstanceBank sequenceInstances, ActorBank actors)
	{
		SequenceInstance _instance = new SequenceInstance();
		_instance.setSequence(sequences.get(Integer.parseInt(tokens[1])));
		sequenceInstances.add(_instance);

		Actor _actor = new Actor();
		_actor.setSequence(_instance);
		actors.add(_actor);

	}

	protected void readLineForSpriteAndActorDefinitions(String data, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors)
	{
		String[] _tokens = tokenize(data);
		if (isInsideSequence())
		{
			// read sequence frame
			readSequenceItem(_tokens);

			// test if sequence is finished for the commit
			if (!isInsideSequence())
			{
				// commit sequence
				sequences.add(getCurrentSequence());
				setCurrentSequence(null);
			}
		}
		else
		{
			switch (Keyword.parse(_tokens[0]))
			{
				case SPRITE:
					readSprite(_tokens, sprites);
					break;
				case ACTOR:
					readActor(_tokens, sequences, sequenceInstances, actors);
					break;
				case SEQUENCE:
					readSequence(_tokens, sequences);
					break;
				default:
					throw new IllegalStateException("[" + _tokens[0] + "] is not a supported keyword.");
			}
		}
	}

	/**
	 * Convert a line into an array of tokens (non empty).
	 * 
	 * @param data line to tokenize.
	 * @return tokenized line.
	 */
	private String[] tokenize(String data)
	{
		final String[] _raw = data.split(SEPARATOR);
		List<String> _tokens = new ArrayList<String>(_raw.length);
		for (String _rawToken : _raw)
		{
			// keep only not empty tokens.
			final String _trimmedToken = _rawToken.trim();
			if (_trimmedToken.length() > 0)
			{
				_tokens.add(_trimmedToken);
			}
		}
		return _tokens.toArray(new String[_tokens.size()]);
	}

	/**
	 * Start a sequence.
	 * 
	 * The line contains the number of sequence items
	 * 
	 * @param tokens
	 * @param sequences
	 */
	private void readSequence(String[] tokens, SequenceBank sequences)
	{
		setSequenceLineToRead(Integer.parseInt(tokens[1]));
		setCurrentSequence(new Sequence());
	}

	private void readSequenceItem(String[] tokens)
	{
		SequenceItem _item = new SequenceItem();
		_item.setType(Type.parse(tokens[0]));
		_item.setValue(Integer.parseInt(tokens[1]));
		_item.setDuration(Long.parseLong(tokens[2]));
		getCurrentSequence().add(_item);
		decSequenceLineToRead();
	}

	/**
	 * Read a sprite definition
	 * 
	 * @param tokens
	 * @param sprites
	 */
	private void readSprite(String[] tokens, SpriteBank sprites)
	{
		Sprite _sprite = new Sprite();
		_sprite.getBloc().setLeft(Integer.parseInt(tokens[1]));
		_sprite.getBloc().setTop(Integer.parseInt(tokens[2]));
		_sprite.getBloc().setWidth(Integer.parseInt(tokens[3]));
		_sprite.getBloc().setHeight(Integer.parseInt(tokens[4]));
		_sprite.setHotSpotX(Integer.parseInt(tokens[5]));
		_sprite.setHotSpotY(Integer.parseInt(tokens[6]));
		sprites.add(_sprite);
	}

	/**
	 * @param currentSequence
	 *            the currentSequence to set
	 */
	private void setCurrentSequence(Sequence currentSequence)
	{
		myCurrentSequence = currentSequence;
	}

	/**
	 * @param insideSequence
	 *            the insideSequence to set
	 */
	private void setInsideSequence(boolean insideSequence)
	{
		myInsideSequence = insideSequence;
	}

	/**
	 * @param myResourceDefinitionLocator
	 *            the myResourceDefinitionLocator to set
	 */
	public void setResourceDefinitionLocator(ResourceDefinitionLocator resourceDefinitionLocator)
	{
		myResourceDefinitionLocator = resourceDefinitionLocator;
	}

	/**
	 * @param sequenceLineToRead
	 *            the sequenceLineToRead to set
	 */
	private void setSequenceLineToRead(int sequenceLineToRead)
	{
		mySequenceLineToRead = sequenceLineToRead;
		if (0 > mySequenceLineToRead)
		{
			throw new IllegalStateException("sequenceLineToRead must be strictly positive.");
		}
		if (0 < mySequenceLineToRead)
		{
			setInsideSequence(true);
		}
	}

}
