package com.sporniket.libre.game.canvas.descriptor;

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

	private final BaseUrlSpecs myBaseUrlSpecs = new BaseUrlSpecs() ;

	private final CanvasManagerSpecs myCanvasManagerSpecs = new CanvasManagerSpecs() ;

	private CanvasSpecs[] myCanvasSpecs;

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

	public void setCanvasSpecs(CanvasSpecs[] canvasSpecs)
	{
		myCanvasSpecs = canvasSpecs;
	}

}
