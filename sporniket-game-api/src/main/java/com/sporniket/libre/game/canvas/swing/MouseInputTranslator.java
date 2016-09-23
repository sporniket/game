/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.sporniket.libre.game.input.InputTranslator;
import com.sporniket.libre.game.input.Pointer;
import com.sporniket.libre.game.input.Pointer.State;
import com.sporniket.libre.game.input.PointerEvent;

/**
 * Listen to {@link MouseEvent} and {@link MouseWheelEvent}, and convert them into {@link PointerEvent}.
 * @author dsporn
 *
 */
public class MouseInputTranslator extends InputTranslator implements MouseListener, MouseMotionListener, MouseWheelListener
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		firePointerEvent(new PointerEvent(this, new Pointer(State.WHEEL, 0, 0, -1, e.getWheelRotation() * e.getScrollAmount())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		firePointerEvent(new PointerEvent(this, new Pointer(State.PRESSED, e.getX(), e.getY(), getButtonIndex(e.getButton()), 0)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		firePointerEvent(new PointerEvent(this, new Pointer(State.NOT_PRESSED, e.getX(), e.getY(), getButtonIndex(e.getButton()), 0)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		firePointerEvent(new PointerEvent(this, new Pointer(State.CLIC, e.getX(), e.getY(), getButtonIndex(e.getButton()), e.getClickCount())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		firePointerEvent(new PointerEvent(this, new Pointer(State.PRESSED, e.getX(), e.getY(), getButtonIndex(e.getButton()), 0)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		firePointerEvent(new PointerEvent(this, new Pointer(State.NOT_PRESSED, e.getX(), e.getY(), getButtonIndex(e.getButton()), 0)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// ignore
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
		// ignore
	}

	private int getButtonIndex(int button)
	{
		switch (button)
		{
			case MouseEvent.BUTTON1:
				return 0;
			case MouseEvent.BUTTON2:
				return 1;
			case MouseEvent.BUTTON3:
				return 2;
			default:
				return 0;
		}
	}
}
