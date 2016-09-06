/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.gamelet.GameletControler;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.gamelet.GameletListener;
import com.sporniket.libre.game.gamelet.events.Render;

/**
 * A gamelet controler references a list of gamelets, and run them in a running stack, waiting for events to put a new gamelet on
 * the stack or remove it from the stack and going back to the previous gamelet.
 * 
 * @author dsporn
 *
 */
public abstract class CanvasGameletControler<CanvasType> extends GameletControler<CanvasGameletContext<CanvasType>> implements GameletListener<CanvasGameletContext<CanvasType>>
{

	/**
	 * Index of the current canvas name to use.
	 */
	private int myCanvasBufferingCurrentIndex = -1;

	/**
	 * List of the names of the canvas that are used for the screen buffering (double, triple, ...), must
	 */
	private String[] myCanvasBufferingList;

	/**
	 * Canvas id of the canvas into which drawing is done, initialized with a negative (invalid) value.
	 */
	private int myCurrentCanvas = -1;

	/**
	 * Canvas id of the canvas ready to display, initialized with a negative (invalid) value.
	 */
	private int myCurrentDisplayedCanvas = -1;

	public int getCanvasBufferingCurrentIndex()
	{
		return myCanvasBufferingCurrentIndex;
	}

	public String[] getCanvasBufferingList()
	{
		return myCanvasBufferingList;
	}

	public int getCurrentCanvas()
	{
		return myCurrentCanvas;
	}

	public int getCurrentDisplayedCanvas()
	{
		return myCurrentDisplayedCanvas;
	}

	@Override
	public void onRender(Render<CanvasGameletContext<CanvasType>> event) throws GameletException
	{
		// buffering management
		int _canvasBufferingCurrentIndex = (getCanvasBufferingCurrentIndex() + 1) % getCanvasBufferingList().length;
		setCanvasBufferingCurrentIndex(_canvasBufferingCurrentIndex);

		CanvasManager<CanvasType> _canvasManager = ((CanvasGameletContext<CanvasType>) getContext()).getCanvasManager();
		setCurrentCanvas(_canvasManager.getCanvasId(getCanvasBufferingList()[_canvasBufferingCurrentIndex]));
		CanvasGamelet<CanvasType> _source = (CanvasGamelet<CanvasType>) event.getSource();
		_source.render(getContext(), getCurrentCanvas(), getCurrentDisplayedCanvas());

		setCurrentDisplayedCanvas(getCurrentCanvas());
	}

	public void setCanvasBufferingCurrentIndex(int canvasBufferingCurrentIndex)
	{
		myCanvasBufferingCurrentIndex = canvasBufferingCurrentIndex;
	}

	public void setCanvasBufferingList(String[] canvasBufferingList)
	{
		myCanvasBufferingList = canvasBufferingList;
	}

	public void setCurrentCanvas(int currentCanvas)
	{
		myCurrentCanvas = currentCanvas;
	}

	public void setCurrentDisplayedCanvas(int currentDisplayedCanvas)
	{
		myCurrentDisplayedCanvas = currentDisplayedCanvas;
	}

}
