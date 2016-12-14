package com.sporniket.libre.game.canvas.descriptor;

/**
 * Descriptor of a canvas game.
 *
 * @author dsporn
 *
 */
public class CanvasGameDescriptor
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
