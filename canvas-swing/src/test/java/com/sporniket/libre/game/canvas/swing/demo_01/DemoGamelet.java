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
 * Demo gamelet : that use
 * {@link BoxCopyMachine#clear(com.sporniket.libre.game.canvas.Box, int, Point)}.
 * 
 * @author dsporn
 *
 */
public class DemoGamelet extends DefaultCanvasGamelet<BufferedImage> {
	/**
	 * Simple grid based moving caterpillar.
	 * 
	 * @author dsporn
	 *
	 */
	static final class Caterpillar {
		/**
		 * number of active bodys in {@link #myBodys}.
		 * 
		 * start from 0.
		 */
		private int myBodyCount ;
		
		/**
		 * Maximum number of bodys.
		 */
		private int myBodyMax ;

		private final Point[] myBodys;

		private final Point myCurrentPosition = new Point();

		private int myDx;

		private int myDy;

		private final int myGridSize;

		/**
		 * Index in {@link #myBodys} of the head body.
		 */
		private int myHeadIndex ;

		private final int myHorizontalLimit;

		/**
		 * Index in {@link #myBodys} of the tail body.
		 * 
		 * at most, <code>headIndex = (tailIndex + 9) % bodys.length</code>
		 */
		private int myTailIndex ;
		
		private final int myVerticalLimit;
		
		public Caterpillar(int bodyCount, int gridSize, int horizontalLimit, int verticalLimit) {
			myBodys = new Point[bodyCount];
			setBodyMax(bodyCount - 2);
			myHorizontalLimit = horizontalLimit;
			myVerticalLimit = verticalLimit;
			myGridSize = gridSize;
			
			reset();
		}

		public int getBodyCount() {
			return myBodyCount;
		}

		public int getBodyMax() {
			return myBodyMax;
		}

		public Point[] getBodys() {
			return myBodys;
		}

		public Point getCurrentPosition() {
			return myCurrentPosition;
		}

		public int getDx() {
			return myDx;
		}
		
		public int getDy() {
			return myDy;
		}

		public int getGridSize() {
			return myGridSize;
		}

		public Point getHead()
		{
			return getBodys()[getHeadIndex()] ;
		}
		
		public int getHeadIndex() {
			return myHeadIndex;
		}
		
		public int getHorizontalLimit() {
			return myHorizontalLimit;
		}

		public Point getTail()
		{
			return getBodys()[getTailIndex()] ;
		}

		public int getTailIndex() {
			return myTailIndex;
		}

		public int getVerticalLimit() {
			return myVerticalLimit;
		}

		private int incrementIndex(int index) {
			return (index +1)%getBodys().length;
		}

		public void move()
		{
			move_updatePosition();
			move_updateBodys();
		}

		private void move_updateBodys() {
			setHeadIndex(incrementIndex(getHeadIndex()));
			recordHead();
		}

		private void move_updatePosition() {
			int _nextX = getCurrentPosition().getX() + getDx();
			if((_nextX < 0)||(_nextX >= getHorizontalLimit()))
			{
				setDx(-getDx());
				_nextX = getCurrentPosition().getX() + getDx();
			}
			int _nextY = getCurrentPosition().getY() + getDy();
			if((_nextY < 0)||(_nextY >= getVerticalLimit()))
			{
				setDy(-getDy());
				_nextY = getCurrentPosition().getY() + getDy();
			}
			getCurrentPosition().withX(_nextX).withY(_nextY);
		}
		
		private void recordHead() {
			getBodys()[getHeadIndex()] = new Point().withX(getCurrentPosition().getX()).withY(getCurrentPosition().getY()) ;
			setBodyCount(getBodyCount()+1);
			if(getBodyCount() > getBodyMax())
			{
				setTailIndex(incrementIndex(getTailIndex()));
				setBodyCount(getBodyCount()-1);
			}
		}

		public void render(CanvasManager<BufferedImage> canvasManager, int cidSprite, SpriteDefinition bodySprite, int cidTo)
		{
			//display the actual body
			canvasManager.copy(cidSprite, bodySprite.getSourceBox(), cidTo, getHead());
			//remove the latest body
			if(getBodyCount() >= getBodyMax())
			{
				canvasManager.clear(bodySprite.getSourceBox(), cidTo, getTail());
			}
		}

		public void reset()
		{
			setBodyCount(0);
			setHeadIndex(0);
			setTailIndex(0);
			getCurrentPosition().withX(0).withY(3 * getGridSize());
			recordHead();
			setDx(getGridSize());
			setDy(getGridSize());
		}

		public void setBodyCount(int bodyCount) {
			myBodyCount = bodyCount;
		}

		public void setBodyMax(int bodyMax) {
			myBodyMax = bodyMax;
		}

		public void setDx(int dx) {
			myDx = dx;
		}

		public void setDy(int dy) {
			myDy = dy;
		}

		public void setHeadIndex(int headIndex) {
			myHeadIndex = headIndex;
		}

		public void setTailIndex(int tailIndex) {
			myTailIndex = tailIndex;
		}
	}

	static final int BODY_COUNT = 10;

	static final String CANVAS_GUID__BACKGROUND = "main";

	static final String CANVAS_GUID__PLAYGROUND = "play";

	static final String CANVAS_GUID__TILESET = "tileset";

	static final int GRID_SIZE = 32;

	static final int MAP_COLS = 40;

	static final int MAP_ROWS = 40;
	
	static final int SPRITE_INDEX__BODY = 1 ;
	
	static final int TIME_BETWEEN_MOVES = 80 ;

	private final Render<CanvasGameletContext<BufferedImage>> MY_RENDER_EVENT = new Render<CanvasGameletContext<BufferedImage>>(
			this);

	private Caterpillar myCaterpillar ;

	/**
	 * Store the last id of the offscreen canvas to draw into, to check if it is
	 * required to redraw all the tiles.
	 */
	private int myLastCanvasId = -1;

	private final int[][] myMap = new int[MAP_ROWS][MAP_COLS];

	List<SpriteDefinition> mySprites;
	
	SpriteDefinition[] myTilePool;
	
	private long myTimeSinceLastMove ;

	public Caterpillar getCaterpillar() {
		return myCaterpillar;
	}

	private int getLastCanvasId() {
		return myLastCanvasId;
	}

	private List<SpriteDefinition> getSprites() {
		return mySprites;
	}

	private SpriteDefinition[] getTilePool() {
		return myTilePool;
	}

	public long getTimeSinceLastMove() {
		return myTimeSinceLastMove;
	}

	@Override
	protected void init(CanvasGameletContext<BufferedImage> context) throws GameletException {
		CanvasManager<BufferedImage> _canvasManager = context.getCanvasManager();
		int _cidPlayground = _canvasManager.getCanvasId(CANVAS_GUID__PLAYGROUND);
		_canvasManager.clear(_canvasManager.getScreenBox(), _cidPlayground, _canvasManager.getScreenCornerTopLeft());

		setTimeSinceLastMove(0);
		setCaterpillar(new Caterpillar(BODY_COUNT, GRID_SIZE, _canvasManager.getScreenWidth(), _canvasManager.getScreenHeight()));
		// "load" the tilemap
		init__loadMap();
		try {
			setSprites(new SpriteDecoder().decode("tileGrid 4x1:0,0,32,32"));
			List<SpriteDefinition> _sprites = getSprites();
			setTilePool(new SpriteDefinition[] { _sprites.get(0), _sprites.get(0), _sprites.get(0), _sprites.get(1) });

			int _cidDisplay = _canvasManager.getCanvasId(CANVAS_GUID__BACKGROUND);
			if (getLastCanvasId() != _cidDisplay) {
				regenerateBackground(_canvasManager, _cidDisplay);
				setLastCanvasId(_cidDisplay);
			}
		} catch (ParsingErrorException _exception) {
			throw new GameletException(_exception);
		}

		requestRender();
	}

	/**
	 * 
	 */
	private void init__loadMap() {
		// only background tiles.
		for (int _row = 0; _row < MAP_ROWS; _row++) {
			for (int _col = 0; _col < MAP_COLS; _col++) {
				myMap[_row][_col] = 0;
			}
		}
	}

	/**
	 * @param canvasManager
	 * @param cidBackground
	 */
	private void regenerateBackground(CanvasManager<BufferedImage> canvasManager, int cidBackground) {
		int _cidTileset = canvasManager.getCanvasId(CANVAS_GUID__TILESET);
		Point _to = new Point();
		int _y = 0;
		_to.setY(_y);
		int _colspan = canvasManager.getScreenWidth() / GRID_SIZE + 1;
		int _rowspan = canvasManager.getScreenHeight() / GRID_SIZE + 1;
		for (int _row = 0; _row < _rowspan; _row++) {
			int _x = 0;
			_to.setX(_x);
			for (int _col = 0; _col < _colspan; _col++) {
				int _sprite = myMap[_row][_col];
				SpriteDefinitionUtils.copySpriteBloc(getTilePool()[_sprite], _to, (BoxCopyMachine) canvasManager,
						_cidTileset, cidBackground);
				_x += GRID_SIZE;
				_to.setX(_x);
			}
			_y += GRID_SIZE;
			_to.setY(_y);
		}
	}

	@Override
	public void render(CanvasGameletContext<BufferedImage> context, int cidDestination, int cidPreviousRender) {
		CanvasManager<BufferedImage> _canvasManager = context.getCanvasManager();

		int _cidTileset = _canvasManager.getCanvasId(CANVAS_GUID__TILESET);
		int _cidPlayground = _canvasManager.getCanvasId(CANVAS_GUID__PLAYGROUND);
		getCaterpillar().render(_canvasManager, _cidTileset, getSprites().get(SPRITE_INDEX__BODY), _cidPlayground);

		int _cidBackground = _canvasManager.getCanvasId(CANVAS_GUID__BACKGROUND);
		_canvasManager.copy(_cidBackground, _canvasManager.getScreenBox(), cidDestination,
				_canvasManager.getScreenCornerTopLeft());
		_canvasManager.copy(_cidPlayground, _canvasManager.getScreenBox(), cidDestination,
				_canvasManager.getScreenCornerTopLeft());
	}

	/**
	 * @throws GameletException
	 */
	private void requestRender() throws GameletException {
		fireRenderEvent(MY_RENDER_EVENT);
	}

	@Override
	protected void rewind(CanvasGameletContext<BufferedImage> context) throws GameletException {
		getCaterpillar().reset();
		setTimeSinceLastMove(0);
		requestRender();
	}

	@Override
	protected void run(long elapsedTime, CanvasGameletContext<BufferedImage> context) throws GameletException {
		setTimeSinceLastMove(getTimeSinceLastMove()+elapsedTime);
		boolean _needRender = false ;
		while (getTimeSinceLastMove() > TIME_BETWEEN_MOVES)
		{
			_needRender = true ;
			getCaterpillar().move();
			setTimeSinceLastMove(getTimeSinceLastMove() - TIME_BETWEEN_MOVES);
		}
		if (_needRender)
		{
			requestRender();
		}
	}

	public void setCaterpillar(Caterpillar caterpillar) {
		myCaterpillar = caterpillar;
	}

	private void setLastCanvasId(int lastCanvasId) {
		myLastCanvasId = lastCanvasId;
	}

	private void setSprites(List<SpriteDefinition> sprites) {
		mySprites = sprites;
	}

	private void setTilePool(SpriteDefinition[] tilePool) {
		myTilePool = tilePool;
	}

	public void setTimeSinceLastMove(long timeSinceLastMove) {
		myTimeSinceLastMove = timeSinceLastMove;
	}

}