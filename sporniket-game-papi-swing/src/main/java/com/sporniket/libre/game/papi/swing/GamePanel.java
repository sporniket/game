/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import sun.awt.image.ToolkitImage;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;
import com.sporniket.libre.game.api.SimpleFolderResourceDefinitionLocator;
import com.sporniket.libre.game.api.SimpleTextFileResourceDefinitionLoaderV00;
import com.sporniket.libre.game.api.sprite.Actor;
import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.SequenceBank;
import com.sporniket.libre.game.api.sprite.SequenceInstance;
import com.sporniket.libre.game.api.sprite.SequenceInstanceBank;
import com.sporniket.libre.game.api.sprite.Sprite;
import com.sporniket.libre.game.api.sprite.SpriteBank;
import com.sporniket.libre.game.api.types.BlocDefinition;
import com.sporniket.libre.game.api.types.CopyMode;
import com.sporniket.libre.game.api.types.Position.Vector;
import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.gamelet.input.KeyboardStateProvider;
import com.sporniket.libre.game.gamelet.input.Pointer;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;
import com.sporniket.libre.game.papi.BufferingStrategy;
import com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface;
import com.sporniket.libre.game.papi.InputAbstractionLayerInterface;
import com.sporniket.libre.game.papi.PictureHandler;
import com.sporniket.libre.game.papi.SoundAbstractionLayerInterface;
import com.sporniket.libre.game.papi.SoundHandler;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Custom panel that will provide game rendering.
 * 
 * This panel provide an offscreen surface for drawing onto, a call to {@link #commitDrawingSurface()} force a redraw with the new
 * graphics.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Swing</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Swing</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
// FIXME add constructor accepting a ScreenFeatureSet.
// FIXME change to support platform adapter ?
class GamePanel extends JPanel implements GraphicAbstractionLayerInterface, SoundAbstractionLayerInterface,
		InputAbstractionLayerInterface, MouseListener, MouseMotionListener, ResourceDefinitionLoader
{
	/**
	 * Implementation of the picture handler.
	 * 
	 * <strong>WARNING ! the {@link #getPixel(int, int)} implementation only works for loaded pictures (BufferedImage) for now. Need
	 * a better implementation to work with other Images as well.</strong>
	 * 
	 * @author dsporn
	 * 
	 */
	private static class PictureHandlerSwing extends PictureHandler
	{

		public PictureHandlerSwing(int value, int width, int height, GamePanel host)
		{
			super(value, width, height);
			setHost(host);
		}

		private GamePanel myHost;

		@Override
		public int getPixel(int x, int y)
		{
			if (null == getHost())
			{
				throw new IllegalStateException("Picture has no host set, forgotten in the instanciation...");
			}

			// out of bounds ?
			if (x < 0 || x >= getFeatures().getWidth() || y < 0 || y >= getFeatures().getHeight())
			{
				return 0;
			}

			// ok, retrieve the pixel.
			int _result = 0;
			Image _data = getHost().getImages().get(getValue());
			// FIXME find a better implementation (supporting various implementation of the class).
			if (_data instanceof BufferedImage)
			{
				_result = ((BufferedImage) _data).getRGB(x, y);
			}
			return _result;
		}

		/**
		 * @return the host
		 */
		private GamePanel getHost()
		{
			return myHost;
		}

		/**
		 * @param host
		 *            the host to set
		 */
		private void setHost(GamePanel host)
		{
			myHost = host;
		}

	}

	/**
	 * Provide a pointer matching the mouse event received by {@link GamePanel}.
	 * 
	 * @author dsporn
	 * 
	 */
	private static class PointerProvider implements PointerStateProvider
	{
		Pointer myNextPointer;

		Pointer[] myStates =
		{
			new Pointer()
		};

		/**
		 * @return the nextPointer
		 */
		private Pointer getNextPointer()
		{
			if (null == myNextPointer)
			{
				// instanciate an undefined pointer.
				myNextPointer = new Pointer();
			}
			return myNextPointer;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface.PointerStateProvider#getPointerCount()
		 */
		public int getPointerCount()
		{
			return 1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface.PointerStateProvider#getStates()
		 */
		public Pointer[] getStates()
		{
			return myStates;
		}

		/**
		 * Take into account a mouse <em>drag</em>, i.e. mouse button is pressed.
		 * 
		 * @param x
		 * @param y
		 */
		public void recordMouseDrag(int x, int y)
		{
			Pointer _newPointer = new Pointer();
			_newPointer.setState(Pointer.State.PRESSED);
			_newPointer.setX(x);
			_newPointer.setY(y);
			setNextPointer(_newPointer);
		}

		/**
		 * Take into account a mouse <em>move</em>, i.e. mouse button is not pressed.
		 * 
		 * @param x
		 * @param y
		 */
		public void recordMouseMove(int x, int y)
		{
			Pointer _newPointer = new Pointer();
			_newPointer.setState(Pointer.State.RELEASED);
			_newPointer.setX(x);
			_newPointer.setY(y);
			setNextPointer(_newPointer);
		}

		/**
		 * @param nextPointer
		 *            the nextPointer to set
		 */
		private void setNextPointer(Pointer nextPointer)
		{
			myNextPointer = nextPointer;
		}

		/**
		 * called by GamePanel in its update.
		 * 
		 * @since 0-SNAPSHOT
		 */
		public void update()
		{
			myStates[0] = getNextPointer();
		}

	}

	/**
	 * Tells which line to start with when copying one line of two.
	 * 
	 * @author dsporn
	 * 
	 */
	private static enum StartingLine
	{
		FIRST_LINE(0),
		SECOND_LINE(1);

		private int myValue;

		/**
		 * @param value
		 */
		private StartingLine(int value)
		{
			myValue = value;
		}

		/**
		 * @return the value
		 */
		public int getValue()
		{
			return myValue;
		}

	}

	private static final BufferingStrategy DEFAULT__BUFFER_STRATEGY = BufferingStrategy.TRIPLE_BUFFERING;

	/**
	 * The default {@link ScreenFeatureSet}.
	 */
	private static final ScreenFeatureSet DEFAULT__SCREEN_FEATURE_SET = ScreenFeatureSet.VGA_PORTRAIT;

	private static final String PATH__DATA = "data";

	/**
	 * Base path to load resources
	 */
	private static final String PATH__RESOURCES_BASE = "assets";

	private int myCurrentDisplay = 0;

	private int myCurrentOffscreen = 1;

	private List<Image> myImages = new ArrayList<Image>();

	/**
	 * Number of actually used Offscreens.
	 */
	private int myOffscreenCount;

	private Graphics2D myOffscreenGraphics[] = new Graphics2D[DEFAULT__BUFFER_STRATEGY.getBufferCount()];

	private Image myOffScreens[] = new Image[DEFAULT__BUFFER_STRATEGY.getBufferCount()];

	private String myPictureBasePath;

	private SimpleTextFileResourceDefinitionLoaderV00 myResourceDefinitionLoader = new SimpleTextFileResourceDefinitionLoaderV00();

	/**
	 * @return the resourceDefinitionLoader
	 */
	SimpleTextFileResourceDefinitionLoaderV00 getResourceDefinitionLoader()
	{
		return myResourceDefinitionLoader;
	}

	private PointerProvider myPointerProvider = new PointerProvider();

	/**
	 * Feature set of the screen.
	 * 
	 * Allow for a screen independancy, if used correctly.
	 */
	private ScreenFeatureSet myScreenFeatureSet;

	public GamePanel()
	{
		this(DEFAULT__SCREEN_FEATURE_SET);
	}

	/**
	 * Initialise the panel with a default {@link ScreenFeatureSet}.
	 * 
	 * @param layout
	 * @see #DEFAULT__SCREEN_FEATURE_SET
	 */
	public GamePanel(LayoutManager layout)
	{
		this(layout, DEFAULT__SCREEN_FEATURE_SET);
	}

	public GamePanel(LayoutManager layout, ScreenFeatureSet screenFeatureSet)
	{
		this(layout, screenFeatureSet, DEFAULT__BUFFER_STRATEGY);
	}

	/**
	 * Initialise the panel with the specified {@link ScreenFeatureSet}.
	 * 
	 * @param layout
	 * @param screenFeatureSet
	 */
	public GamePanel(LayoutManager layout, ScreenFeatureSet screenFeatureSet, BufferingStrategy bufferStrategy)
	{
		super(layout, true);
		initGamePanel(screenFeatureSet, bufferStrategy);
	}

	public GamePanel(ScreenFeatureSet screenFeatureSet)
	{
		this(screenFeatureSet, DEFAULT__BUFFER_STRATEGY);
	}

	public GamePanel(ScreenFeatureSet screenFeatureSet, BufferingStrategy bufferStrategy)
	{
		super();
		initGamePanel(screenFeatureSet, bufferStrategy);
	}

	private void addEventHandler()
	{
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				// commitDrawingSurface();
			}
		});

	}

	public void commitDisplay()
	{
		commitDrawingSurface();
	}

	/**
	 * Change the image displayed and given for drawing.
	 */
	public void commitDrawingSurface()
	{
		nextDisplay();
		nextOffscreen();
		repaint();
	}

	/**
	 * @param resource
	 * @return
	 */
	private String computePathForImageResource(String resource)
	{
		String _resourceBuffer = new StringBuffer(getPictureBasePath()).append(resource).toString();
		return _resourceBuffer;
	}

	public void copyPicture(PictureHandler source, int x, int y, int width, int height, int dx, int dy)
	{
		copyPicture(source, x, y, width, height, dx, dy, CopyMode.NORMAL);
	}

	public void copyPicture(PictureHandler source, int x, int y, int width, int height, int dx, int dy, CopyMode mode)
	{
		switch (mode)
		{
			case EVEN_LINES:
				copyPicture__evenLines(source, x, y, width, height, dx, dy);
				break;
			case ODD_LINES:
				copyPicture__oddLines(source, x, y, width, height, dx, dy);
				break;
			default:
				copyPicture__normal(source, x, y, width, height, dx, dy);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#copyPicture(com.sporniket.libre.game.papi.PictureHandler,
	 * int, int, int, int, com.sporniket.libre.game.papi.PictureHandler, int, int)
	 */

	public void copyPicture(PictureHandler source, int x, int y, int width, int height, PictureHandler destination, int dx, int dy)
	{
		copyPicture(source, x, y, width, height, destination, dx, dy, CopyMode.NORMAL);
	}

	public void copyPicture(PictureHandler source, int x, int y, int width, int height, PictureHandler destination, int dx, int dy,
			CopyMode mode)
	{
		switch (mode)
		{
			case EVEN_LINES:
				copyPicture__evenLines(source, x, y, width, height, destination, dx, dy);
				break;
			case ODD_LINES:
				copyPicture__oddLines(source, x, y, width, height, destination, dx, dy);
				break;
			default:
				copyPicture__normal(source, x, y, width, height, destination, dx, dy);
		}
	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param destination
	 *            Graphics
	 * @param dx
	 * @param dy
	 */
	private void copyPicture__evenLines(PictureHandler source, int x, int y, int width, int height, Graphics2D _destination,
			int dx, int dy)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		int _ddX = x * _fx;
		int _ddY = y * _fy;
		int _ddWidth = width * _fx;
		int _ddHeight = height * _fy;
		int _ddDx = dx * _fx;
		int _ddDy = dy * _fy;
		if (isOdd(_ddDy))
		{
			doCopyPictureOneLineOfTwo(source, _ddX, _ddY, _ddWidth, _ddHeight, _destination, _ddDx, _ddDy + 1,
					StartingLine.SECOND_LINE);
		}
		else
		{
			doCopyPictureOneLineOfTwo(source, _ddX, _ddY, _ddWidth, _ddHeight, _destination, _ddDx, _ddDy, StartingLine.FIRST_LINE);
		}
	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dx
	 * @param dy
	 */
	private void copyPicture__evenLines(PictureHandler source, int x, int y, int width, int height, int dx, int dy)
	{
		Graphics2D _destination = getCurrentGraphics();
		copyPicture__evenLines(source, x, y, width, height, _destination, dx, dy);
	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param destination
	 *            Picture
	 * @param dx
	 * @param dy
	 */
	private void copyPicture__evenLines(PictureHandler source, int x, int y, int width, int height, PictureHandler destination,
			int dx, int dy)
	{
		Graphics2D _destination = getPictureGraphics(destination);
		copyPicture__evenLines(source, x, y, width, height, _destination, dx, dy);
	}

	private void copyPicture__normal(PictureHandler source, int x, int y, int width, int height, int dx, int dy)
	{
		Graphics2D g = getCurrentGraphics();
		doCopyPictureToGraphics(source, x, y, width, height, g, dx, dy);

	}

	private void copyPicture__normal(PictureHandler source, int x, int y, int width, int height, PictureHandler destination,
			int dx, int dy)
	{
		Graphics2D g = getPictureGraphics(destination);
		doCopyPictureToGraphics(source, x, y, width, height, g, dx, dy);

	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param destination
	 * @param dx
	 * @param dy
	 */
	private void copyPicture__oddLines(PictureHandler source, int x, int y, int width, int height, Graphics2D destination, int dx,
			int dy)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		int _ddX = x * _fx;
		int _ddY = y * _fy;
		int _ddWidth = width * _fx;
		int _ddHeight = height * _fy;
		int _ddDx = dx * _fx;
		int _ddDy = dy * _fy;
		if (isOdd(_ddDy))
		{
			doCopyPictureOneLineOfTwo(source, _ddX, _ddY, _ddWidth, _ddHeight, destination, _ddDx, _ddDy, StartingLine.FIRST_LINE);
		}
		else
		{
			doCopyPictureOneLineOfTwo(source, _ddX, _ddY, _ddWidth, _ddHeight, destination, _ddDx, _ddDy + 1,
					StartingLine.SECOND_LINE);
		}
	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dx
	 * @param dy
	 */
	private void copyPicture__oddLines(PictureHandler source, int x, int y, int width, int height, int dx, int dy)
	{
		Graphics2D _destination = getCurrentGraphics();
		copyPicture__oddLines(source, x, y, width, height, _destination, dx, dy);
	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param destination
	 * @param dx
	 * @param dy
	 */
	private void copyPicture__oddLines(PictureHandler source, int x, int y, int width, int height, PictureHandler destination,
			int dx, int dy)
	{
		Graphics2D _destination = getPictureGraphics(destination);
		copyPicture__oddLines(source, x, y, width, height, _destination, dx, dy);
	}

	public PictureHandler createOffscreenPicture()
	{
		return createOffscreenPicture(getScreenFeatures().getWidth(), getScreenFeatures().getHeight());
	}

	public PictureHandler createOffscreenPicture(int width, int height)
	{
		Image _image = createImage(width, height);
		return registerPicture(_image);
	}

	public void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture)
	{
		Graphics2D _destination = getCurrentGraphics();
		displayActorBank(actorBank, spriteBank, picture, _destination);
	}

	public void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture, PictureHandler destination)
	{
		Graphics2D _destination = getPictureGraphics(destination);
		displayActorBank(actorBank, spriteBank, picture, _destination);
	}

	/**
	 * Display active actors using the specified sprite definition, the sprite picture, to the specified graphics.
	 * 
	 * @param actorBank
	 * @param spriteBank
	 * @param picture
	 * @param destination
	 */
	private void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture, Graphics2D destination)
	{
		for (Actor _actor : actorBank)
		{
			if (!_actor.isActive())
			{
				continue;
			}
			SequenceInstance _sequence = _actor.getSequence();
			int _spriteIndex = _sequence.getCurrentSprite();
			if (_spriteIndex < 0)
			{
				// continue ; //FIXME in certain situation, the sequence instance return -1
			}
			Sprite _sprite = spriteBank.get(_spriteIndex);
			Vector _dpos = _actor.getPosition().getCurrentPositionVector();
			int _dx = _dpos.getX() - _sprite.getHotSpotX();
			int _dy = _dpos.getY() - _sprite.getHotSpotY();
			CopyMode _mode = CopyMode.NORMAL;
			BlocDefinition _bloc = _sprite.getBloc();
			switch (_mode)
			{
				case EVEN_LINES:
					copyPicture__evenLines(picture, _bloc.getLeft(), _bloc.getTop(), _bloc.getWidth(), _bloc.getHeight(),
							destination, _dx, _dy);
					break;
				case ODD_LINES:
					copyPicture__oddLines(picture, _bloc.getLeft(), _bloc.getTop(), _bloc.getWidth(), _bloc.getHeight(),
							destination, _dx, _dy);
					break;
				default:
					doCopyPictureToGraphics(picture, _bloc.getLeft(), _bloc.getTop(), _bloc.getWidth(), _bloc.getHeight(),
							destination, _dx, _dy);
			}
		}
	}

	public void displayPicture(PictureHandler picture, int x, int y)
	{
		displayPicture(picture, x, y, CopyMode.NORMAL);
	}

	public void displayPicture(PictureHandler picture, int x, int y, CopyMode mode)
	{
		switch (mode)
		{
			case EVEN_LINES:
				displayPicture__evenLines(picture, x, y);
				break;
			case ODD_LINES:
				displayPicture__oddLines(picture, x, y);
				break;
			default:
				displayPicture__Normal(picture, x, y);
		}
	}

	/**
	 * @param picture
	 * @param x
	 * @param y
	 */
	private void displayPicture__evenLines(PictureHandler picture, int x, int y)
	{
		{
			int _ddDx = x * getScreenFeatures().getXfactor();
			int _ddDy = y * getScreenFeatures().getYfactor();
			if (isOdd(_ddDy))
			{
				doCopyPictureOneLineOfTwo(picture, getCurrentGraphics(), _ddDx, _ddDy, StartingLine.SECOND_LINE);
			}
			else
			{
				doCopyPictureOneLineOfTwo(picture, getCurrentGraphics(), _ddDx, _ddDy, StartingLine.FIRST_LINE);
			}
		}
	}

	/**
	 * <code>displayPicture</code> implementation in the normal case.
	 * 
	 * @param picture
	 * @param x
	 * @param y
	 */
	private void displayPicture__Normal(PictureHandler picture, int x, int y)
	{
		Graphics2D g = getCurrentGraphics();
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		doCopyPicture(picture, g, x * _fx, y * _fy);
	}

	/**
	 * @param picture
	 * @param x
	 * @param y
	 */
	private void displayPicture__oddLines(PictureHandler picture, int x, int y)
	{
		{
			int _ddDx = x * getScreenFeatures().getXfactor();
			int _ddDy = y * getScreenFeatures().getYfactor();
			if (isOdd(_ddDy))
			{
				doCopyPictureOneLineOfTwo(picture, getCurrentGraphics(), _ddDx, _ddDy, StartingLine.FIRST_LINE);
			}
			else
			{
				doCopyPictureOneLineOfTwo(picture, getCurrentGraphics(), _ddDx, _ddDy, StartingLine.SECOND_LINE);
			}
		}
	}

	/**
	 * Actually does the copy of the whole picture at the specified position.
	 * 
	 * @param source
	 * @param destination
	 * @param ddDx
	 *            device dependant Destination x
	 * @param ddDy
	 *            device dependant Destination y
	 */
	private void doCopyPicture(PictureHandler source, Graphics2D destination, int ddDx, int ddDy)
	{
		Image _image = getPicture(source);
		if (null == _image)
		{
			throw new NullPointerException("Trying to copy a null picture from Handler source " + source.getValue());
		}
		destination.drawImage(_image, ddDx, ddDy, this);
	}

	/**
	 * Actually does the copy, the position and dimensions are expressed in devide dependant units (pixels).
	 * 
	 * @param source
	 * @param ddX
	 * @param ddY
	 * @param ddWidth
	 * @param ddHeight
	 * @param destination
	 * @param ddDx
	 * @param ddDy
	 */
	private void doCopyPicture(PictureHandler source, int ddX, int ddY, int ddWidth, int ddHeight, Graphics2D destination,
			int ddDx, int ddDy)
	{
		Image _image = getPicture(source);
		if (null == _image)
		{
			throw new NullPointerException("Trying to copy a null picture from Handler source " + source.getValue());
		}

		int _ddDx2 = ddDx + ddWidth;
		int _ddDy2 = ddDy + ddHeight;
		int _ddX2 = ddX + ddWidth;
		int _ddY2 = ddY + ddHeight;
		destination.drawImage(_image, ddDx, ddDy, _ddDx2, _ddDy2, ddX, ddY, _ddX2, _ddY2, this);
	}

	/**
	 * Actually does the copy of the whole picture at the specified position, but only one line of two are copied.
	 * 
	 * The first line + <code>ddYOffset</code> is copied at ddDy, the second is skipped, the third is copied at ddDy+2, and so on...
	 * 
	 * @param source
	 * @param destination
	 * @param ddDx
	 * @param ddDy
	 * @param startingLine
	 * 
	 * @see #doCopyPictureOneLineOfTwo__doItNow(Image, int, int, int, int, Graphics2D, int, int)
	 */
	private void doCopyPictureOneLineOfTwo(PictureHandler source, Graphics2D destination, int ddDx, int ddDy,
			StartingLine startingLine)
	{
		Image _image = getPicture(source);
		if (null == _image)
		{
			throw new NullPointerException("Trying to copy a null picture from Handler source " + source.getValue());
		}
		int _ddX = 0;
		int _ddY = startingLine.getValue();
		int _ddHeight = _image.getHeight(this) - _ddY;
		int _ddWidth = _image.getWidth(this);
		if (_ddHeight < 0 || _ddWidth < 0)
		{
			throw new IllegalStateException("Image was not fully loaded...");
		}
		int _ddDy = ddDy;
		doCopyPictureOneLineOfTwo__doItNow(_image, _ddX, _ddY, _ddWidth, _ddHeight, destination, ddDx, _ddDy);
	}

	/**
	 * Actually does the copy of a picture area at the specified position, but only one line of two are copied.
	 * 
	 * The first line + <code>ddYOffset</code> is copied at ddDy, the second is skipped, the third is copied at ddDy+2, and so on...
	 * 
	 * @param source
	 * @param ddX
	 * @param ddY
	 * @param ddWidth
	 * @param ddHeight
	 * @param destination
	 * @param ddDx
	 * @param ddDy
	 * @param startingLine
	 */
	private void doCopyPictureOneLineOfTwo(PictureHandler source, int ddX, int ddY, int ddWidth, int ddHeight,
			Graphics2D destination, int ddDx, int ddDy, StartingLine startingLine)
	{
		Image _image = getPicture(source);
		if (null == _image)
		{
			throw new NullPointerException("Trying to copy a null picture from Handler source " + source.getValue());
		}
		int yOffset = startingLine.getValue();
		int _ddX = ddX;
		int _ddY = ddY + yOffset;
		int _ddHeight = ddHeight - yOffset;
		int _ddWidth = ddWidth;
		if (_ddHeight < 0 || _ddWidth < 0)
		{
			throw new IllegalStateException("Image was not fully loaded...");
		}
		int _ddDy = ddDy;
		doCopyPictureOneLineOfTwo__doItNow(_image, _ddX, _ddY, _ddWidth, _ddHeight, destination, ddDx, _ddDy);
	}

	/**
	 * Really do the one line of two copy, it skips the second line, forth line, and so on of the source area.
	 * 
	 * @param image
	 * @param ddX
	 * @param ddY
	 * @param ddHeight
	 * @param ddWidth
	 * @param destination
	 * @param ddDx
	 * @param ddDy
	 */
	private void doCopyPictureOneLineOfTwo__doItNow(Image image, int ddX, int ddY, int ddWidth, int ddHeight,
			Graphics2D destination, int ddDx, int ddDy)
	{
		int _ddY = ddY;
		int _ddDy = ddDy;
		int _ddX2 = ddX + ddWidth;
		int _ddDx2 = ddDx + ddWidth;
		for (int _i = 0; _i < ddHeight; _i += 2)
		{
			// copy one line
			destination.drawImage(image, ddDx, _ddDy, _ddDx2, _ddDy + 1, ddX, _ddY, _ddX2, _ddY + 1, this);
			_ddY += 2;
			_ddDy += 2;
		}
	}

	/**
	 * Copy the source picture to the specified canvas (position and dimension in devide independant measure).
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param g
	 * @param dx
	 * @param dy
	 */
	private void doCopyPictureToGraphics(PictureHandler source, int x, int y, int width, int height, Graphics2D g, int dx, int dy)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		doCopyPicture(source, x * _fx, y * _fy, width * _fx, height * _fy, g, dx * _fx, dy * _fy);
	}

	/**
	 * @return the currentDisplay
	 */
	protected int getCurrentDisplay()
	{
		return myCurrentDisplay;
	}

	private Graphics2D getCurrentGraphics()
	{
		return getOffscreenGraphics()[getCurrentOffscreen()];
	}

	/**
	 * @return the currentOffscreen
	 */
	protected int getCurrentOffscreen()
	{
		return myCurrentOffscreen;
	}

	public Graphics2D getDrawingSurface()
	{
		return getOffscreenGraphics()[getCurrentDisplay()];
	}

	public GameControllerStateProvider getGameControllerStateProvider() throws UnsupportedOperationException
	{
		return null;
	}

	/**
	 * @return the images
	 */
	protected List<Image> getImages()
	{
		return myImages;
	}

	public KeyboardStateProvider getKeyboardStateProvider() throws UnsupportedOperationException
	{
		return null;
	}

	/**
	 * @return the offscreenCount
	 */
	private int getOffscreenCount()
	{
		return myOffscreenCount;
	}

	/**
	 * @return the offscreenGraphics
	 */
	protected Graphics2D[] getOffscreenGraphics()
	{
		return myOffscreenGraphics;
	}

	/**
	 * @return the offScreens
	 */
	protected Image[] getOffscreens()
	{
		return myOffScreens;
	}

	private Image getPicture(PictureHandler handler)
	{
		return getImages().get(handler.getValue());
	}

	/**
	 * @return the resourcePathBase
	 */
	private String getPictureBasePath()
	{
		return myPictureBasePath;
	}

	private Graphics2D getPictureGraphics(PictureHandler destination)
	{
		return (Graphics2D) getPicture(destination).getGraphics();
	}

	/**
	 * @return the pointerProvider
	 */
	private PointerProvider getPointerProvider()
	{
		return myPointerProvider;
	}

	public PointerStateProvider getPointerStateProvider() throws UnsupportedOperationException
	{
		return getPointerProvider();
	}

	public Dimension getPreferredSize()
	{
		// return new Dimension(240, 400); // QWVGA, Portrait
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		int _width = getScreenFeatures().getWidth() * _fx;
		int _height = getScreenFeatures().getHeight() * _fy;
		return new Dimension(_width, _height); // QWVGA, Portrait
	}

	public ScreenFeatureSet getScreenFeatures()
	{
		return myScreenFeatureSet;
	}

	/**
	 * Change the current display for the next one (commit display).
	 * 
	 * @see #commitDisplay()
	 */
	private void nextDisplay()
	{
		++myCurrentDisplay;
		if (getCurrentDisplay() >= getOffscreenCount())
		{
			myCurrentDisplay = 0;
		}
	}

	/**
	 * Change the current offscreen for the next one (not to draw in the now current display).
	 * 
	 * @see #commitDisplay()
	 */
	private void nextOffscreen()
	{
		++myCurrentOffscreen;
		if (getCurrentOffscreen() >= getOffscreenCount())
		{
			myCurrentOffscreen = 0;
		}
	}

	public void init()
	{
		initOffscreens();
		addEventHandler();
	}

	/**
	 * Add internal listeners.
	 */
	private void initBuiltinListeners()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * @param screenFeatureSet
	 * @param bufferStrategy
	 */
	private void initGamePanel(ScreenFeatureSet screenFeatureSet, BufferingStrategy bufferStrategy)
	{
		setupResourceDefinitionLoader();
		setScreenFeatureSet(screenFeatureSet);
		setOffscreenCount(bufferStrategy.getBufferCount());
		initBuiltinListeners();
	}

	/**
	 * Compute the path of the data directory (data other than pictures)
	 */
	private void setupResourceDefinitionLoader()
	{
		StringBuffer _basePath = new StringBuffer(".").append(File.separator).append(PATH__RESOURCES_BASE).append(File.separator)
				.append(PATH__DATA);
		getResourceDefinitionLoader().setResourceDefinitionLocator(
				new SimpleFolderResourceDefinitionLocator(new File(_basePath.toString())));
	}

	private void initOffscreens()
	{
		for (int i = 0; i < getOffscreens().length; i++)
		{
			getOffscreens()[i] = createImage(getScreenFeatures().getWidth(), getScreenFeatures().getHeight());
			getOffscreenGraphics()[i] = (Graphics2D) getOffscreens()[i].getGraphics();

			Graphics2D g = getOffscreenGraphics()[i];
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());

		}

	}

	public boolean isGameControllerAware()
	{
		return false;
	}

	public boolean isKeyboardAware()
	{
		return false;
	}

	/**
	 * @param value
	 * @return
	 */
	private boolean isOdd(int value)
	{
		return 1 == (value & 1);
	}

	public boolean isPointerAware()
	{
		return true;
	}

	public PictureHandler loadPicture(String ressource, boolean useBlackAsTransparentColor) throws IOException
	{
		// do not care about useBlackAsTransparentColor.
		BufferedImage _image = loadImage(ressource);

		return registerPicture(_image);
	}

	/**
	 * Load an image.
	 * 
	 * @param ressource
	 * @return
	 */
	private BufferedImage loadImage(String ressource)
	{
		String _resource = computePathForImageResource(ressource);

		// load image with transparency
		ImageIcon _iic = new ImageIcon(_resource);
		BufferedImage _image = ((ToolkitImage) _iic.getImage()).getBufferedImage();
		return _image;
	}
	
	

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#captureScreen()
	 */
	@Override
	public File captureScreen() throws IOException
	{
		File _result = new File(System.getProperty( "user.home" ),"capture.png");
		final Image _image = getOffscreens()[getCurrentDisplay()];
		ImageIO.write((RenderedImage) _image, "png", _result);
		return _result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#changePictureSource(com.sporniket.libre.game.papi.PictureHandler
	 * , java.lang.String, boolean)
	 */
	@Override
	public PictureHandler changePictureSource(PictureHandler picture, String ressource, boolean useBlackAsTransparentColor)
			throws IOException
	{
		int _index = picture.getValue();
		getImages().remove(_index);
		BufferedImage _newImage = loadImage(ressource);
		getImages().add(_index, _newImage);
		
		picture.updateFeatures(_newImage.getWidth(), _newImage.getHeight());
		return picture;
	}

	public void mouseClicked(MouseEvent e)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		getPointerProvider().recordMouseMove(e.getX() / _fx, e.getY() / _fy);
	}

	public void mouseDragged(MouseEvent e)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		getPointerProvider().recordMouseDrag(e.getX() / _fx, e.getY() / _fy);
	}

	public void mouseEntered(MouseEvent e)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		getPointerProvider().recordMouseMove(e.getX() / _fx, e.getY() / _fy);
	}

	public void mouseExited(MouseEvent e)
	{
		// do nothing
	}

	public void mouseMoved(MouseEvent e)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		getPointerProvider().recordMouseMove(e.getX() / _fx, e.getY() / _fy);
	}

	public void mousePressed(MouseEvent e)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		getPointerProvider().recordMouseDrag(e.getX() / _fx, e.getY() / _fy);
	}

	public void mouseReleased(MouseEvent e)
	{
		int _fx = getScreenFeatures().getXfactor();
		int _fy = getScreenFeatures().getYfactor();
		getPointerProvider().recordMouseMove(e.getX() / _fx, e.getY() / _fy);
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(getOffscreens()[getCurrentDisplay()], 0, 0, this);
	}

	/**
	 * Store the picture and return a picture handler for later use.
	 * 
	 * @param _image
	 * @return
	 */
	private PictureHandler registerPicture(Image _image)
	{
		int _id = getImages().size();
		getImages().add(_image);
		// compute image dimension in device independante unit.
		int _width = _image.getWidth(this) / getScreenFeatures().getXfactor();
		int _height = _image.getHeight(this) / getScreenFeatures().getYfactor();
		PictureHandler _handler = new PictureHandlerSwing(_id, _width, _height, this);
		return _handler;
	}

	/**
	 * @param offscreenCount
	 *            the offscreenCount to set
	 */
	private void setOffscreenCount(int offscreenCount)
	{
		myOffscreenCount = offscreenCount;
	}

	/**
	 * @param path
	 *            the resourcePathBase to set
	 */
	private void setPictureBasePath(String path)
	{
		myPictureBasePath = path;
	}

	/**
	 * @param screenFeatureSet
	 *            the screenFeatureSet to set
	 */
	public void setScreenFeatureSet(ScreenFeatureSet screenFeatureSet)
	{
		myScreenFeatureSet = screenFeatureSet;
		updatePictureBasePath();
	}

	public void update()
	{
		((PointerProvider) getPointerStateProvider()).update();
	}

	private void updatePictureBasePath()
	{
		StringBuffer _resourceBuffer = new StringBuffer(".").append(File.separator).append(PATH__RESOURCES_BASE)
				.append(File.separator).append(getScreenFeatures().getGraphicsDefinition().toString()).append(File.separator);
		setPictureBasePath(_resourceBuffer.toString());
	}

	public void loadSpriteAndActorDefinitions(File file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		getResourceDefinitionLoader().loadSpriteAndActorDefinitions(file, sprites, sequences, sequenceInstances, actors);
	}

	public void loadSpriteAndActorDefinitions(InputStream inputStream, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		getResourceDefinitionLoader().loadSpriteAndActorDefinitions(inputStream, sprites, sequences, sequenceInstances, actors);
	}

	public void loadSpriteAndActorDefinitions(String file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		getResourceDefinitionLoader().loadSpriteAndActorDefinitions(file, sprites, sequences, sequenceInstances, actors);
	}

	public SoundHandler loadSound(String ressource) throws IOException
	{
		// FIXME to do...
		return new SoundHandler(0);
	}

	public void playSound(SoundHandler sound)
	{
		// FIXME to do...

	}

}
