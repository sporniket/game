/**
 * 
 */
package com.sporniket.libre.game.canvas.descriptor;

import com.sporniket.libre.game.gamelet.GameletControler;

/**
 * Specifications of gamelets : registry and main gamelets calling sequence.
 * 
 * @author dsporn
 *
 */
public class GameletsSpecs
{
	/**
	 * Specification of the gamelet registry : name and class.
	 * 
	 * <p>One MUST call a {@link GameletControler#registerGamelet(String, com.sporniket.libre.game.gamelet.Gamelet)} using the name and
	 * an instance of the specified classname.
	 */
	private String[] myRegistry;

	/**
	 * Specification of the main gamelets calling sequence.
	 * 
	 * <p>Typically, the sequence will start with various splash screens, and the last gamelet is the main menu.
	 */
	private String[] mySequence;

	public String[] getRegistry()
	{
		return myRegistry;
	}

	public String[] getSequence()
	{
		return mySequence;
	}

	public void setRegistry(String[] registry)
	{
		myRegistry = registry;
	}

	public void setSequence(String[] sequence)
	{
		mySequence = sequence;
	}
}
