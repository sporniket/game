package com.sporniket.libre.game.api.sprite;


/**
 * Gathers the banks filled when loading actor definitions.
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
 * @deprecated lack of abstraction, too limited.
 */
public class ActorBankSet
{
	private ActorBank myActors = new ActorBank() ;

	private SequenceInstanceBank mySequenceInstances = new SequenceInstanceBank();

	private SequenceBank mySequences = new SequenceBank();
	
	private SpriteBank mySprites = new SpriteBank();

	/**
	 * @return the actors
	 */
	public ActorBank getActors()
	{
		return myActors;
	}

	/**
	 * @return the sequenceInstances
	 */
	public SequenceInstanceBank getSequenceInstances()
	{
		return mySequenceInstances;
	}

	/**
	 * @return the sequences
	 */
	public SequenceBank getSequences()
	{
		return mySequences;
	}

	/**
	 * @return the sprites
	 */
	public SpriteBank getSprites()
	{
		return mySprites;
	}

}