package com.sporniket.libre.game.canvas.swing.demo_01;

import java.awt.image.BufferedImage;
import java.util.List;

import com.sporniket.libre.game.canvas.BoxCopyMachine;
import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.canvas.Point;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletContext;
import com.sporniket.libre.game.canvas.gamelet.DefaultCanvasGamelet;
import com.sporniket.libre.game.canvas.sprite.SpriteDecoder;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinition;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinitionUtils;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.gamelet.events.Render;
import com.sporniket.libre.game.pal.codec.ParsingErrorException;

/**
 * Demo gamelet : show a tile based map, and a draggable object.
 * 
 * @author dsporn
 *
 */
public class DemoGamelet extends DefaultCanvasGamelet<BufferedImage>
{
	static final String CANVAS_GUID__BACKGROUND = "main";

	static final String CANVAS_GUID__PLAYGROUND = "play";

	static final String CANVAS_GUID__TILESET = "tileset";

	static final int GRID_SIZE = 32;

	static final int MAP_COLS = 40;

	static final int MAP_ROWS = 40;

	private final Render<CanvasGameletContext<BufferedImage>> MY_RENDER_EVENT = new Render<CanvasGameletContext<BufferedImage>>(this);

	/**
	 * Store the last id of the offscreen canvas to draw into, to check if it is required to redraw all the tiles.
	 */
	private int myLastCanvasId = -1;

	private final int[][] myMap = new int[MAP_ROWS][MAP_COLS];

	List<SpriteDefinition> mySprites;

	SpriteDefinition[] myTilePool;

	@Override
	public void render(CanvasGameletContext<BufferedImage> context, int cidDestination, int cidPreviousRender)
	{
		CanvasManager<BufferedImage> _canvasManager = context.getCanvasManager();
		int _cidBackground = _canvasManager.getCanvasId(CANVAS_GUID__BACKGROUND);

		_canvasManager.copy(_cidBackground, _canvasManager.getScreenBox(), cidDestination, _canvasManager.getScreenCornerTopLeft());
	}

	@Override
	protected void init(CanvasGameletContext<BufferedImage> context) throws GameletException
	{
		// "load" the tilemap
		init__loadMap();
		try
		{
			setSprites(new SpriteDecoder().decode("tileGrid 4x1:0,0,32,32"));
			List<SpriteDefinition> _sprites = getSprites();
			setTilePool(new SpriteDefinition[]
			{
					_sprites.get(0), _sprites.get(0), _sprites.get(0), _sprites.get(1)
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

	private int getLastCanvasId()
	{
		return myLastCanvasId;
	}

	private List<SpriteDefinition> getSprites()
	{
		return mySprites;
	}

	private SpriteDefinition[] getTilePool()
	{
		return myTilePool;
	}

	/**
	 * 
	 */
	private void init__loadMap()
	{
		// only background tiles.
		for (int _row = 0; _row < MAP_ROWS; _row++)
		{
			for (int _col = 0; _col < MAP_COLS; _col++)
			{
				myMap[_row][_col] = 0;
			}
		}
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
		int _colspan = canvasManager.getScreenWidth() / GRID_SIZE + 1;
		int _rowspan = canvasManager.getScreenHeight() / GRID_SIZE + 1;
		for (int _row = 0; _row < _rowspan; _row++)
		{
			int _x = 0;
			_to.setX(_x);
			for (int _col = 0; _col < _colspan; _col++)
			{
				int _sprite = myMap[_row][_col];
				SpriteDefinitionUtils.copySpriteBloc(getTilePool()[_sprite], _to, (BoxCopyMachine) canvasManager, _cidTileset,
						cidBackground);
				_x += GRID_SIZE;
				_to.setX(_x);
			}
			_y += GRID_SIZE;
			_to.setY(_y);
		}
	}

	/**
	 * @throws GameletException
	 */
	private void requestRender() throws GameletException
	{
		fireRenderEvent(MY_RENDER_EVENT);
	}

	private void setLastCanvasId(int lastCanvasId)
	{
		myLastCanvasId = lastCanvasId;
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