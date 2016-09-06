/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sporniket.libre.game.canvas.Box;
import com.sporniket.libre.game.canvas.BoxCopyMachine;
import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.canvas.CanvasManager;
import com.sporniket.libre.game.canvas.Point;
import com.sporniket.libre.game.canvas.gamelet.CanvasGamelet;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletContext;
import com.sporniket.libre.game.canvas.gamelet.DefaultCanvasGamelet;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinition;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinitionUtils;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.pal.codec.ParsingErrorException;
import com.sporniket.libre.game.pal.codec.SpriteDecoder;

/**
 * Demo application of a canvas.
 * 
 * @author dsporn
 *
 */
public class Demo
{
	private static class DemoGamelet extends DefaultCanvasGamelet<BufferedImage>
	{
		List<SpriteDefinition> mySprites;

		SpriteDefinition[] myTilePool;

		final Random myRand = new Random(System.currentTimeMillis());

		/**
		 * Store the last id of the offscreen canvas to draw into, to check if it is required to redraw all the tiles.
		 */
		int myLastCanvasId = -1;

		@Override
		protected void doInit(CanvasGameletContext<BufferedImage> context) throws GameletException
		{
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

			}
			catch (ParsingErrorException _exception)
			{
				throw new GameletException(_exception);
			}
		}

		private List<SpriteDefinition> getSprites()
		{
			return mySprites;
		}

		private void setSprites(List<SpriteDefinition> sprites)
		{
			mySprites = sprites;
		}

		private SpriteDefinition[] getTilePool()
		{
			return myTilePool;
		}

		private void setTilePool(SpriteDefinition[] tilePool)
		{
			myTilePool = tilePool;
		}

		private Random getRand()
		{
			return myRand;
		}

		@Override
		public void render(CanvasManager<BufferedImage> canvasManager, int cidDestination, int cidPreviousRender)
		{
			int _cidBackground = canvasManager.getCanvasId(CANVAS_GUID__BACKGROUND);

			((BoxCopyMachine) canvasManager).copy(_cidBackground, canvasManager.getScreenBox(), cidDestination,
					canvasManager.getScreenCornerTopLeft());
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
					int _sprite = getRand().nextInt(getTilePool().length);
					SpriteDefinitionUtils.copySpriteBloc(getTilePool()[_sprite], _to, (BoxCopyMachine) canvasManager, _cidTileset,
							cidBackground);
					_x += GRID_SIZE;
					_to.setX(_x);
				}
				_y += GRID_SIZE;
				_to.setY(_y);
			}
		}

		private int getLastCanvasId()
		{
			return myLastCanvasId;
		}

		private void setLastCanvasId(int lastCanvasId)
		{
			myLastCanvasId = lastCanvasId;
		}

	}

	private static final String CANVAS_GUID__BACKGROUND = "main";

	private static final String CANVAS_GUID__SCREEN = "screen0";

	private static final String CANVAS_GUID__TILESET = "spritesheet";

	private static final int GRID_SIZE = 32;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new Demo().init();
		}
		catch (CanvasException | ParsingErrorException | GameletException _exception)
		{
			_exception.printStackTrace();
		}

	}

	/**
	 * Canvas manager, for a qHD landscape screen.
	 */
	private final BufferedImagesManager myCanvasManager = new BufferedImagesManager(960, 540);

	private void init() throws CanvasException, ParsingErrorException, GameletException
	{
		CanvasUtils.createBlackFilledCanvas(myCanvasManager, CANVAS_GUID__SCREEN);
		CanvasUtils.createBlackFilledCanvas(myCanvasManager, CANVAS_GUID__BACKGROUND);
		CanvasUtils.createCanvasFromImage(myCanvasManager, CANVAS_GUID__TILESET, "classpath:demo/spritesheet.png");

		int _cidDisplay = myCanvasManager.getCanvasId(CANVAS_GUID__SCREEN);

		DemoGamelet _gamelet = new DemoGamelet();
		CanvasGameletContext<BufferedImage> _context = new CanvasGameletContext<BufferedImage>();
		_context.setCanvasManager(myCanvasManager);
		_gamelet.init(_context);
		_gamelet.render(myCanvasManager, _cidDisplay, -1);

		// test transparency mode
		// myCanvasManager.copy(_cidTileset, _sprites.get(2).getSourceBox(), _cidDisplay, new Point().withX(40).withY(40));
		// myCanvasManager.replace(_cidTileset, _sprites.get(2).getSourceBox(), _cidDisplay, new Point().withX(104).withY(40));

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				init__createAndShowGui();
			}
		});
	}

	private void init__createAndShowGui()
	{
		CanvasView _view = new CanvasView();
		_view.setCanvasManager(myCanvasManager);
		_view.setCanvasId(myCanvasManager.getCanvasId(CANVAS_GUID__BACKGROUND));

		JFrame f = new JFrame("Canvas Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(_view);
		f.pack();
		f.setVisible(true);
	}

}
