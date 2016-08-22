package com.sporniket.libre.game.pal.game.canvas.descriptor;

import java.util.HashMap;
import java.util.Map;

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

	private BaseUrlSpecs myBaseUrlSpecs;

	private final Map<String, CanvasManagerSpecs> myCanvasManagerSpecs = new HashMap<String, CanvasManagerSpecs>(
			INITIAL_CAPACITY__MANAGER_SPECS);

	private CanvasSpecs[] myCanvasSpecs;

	public BaseUrlSpecs getBaseUrlSpecs()
	{
		return myBaseUrlSpecs;
	}

	public Map<String, CanvasManagerSpecs> getCanvasManagerSpecs()
	{
		return myCanvasManagerSpecs;
	}

	public CanvasSpecs[] getCanvasSpecs()
	{
		return myCanvasSpecs;
	}

	public void setBaseUrlSpecs(BaseUrlSpecs baseUrlSpecs)
	{
		myBaseUrlSpecs = baseUrlSpecs;
	}

	public void setCanvasSpecs(CanvasSpecs[] canvasSpecs)
	{
		myCanvasSpecs = canvasSpecs;
	}

}
