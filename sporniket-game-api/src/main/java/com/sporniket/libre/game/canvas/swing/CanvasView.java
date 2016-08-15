/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import com.sporniket.libre.game.canvas.Box;
import com.sporniket.libre.game.canvas.Point;

/**
 * View to link to a canvas id and the canvas manager hosting the canvas.
 * 
 * @author dsporn
 *
 */
public class CanvasView extends JComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6032724799458153065L;

	public int myCanvasId;

	private BufferedImagesManager myCanvasManager;

	private final Box myDestinationBox = new Box().withX(0).withY(0);

	private final Point myDestinationPoint = new Point().withX(0).withY(0);

	/**
	 * 
	 */
	public CanvasView()
	{
		// TODO Auto-generated constructor stub
	}

	public int getCanvasId()
	{
		return myCanvasId;
	}

	public BufferedImagesManager getCanvasManager()
	{
		return myCanvasManager;
	}

	@Override
	public Dimension getPreferredSize()
	{
		Dimension _result = null;
		if (null != getCanvasManager())
		{
			_result = new Dimension(getCanvasManager().getScreenWidth(), getCanvasManager().getScreenHeight());
		}
		return _result;
	}

	public void setCanvasId(int canvasId)
	{
		myCanvasId = canvasId;
	}

	public void setCanvasManager(BufferedImagesManager canvasManager)
	{
		myCanvasManager = canvasManager;
		getDestinationBox().withWidth(myCanvasManager.getScreenWidth()).withHeight(myCanvasManager.getScreenHeight());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D _g2 = (Graphics2D) g;
		getCanvasManager().copyToGraphics(getCanvasId(), getDestinationBox(), _g2, getDestinationPoint(), this);
	}

	private Box getDestinationBox()
	{
		return myDestinationBox;
	}

	private Point getDestinationPoint()
	{
		return myDestinationPoint;
	}
}
