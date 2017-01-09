package com.sporniket.libre.game.canvas.descriptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Descriptor of a canvas game.
 *
 * @author dsporn
 *
 */
public class CanvasGameDescriptor implements Cloneable
{
	private static final int INITIAL_CAPACITY__MANAGER_SPECS = 10;

	public static int getInitialCapacityManagerSpecs()
	{
		return INITIAL_CAPACITY__MANAGER_SPECS;
	}

	private final BaseUrlSpecs myBaseUrlSpecs = new BaseUrlSpecs();

	private final CanvasManagerSpecs myCanvasManagerSpecs = new CanvasManagerSpecs();

	private CanvasSpecs[] myCanvasSpecs;

	private final GameletsSpecs myGamelets = new GameletsSpecs();

	private final GraphicalDefinitionSpecs myGraphicalDefinitionsSpecs = new GraphicalDefinitionSpecs();

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		final CanvasGameDescriptor _clone = new CanvasGameDescriptor();

		// copy #myBaseUrlSpecs
		_clone.getBaseUrlSpecs().setBaseUrlForData(getBaseUrlSpecs().getBaseUrlForData());
		_clone.getBaseUrlSpecs().setBaseUrlForJukebox(getBaseUrlSpecs().getBaseUrlForJukebox());
		_clone.getBaseUrlSpecs().setBaseUrlForPictures(getBaseUrlSpecs().getBaseUrlForPictures());
		_clone.getBaseUrlSpecs().setBaseUrlForSoundEffects(getBaseUrlSpecs().getBaseUrlForSoundEffects());
		_clone.getBaseUrlSpecs().setBaseUrlForSprites(getBaseUrlSpecs().getBaseUrlForSprites());

		// copy #myCanvasManagerSpecs
		_clone.getCanvasManagerSpecs().setBufferingNames(getCanvasManagerSpecs().getBufferingNames());
		_clone.getCanvasManagerSpecs().setCanvasSpecs(getCanvasManagerSpecs().getCanvasSpecs());

		// copy #myCanvasSpecs
		final CanvasSpecs[] _cloneCanvasSpecs = new CanvasSpecs[getCanvasSpecs().length];
		for (int _index = 0; _index < _cloneCanvasSpecs.length; _index++)
		{
			_cloneCanvasSpecs[_index] = (CanvasSpecs) getCanvasSpecs()[_index].clone();
		}
		_clone.setCanvasSpecs(_cloneCanvasSpecs);

		// copy #myGamelets
		_clone.getGamelets().setRegistry(getGamelets().getRegistry());
		_clone.getGamelets().setSequence(getGamelets().getSequence());

		// copy #myGraphicalDefinitionsSpecs
		_clone.getGraphicalDefinitionsSpecs().setAxis(getGraphicalDefinitionsSpecs().getAxis());
		_clone.getGraphicalDefinitionsSpecs().setTresholds(getGraphicalDefinitionsSpecs().getTresholds());

		final Map<String, Object> _dataMap = new HashMap<>(getGraphicalDefinitionsSpecs().getDataMap());
		_clone.getGraphicalDefinitionsSpecs().setDataMap(_dataMap);

		return _clone;
	}

	public BaseUrlSpecs getBaseUrlSpecs()
	{
		return myBaseUrlSpecs;
	}

	public CanvasManagerSpecs getCanvasManagerSpecs()
	{
		return myCanvasManagerSpecs;
	}

	public CanvasSpecs[] getCanvasSpecs()
	{
		return myCanvasSpecs;
	}

	public GameletsSpecs getGamelets()
	{
		return myGamelets;
	}

	public GraphicalDefinitionSpecs getGraphicalDefinitionsSpecs()
	{
		return myGraphicalDefinitionsSpecs;
	}

	public void setCanvasSpecs(CanvasSpecs[] canvasSpecs)
	{
		myCanvasSpecs = canvasSpecs;
	}

}
