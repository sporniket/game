package com.sporniket.libre.game.canvas.swing.demo_00;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.sporniket.libre.game.canvas.BoxCopyMachine;
import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.canvas.Point;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletContext;
import com.sporniket.libre.game.canvas.gamelet.DefaultCanvasGamelet;
import com.sporniket.libre.game.canvas.sprite.SpriteDecoder;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinition;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinitionUtils;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.input.Pointer;
import com.sporniket.libre.game.pal.codec.ParsingErrorException;

/**
 * Demo gamelet : show a tile based map, and a draggable object.
 * 
 * @author dsporn
 *
 */
public class DemoGamelet extends DefaultCanvasGamelet<BufferedImage>
{
	private static final String NAME__GRID_SIZE = "gridSize";

	static final String CANVAS_GUID__BACKGROUND = "main";

	static final String CANVAS_GUID__TILESET = "tileset";

	static final int GRID_SIZE = 32;

	static final int MAP_COLS = 40;

	static final int MAP_ROWS = 40;

	private boolean myDragging;

	private int myGridSize = GRID_SIZE;

	/**
	 * Store the last id of the offscreen canvas to draw into, to check if it is required to redraw all the tiles.
	 */
	private int myLastCanvasId = -1;

	private int[][] myMap ;

	private int myMapCols = MAP_COLS;

	private int myMapRows = MAP_ROWS;

	/**
	 * delta between the position and the mouse pointer, when dragging.
	 */
	private final Point myPointerDelta = new Point().withX(0).withY(0);

	/**
	 * position of the draggable object.
	 */
	private final Point myPosition = new Point().withX(0).withY(0);

	private final Random myRand = new Random(System.currentTimeMillis());

	List<SpriteDefinition> mySprites;

	SpriteDefinition[] myTilePool;

	private int getGridSize()
	{
		return myGridSize;
	}

	private int getLastCanvasId()
	{
		return myLastCanvasId;
	}

	private int[][] getMap()
	{
		return myMap;
	}

	private int getMapCols()
	{
		return myMapCols;
	}

	private int getMapRows()
	{
		return myMapRows;
	}

	private Point getPointerDelta()
	{
		return myPointerDelta;
	}

	private Point getPosition()
	{
		return myPosition;
	}

	private Random getRand()
	{
		return myRand;
	}

	private List<SpriteDefinition> getSprites()
	{
		return mySprites;
	}

	private SpriteDefinition[] getTilePool()
	{
		return myTilePool;
	}

	@Override
	protected void init(CanvasGameletContext<BufferedImage> context) throws GameletException
	{
		//init rendering parameters
		init__renderingParameters(context);
		
		// "load" the tilemap
		init__loadMap();
		try
		{
			setSprites(new SpriteDecoder().decode("tileGrid 4x1:0,0,32,32"));
			List<SpriteDefinition> _sprites = getSprites();
			setTilePool(new SpriteDefinition[]
			{
					_sprites.get(0),
					_sprites.get(0),
					_sprites.get(0),
					_sprites.get(1)
			});

			CanvasManager<BufferedImage> _canvasManager = context.getCanvasManager();
			int _cidDisplay = _canvasManager.getCanvasId(CANVAS_GUID__BACKGROUND);
			if (getLastCanvasId() != _cidDisplay)
			{
				regenerateBackground(_canvasManager, _cidDisplay);
				setLastCanvasId(_cidDisplay);
			}

			requestRender();

		}
		catch (ParsingErrorException _exception)
		{
			throw new GameletException(_exception);
		}
	}

	private void init__renderingParameters(CanvasGameletContext<BufferedImage> context)
	{
		setGridSize((int) context.getData().get(NAME__GRID_SIZE));
		int _screenWidth = context.getCanvasManager().getScreenWidth();
		int _mapCols = _screenWidth/getGridSize();
		if (0 != _screenWidth % _mapCols)
		{
			_mapCols++;
		}
		setMapCols(_mapCols);
		setMapRows(getMapCols());
		setMap(new int[getMapRows()][getMapCols()]);
	}

	/**
	 * 
	 */
	private void init__loadMap()
	{
		int[] _valueMap =
		{
				0,
				0,
				0,
				1
		}; // ratio : 3 background tiles for one wall tile
		for (int _row = 0; _row < getMapRows(); _row++)
		{
			for (int _col = 0; _col < getMapCols(); _col++)
			{
				myMap[_row][_col] = getRand().nextInt(_valueMap.length);
			}
		}
	}

	/**
	 * @return <code>true</code> when dragging the test object is activated.
	 */
	private boolean isDragging()
	{
		return myDragging;
	}

