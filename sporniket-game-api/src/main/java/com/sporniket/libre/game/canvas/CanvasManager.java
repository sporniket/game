/**
 * 
 */
package com.sporniket.libre.game.canvas;

import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.game.api.InvalidValueException;

/**
 * Canvas manager that will deal with creating/maintaining a set of Canvas.
 *
 * @author dsporn
 *
 */
public abstract class CanvasManager<CanvasType>
{
	private static final int REGISTRY_INITIAL_CAPACITY = 50;

	private final Map<String, Integer> myCanvasGuidToIdMap = new HashMap<String, Integer>(REGISTRY_INITIAL_CAPACITY);

	private final List<CanvasDescriptor<CanvasType>> myCanvasRegistry = new ArrayList<CanvasDescriptor<CanvasType>>(
			REGISTRY_INITIAL_CAPACITY);

	private final int myScreenHeight;

	private final int myScreenWidth;

	/**
	 * Create a manager with the specified screen size.
	 * 
	 * @param screenWidth
	 *            width of the screen.
	 * @param screenHeight
	 *            height of the screen.
	 */
	public CanvasManager(int screenWidth, int screenHeight)
	{
		myScreenWidth = screenWidth;
		myScreenHeight = screenHeight;
	}

	public void attachRegenerator(int canvas, CanvasCallback<CanvasType> regenerator)
	{
		CanvasDescriptor<CanvasType> _canvasDescriptor = getCanvasRegistry().get(canvas);
		_canvasDescriptor.setRegenerator(regenerator);
	}

	/**
	 * Create a canvas the same size than the screen.
	 * 
	 * @param guid
	 *            the GUID of the canvas.
	 * @return the canvas id.
	 */
	public int createCanvas(String guid)
	{
		return createCanvas(guid, getScreenWidth(), getScreenHeight());
	}

	/**
	 * Create a canvas with the specified dimensions
	 * 
	 * @param guid
	 *            the GUID of the canvas.
	 * @param width
	 *            canvas width.
	 * @param height
	 *            canvas height.
	 * @return the canvas id.
	 */
	public int createCanvas(String guid, int width, int height)
	{
		CanvasType _data = createCanvasData(width, height);
		CanvasDescriptor<CanvasType> _descriptor = new CanvasDescriptor<CanvasType>();
		_descriptor.setGuid(guid);
		_descriptor.setCanvas(_data);
		_descriptor.setWidth(width);
		_descriptor.setHeight(height);
		getCanvasRegistry().add(_descriptor);
		return getCanvasRegistry().size() - 1;
	}

	/**
	 * Search a canvas by GUID and return the canvas id.
	 * 
	 * @param guid
	 *            the canvas GUID.
	 * @return the canvas id.
	 */
	public int getCanvasId(String guid)
	{
		if (getCanvasGuidToIdMap().containsKey(guid))
		{
			return getCanvasGuidToIdMap().get(guid);
		}
		throw new InvalidValueException(guid);
	}

	public int getScreenHeight()
	{
		return myScreenHeight;
	}

	public int getScreenWidth()
	{
		return myScreenWidth;
	}

	protected Map<String, Integer> getCanvasGuidToIdMap()
	{
		return myCanvasGuidToIdMap;
	}

	protected List<CanvasDescriptor<CanvasType>> getCanvasRegistry()
	{
		return myCanvasRegistry;
	}

	protected abstract CanvasType createCanvasData(int width, int height);
}
