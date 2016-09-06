/**
 * 
 */
package com.sporniket.libre.game.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.game.api.InvalidValueException;

/**
 * Canvas manager that will deal with creating/maintaining a set of Canvas, and serve as a proxy to use those canvas.
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

	private final Box myScreenBox;

	private final Point myScreenCornerBottomLeft;
	
	private final Point myScreenCornerBottomRight;
	
	private final Point myScreenCornerTopLeft;
	
	private final Point myScreenCornerTopRight;
	
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
		myScreenBox=new Box().withX(0).withY(0).withWidth(getScreenWidth())
				.withHeight(getScreenHeight());
		myScreenCornerTopLeft = new Point().withX(0).withY(0);
		myScreenCornerTopRight = new Point().withX(getScreenWidth()).withY(0);
		myScreenCornerBottomLeft = new Point().withX(0).withY(getScreenHeight());
		myScreenCornerBottomRight = new Point().withX(getScreenWidth()).withY(getScreenHeight());
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
		int _result = getCanvasRegistry().size() - 1;
		getCanvasGuidToIdMap().put(guid, _result);
		return _result;
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

	/**
	 * A box representing the entire screen.
	 * 
	 * @return the box.
	 */
	public Box getScreenBox()
	{
		return myScreenBox;
	}

	/**
	 * The point at the bottom left corner.
	 * 
	 * @return the point.
	 */
	public Point getScreenCornerBottomLeft()
	{
		return myScreenCornerBottomLeft;
	}

	/**
	 * The point at the bottom right corner.
	 * 
	 * @return the point.
	 */
	public Point getScreenCornerBottomRight()
	{
		return myScreenCornerBottomRight;
	}

	/**
	 * The point at the top left corner.
	 * 
	 * @return the point.
	 */
	public Point getScreenCornerTopLeft()
	{
		return myScreenCornerTopLeft;
	}

	/**
	 * The point at the top right corner.
	 * 
	 * @return the point.
	 */
	public Point getScreenCornerTopRight()
	{
		return myScreenCornerTopRight;
	}

	public int getScreenHeight()
	{
		return myScreenHeight;
	}

	public int getScreenWidth()
	{
		return myScreenWidth;
	}

	/**
	 * Call the regenerator of the canvas.
	 * 
	 * @param canvasId
	 *            canvas id.
	 * @throws CanvasException
	 *             when there is a problem.
	 */
	public void regenerate(int canvasId) throws CanvasException
	{
		CanvasDescriptor<CanvasType> _canvasDescriptor = getCanvasRegistry().get(canvasId);
		if(_canvasDescriptor.isDisposed())
		{
			CanvasType _newCanvas = createCanvasData(_canvasDescriptor.getWidth(), _canvasDescriptor.getHeight());
			_canvasDescriptor.setCanvas(_newCanvas);
		}
		_canvasDescriptor.getRegenerator().execute(_canvasDescriptor.getGuid(), _canvasDescriptor.getCanvas());
	}

	/**
	 * Create the actual canvas.
	 * 
	 * @param width
	 *            the width of the canvas.
	 * @param height
	 *            the height of the canvas.
	 * @return the canvas to store in the descriptor.
	 */
	protected abstract CanvasType createCanvasData(int width, int height);

	protected Map<String, Integer> getCanvasGuidToIdMap()
	{
		return myCanvasGuidToIdMap;
	}

	protected List<CanvasDescriptor<CanvasType>> getCanvasRegistry()
	{
		return myCanvasRegistry;
	}
}