	/**
	 * @param canvasManager
	 * @param cidBackground
	 */
	private void regenerateBackground(CanvasManager<BufferedImage> canvasManager, int cidBackground)
	{
		int _cidTileset = canvasManager.getCanvasId(CANVAS_GUID__TILESET);
		Point _to = new Point();
		int _y = 0;
		_to.setY(_y);
		int _colspan = getMapCols();
		int _rowspan = getMapRows();
		for (int _row = 0; _row < _rowspan; _row++)
		{
			int _x = 0;
			_to.setX(_x);
			for (int _col = 0; _col < _colspan; _col++)
			{
				int _sprite = myMap[_row][_col];
				SpriteDefinitionUtils.copySpriteBloc(getTilePool()[_sprite], _to, (BoxCopyMachine) canvasManager, _cidTileset,
						cidBackground);
				_x += getGridSize();
				_to.setX(_x);
			}
			_y += getGridSize();
			_to.setY(_y);
		}
	}

	@Override
	public void render(CanvasGameletContext<BufferedImage> context, int cidDestination, int cidPreviousRender)
	{
		CanvasManager<BufferedImage> _canvasManager = context.getCanvasManager();
		int _cidBackground = _canvasManager.getCanvasId(CANVAS_GUID__BACKGROUND);

		_canvasManager.copy(_cidBackground, _canvasManager.getScreenBox(), cidDestination, _canvasManager.getScreenCornerTopLeft());

		// test transparency mode
		int _cidTileset = _canvasManager.getCanvasId(CANVAS_GUID__TILESET);
		_canvasManager.copy(_cidTileset, getSprites().get(2).getSourceBox(), cidDestination,
				new Point().withX(getPosition().getX()).withY(getPosition().getY()));
		_canvasManager.replace(_cidTileset, getSprites().get(2).getSourceBox(), cidDestination,
				new Point().withX(getPosition().getX() + 64).withY(getPosition().getY()));

	}

	@Override
	protected void rewind(CanvasGameletContext<BufferedImage> context) throws GameletException
	{
		getPosition().withX(0).withY(0);
		setDragging(false);
	}

	@Override
	protected void run(long elapsedTime, CanvasGameletContext<BufferedImage> context) throws GameletException
	{
		// process mouse event (drag using left click only ==> pointer index = 0)
		boolean _isUpdated = run__processPointers(context);
		if (_isUpdated)
		{
			requestRender();
		}
	}

	/**
	 * Process mouse events (drag using left click only ==> pointer index = 0).
	 * 
	 * @param context
	 *            the game context.
	 * @return <code>true</code> if the display has been updated.
	 */
	private boolean run__processPointers(CanvasGameletContext<BufferedImage> context)
	{
		boolean _isUpdated = false;
		for (Pointer _pointer : context.getPointerLog())
		{
			if (0 == _pointer.getIndex() && (_pointer.getState() == com.sporniket.libre.game.input.Pointer.State.PRESSED
					|| _pointer.getState() == com.sporniket.libre.game.input.Pointer.State.NOT_PRESSED
					|| _pointer.getState() == com.sporniket.libre.game.input.Pointer.State.UNDEFINED))
			{
				// update dragging state
				if (!isDragging() && _pointer.getState() == com.sporniket.libre.game.input.Pointer.State.PRESSED)
				{
					int _deltaX = _pointer.getX() - getPosition().getX();
					int _deltaY = _pointer.getY() - getPosition().getY();
					if (_deltaX >= 0 && _deltaX < GRID_SIZE && _deltaY >= 0 && _deltaY < getGridSize())
					{
						getPointerDelta().withX(_deltaX).withY(_deltaY);
						setDragging(true);
					}
				}
				if (isDragging() && _pointer.getState() != com.sporniket.libre.game.input.Pointer.State.PRESSED)
				{
					setDragging(false);
				}

				// update position
				if (isDragging())
				{
					getPosition().withX(_pointer.getX() - getPointerDelta().getX())
							.withY(_pointer.getY() - getPointerDelta().getY());
					_isUpdated = true;
				}

			}
		}
		return _isUpdated;
	}

	private void setDragging(boolean dragging)
	{
		myDragging = dragging;
	}

	private void setGridSize(int gridSize)
	{
		myGridSize = gridSize;
	}

	private void setLastCanvasId(int lastCanvasId)
	{
		myLastCanvasId = lastCanvasId;
	}

	private void setMap(int[][] map)
	{
		myMap = map;
	}

	private void setMapCols(int mapCols)
	{
		myMapCols = mapCols;
	}

	private void setMapRows(int mapRows)
	{
		myMapRows = mapRows;
	}

	private void setSprites(List<SpriteDefinition> sprites)
	{
		mySprites = sprites;
	}

	private void setTilePool(SpriteDefinition[] tilePool)
	{
		myTilePool = tilePool;
	}

}