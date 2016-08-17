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

import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.canvas.Point;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinition;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinitionUtils;
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

	private static final String CANVAS_GUID__DISPLAY = "main";

	private static final String CANVAS_GUID__TILESET = "spritesheet";
	
	private static final int GRID_SIZE = 32 ;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new Demo().init();
		}
		catch (CanvasException | ParsingErrorException _exception)
		{
			_exception.printStackTrace();
		}

	}

	/**
	 * Canvas manager, for a qHD landscape screen.
	 */
	private final BufferedImagesManager myCanvasManager = new BufferedImagesManager(960, 540);

	private void init() throws CanvasException, ParsingErrorException
	{
		CanvasUtils.createBlackFilledCanvas(myCanvasManager, CANVAS_GUID__DISPLAY);
		CanvasUtils.createCanvasFromImage(myCanvasManager, CANVAS_GUID__TILESET, "classpath:demo/spritesheet.png");

		int _cidTileset = myCanvasManager.getCanvasId(CANVAS_GUID__TILESET);
		int _cidDisplay = myCanvasManager.getCanvasId(CANVAS_GUID__DISPLAY);
		List<SpriteDefinition> _sprites = new SpriteDecoder().decode("tileGrid 4x1:0,0,32,32");

		SpriteDefinition[] _tiles =
		{
				_sprites.get(0), _sprites.get(0), _sprites.get(0), _sprites.get(1)
		};
		Random _rand = new Random(System.currentTimeMillis());
		Point _to = new Point();
		int _y = 0;
		_to.setY(_y);
		int _colspan = myCanvasManager.getScreenWidth()/GRID_SIZE + 1 ;
		int _rowspan = myCanvasManager.getScreenHeight()/GRID_SIZE + 1 ;
		for (int _row = 0; _row < _rowspan; _row++)
		{
			int _x = 0;
			_to.setX(_x);
			for (int _col = 0; _col < _colspan; _col++)
			{
				int _sprite = _rand.nextInt(_tiles.length);
				SpriteDefinitionUtils.copySpriteBloc(_tiles[_sprite], _to, myCanvasManager, _cidTileset, _cidDisplay);
				_x += GRID_SIZE;
				_to.setX(_x);
			}
			_y += GRID_SIZE;
			_to.setY(_y);
		}
		
		//test transparency mode
		myCanvasManager.copy(_cidTileset, _sprites.get(2).getSourceBox(), _cidDisplay, new Point().withX(40).withY(40));
		myCanvasManager.replace(_cidTileset, _sprites.get(2).getSourceBox(), _cidDisplay, new Point().withX(104).withY(40));

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
		_view.setCanvasId(myCanvasManager.getCanvasId(CANVAS_GUID__DISPLAY));

		JFrame f = new JFrame("Canvas Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(_view);
		f.pack();
		f.setVisible(true);
	}

}
