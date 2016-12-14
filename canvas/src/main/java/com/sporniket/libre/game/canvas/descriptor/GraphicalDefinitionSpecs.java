/**
 *
 */
package com.sporniket.libre.game.canvas.descriptor;

import java.util.Set;
import java.util.TreeSet;

/**
 * A specification to decide the graphical definition according to the screen size.
 *
 * @author dsporn
 *
 */
public class GraphicalDefinitionSpecs
{
	/**
	 * Enumeration of the available axis for deciding the graphical definition.
	 *
	 * @author dsporn
	 *
	 */
	public enum Axis
	{
		HORIZONTAL,
		VERTICAL;
	}

	private Axis myAxis = Axis.HORIZONTAL;

	private final Set<Integer> myTresholds = new TreeSet<>();

	public Axis getAxis()
	{
		return myAxis;
	}

	public Set<Integer> getTresholds()
	{
		return myTresholds;
	}

	public void setAxis(Axis axis)
	{
		myAxis = axis;
	}

	public void setAxis(String axis)
	{
		myAxis = Axis.valueOf(axis.toUpperCase());
	}

	public void setTresholds(Set<Integer> tresholds)
	{
		if (tresholds != getTresholds())
		{
			getTresholds().clear();
			getTresholds().addAll(tresholds);
		}
	}

	public void setTresholds(String[] tresholds)
	{
		for (final String treshold : tresholds)
		{
			getTresholds().add(Integer.parseInt(treshold));
		}
	}

}
