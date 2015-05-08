/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.sporniket.libre.game.api.ResourceDefinitionLocator;
import com.sporniket.libre.game.api.sprite.Actor;
import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.SequenceInstance;
import com.sporniket.libre.game.api.sprite.Sprite;
import com.sporniket.libre.game.api.sprite.SpriteBank;
import com.sporniket.libre.game.api.types.BlocDefinition;
import com.sporniket.libre.game.api.types.CopyMode;
import com.sporniket.libre.game.api.types.Position.Vector;
import com.sporniket.libre.game.papi.BufferingStrategy;
import com.sporniket.libre.game.papi.Game;
import com.sporniket.libre.game.papi.InputAbstractionLayerInterface;
import com.sporniket.libre.game.papi.MessageSenderException;
import com.sporniket.libre.game.papi.PictureHandler;
import com.sporniket.libre.game.papi.PictureHandler.Features;
import com.sporniket.libre.game.papi.PointerStateProviderSupport;
import com.sporniket.libre.game.papi.SoundHandler;
import com.sporniket.libre.game.papi.TimeAbstractionLayerInterface;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Implementation for the platform.
 * 
 * To be ready, this implementation require an android {@link Context}.
 * 
 * Some implementations are API level dependant, this implementation support API level 10 (2.3.3/2.3.4).
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Android</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Android</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
public class GamePlatform extends PlatformAdapter
{
	private static final int BUFFER_COUNT = BufferingStrategy.TRIPLE_BUFFERING.getBufferCount();

	private static final String TAG = GamePlatform.class.getSimpleName();

	// FIXME supprimer cette classe
	private static class AndroidPictureHandler extends PictureHandler
	{
		private GamePlatform myHost;

		/**
		 * @param value
		 * @param width
		 * @param height
		 */
		public AndroidPictureHandler(int value, int width, int height, GamePlatform host)
		{
			super(value, width, height);
			setHost(host);
		}

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
			Bitmap _data = getHost().getImages().get(getValue()).getData();
			return _data.getPixel(x, y);
		}

		/**
		 * @return the host
		 */
		private GamePlatform getHost()
		{
			return myHost;
		}

		/**
		 * @param host
		 *            the host to set
		 */
		private void setHost(GamePlatform host)
		{
			myHost = host;
		}

	}

	private static final class AndroidAssetResourceDefinitionLocator implements ResourceDefinitionLocator
	{

		private static final String DATA_PATH = "data";

		private Context myContext;

		private File myBaseDir = new File(DATA_PATH);

		/**
		 * @param context
		 */
		public AndroidAssetResourceDefinitionLocator(Context context)
		{
			super();
			myContext = context;
		}

		public InputStream getInputStream(String location) throws IOException
		{
			return myContext.getAssets().open(new File(myBaseDir, location).getPath());
		}
	}

	// private TimeAbstractionLayerInterface myTime = new TimeAbstractionLayer();
	//
	// /**
	// * @return the time
	// */
	// public TimeAbstractionLayerInterface getTime()
	// {
	// return myTime;
	// }

	/**
	 * Structure to caching a bitmap and it's associated canvas.
	 * 
	 * The bitmap is recycled when the activity stops (onStop()).
	 * 
	 * @author dsporn
	 * 
	 */
	private static class OffScreen
	{
		/**
		 * Drawing agent.
		 */
		private Canvas myCanvas = null;

		/**
		 * Image data.
		 */
		private Bitmap myData;

		/**
		 * @param data
		 */
		public OffScreen(Bitmap data)
		{
			setData(data);
		}

		/**
		 * @param data
		 */
		void setData(Bitmap data)
		{
			myData = data;
			if (null != data && data.isMutable())
			{
				myCanvas = new Canvas(myData);
			}
			else
			{
				myCanvas = null;
			}
		}

		/**
		 * @return <code>true</code> if the Offscreen can be modified by draw operations.
		 */
		public boolean isDrawable()
		{
			return true;
		}

		/**
		 * @return the canvas
		 */
		public Canvas getCanvas()
		{
			return myCanvas;
		}

		/**
		 * @return the data
		 */
		public Bitmap getData()
		{
			return myData;
		}

	}

	/**
	 * Extension for the Android platform.
	 * 
	 * When the activity stops, the bitmap are recycled, this extension store the path of the bitmap file in order to reload it. If
	 * there are no path, the bitmap was created using {@link GamePlatform#createOffscreenPicture()} or
	 * {@link GamePlatform#createOffscreenPicture(int, int)}.
	 * 
	 * @author dsporn
	 * 
	 */
	private static class PictureHandlerAndroid extends PictureHandler
	{
		private String mySource = null;

		public PictureHandlerAndroid(int value, int width, int height, GamePlatform host)
		{
			super(value, width, height);
			setHost(host);
		}

		public PictureHandlerAndroid(int value, int width, int height, String source, GamePlatform host)
		{
			this(value, width, height, host);
			mySource = source;
		}

		/**
		 * @return the source
		 */
		public String getSource()
		{
			return mySource;
		}

		private GamePlatform myHost;

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
			Bitmap _data = getHost().getImages().get(getValue()).getData();
			return _data.getPixel(x, y);
		}

		/**
		 * @return the host
		 */
		private GamePlatform getHost()
		{
			return myHost;
		}

		/**
		 * @param host
		 *            the host to set
		 */
		private void setHost(GamePlatform host)
		{
			myHost = host;
		}

	}

	private static class SoundHandlerAndroid extends SoundHandler
	{
		private String mySource;

		/**
		 * @param value
		 * @param source
		 */
		public SoundHandlerAndroid(Integer value, String source)
		{
			super(value);
			mySource = source;
		}

		/**
		 * @return the source
		 */
		private String getSource()
		{
			return mySource;
		}

		/**
		 * Set the sound value again (after a reload).
		 * 
		 * @param value
		 */
		public void resetValue(int value)
		{
			setValue(value);
		}

	}

	private SoundPool mySoundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);

	/**
	 * Singleton instance.
	 */
	private static final GamePlatform INSTANCE = new GamePlatform();

	/**
	 * Access to the singleton.
	 * 
	 * @return the instance
	 */
	public static GamePlatform getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Context for getting the assets and other things.
	 */
	private Context myAndroidContext;

	/**
	 * Drawing to the screen will be directed to this offscreen.
	 * 
	 * @see #myOffscreens
	 */
	private int myCurrentOffscreen = 0;

	/**
	 * The base path, according to the screen features, of the image.
	 */
	private String myImageBasePath;

	/**
	 * Image pool, {@link PictureHandler} reference the index of an {@link OffScreen}.
	 */
	private List<OffScreen> myImages = new ArrayList<GamePlatform.OffScreen>();

	/**
	 * Offscreens management for triple buffering.
	 */
	private OffScreen[] myOffscreens = new OffScreen[BUFFER_COUNT];

	/**
	 * Offscreens picture handlers, for quick display pictures.
	 */
	private PictureHandlerAndroid[] myOffscreensPictureHandlers = new PictureHandlerAndroid[BUFFER_COUNT];

	/**
	 * Rendering the screen will be from this offscreen.
	 * 
	 * @see #myOffscreens
	 */
	private int myRenderedOffscreen = 0;

	ResourceDefinitionLocator myResourceLocator;

	ScreenFeatureSet myScreenFeatures = null;

	/**
	 * flag to know if the platform can do a call to the redraw.
	 */
	private boolean myRedrawPossible = false;

	// ============================================================================================================
	public void commitDisplay()
	{
		// cycle offscreens
		myRenderedOffscreen = myCurrentOffscreen;
		++myCurrentOffscreen;
		if (myCurrentOffscreen >= myOffscreens.length)
		{
			myCurrentOffscreen = 0;
		}

		// FIXME lock canvas, draw, unlock canvas.
	}

	/**
	 * actually
	 * 
	 * @param screen
	 */
	private void displayRenderedOffscreen(Canvas screen)
	{
		screen.drawBitmap(getBitmapToDisplayOnScreen(), 0, 0, null);
	}

	/**
	 * @param ressource
	 * @return
	 */
	private String computeImagePath(String ressource)
	{
		return myImageBasePath + ressource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#copyPicture(com.sporniket.libre.game.papi.PictureHandler,
	 * int, int, int, int, int, int, com.sporniket.libre.game.api.types.CopyMode)
	 */
	public void copyPicture(PictureHandler source, int x, int y, int width, int height, int dx, int dy, CopyMode mode)
	{
		// OffScreen _source = myImages.get(source.getValue());
		PictureHandler _currentScreen = getPictureHandlerOfCurrentScreen();
		// OffScreen _target = getCurrentScreen();
		// doCopyPicture(_source, x, y, width, height, _target, dx, dy, mode);
		copyPicture(source, x, y, width, height, _currentScreen, dx, dy, mode);
	}

	public void copyPicture(PictureHandler source, int x, int y, int width, int height, PictureHandler destination, int dx, int dy,
			CopyMode mode)
	{
		OffScreen _source = myImages.get(source.getValue());
		OffScreen _target = myImages.get(destination.getValue());
		doCopyPicture(_source, x, y, width, height, _target, dx, dy, mode);
	}

	public PictureHandler createOffscreenPicture()
	{
		return createOffscreenPicture(getScreenFeatures().getWidth(), getScreenFeatures().getHeight());
	}

	public PictureHandler createOffscreenPicture(int width, int height)
	{
		Bitmap _newBitmap = createBitmap(width, height);
		return registerPicture(_newBitmap);
	}

	/**
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap createBitmap(int width, int height)
	{
		int _width = width * getScreenFeatures().getXfactor();
		int _height = height * getScreenFeatures().getYfactor();
		Bitmap _newBitmap = Bitmap.createBitmap(_width, _height, Config.ARGB_8888);
		return _newBitmap;
	}

	public void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture)
	{
		PictureHandler _currentScreen = getPictureHandlerOfCurrentScreen();
		displayActorBank(actorBank, spriteBank, picture, _currentScreen);
	}

	/**
	 * @return
	 */
	public PictureHandler getPictureHandlerOfCurrentScreen()
	{
		return myOffscreensPictureHandlers[getCurrentOffscreen()];
	}

	public void displayActorBank(ActorBank actorBank, SpriteBank spriteBank, PictureHandler picture, PictureHandler destination)
	{
		OffScreen _picture = getImages().get(picture.getValue());
		OffScreen _destination = getImages().get(destination.getValue());
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
			int _dx = _dpos.x - _sprite.getHotSpotX();
			int _dy = _dpos.y - _sprite.getHotSpotY();
			CopyMode _mode = CopyMode.NORMAL;
			BlocDefinition _bloc = _sprite.getBloc();
			switch (_mode)
			{
				case EVEN_LINES:
					doCopyBitmapToOffscreen__evenLines(_picture, _bloc.getLeft(), _bloc.getTop(), _bloc.getWidth(),
							_bloc.getHeight(), _destination, _dx, _dy);
					break;
				case ODD_LINES:
					doCopyBitmapToOffscreen__oddLines(_picture, _bloc.getLeft(), _bloc.getTop(), _bloc.getWidth(),
							_bloc.getHeight(), _destination, _dx, _dy);
					break;
				default:
					doCopyBitmapToOffscreen(_picture, _bloc.getLeft(), _bloc.getTop(), _bloc.getWidth(), _bloc.getHeight(),
							_destination, _dx, _dy);
			}
		}

	}

	private boolean isLog__doCopyBitmapToOffscreen = true;

	/**
	 * Base copy of a bitmap.
	 * 
	 * Position and dimensions are expressed in device dependant pixels.
	 * 
	 * @param source
	 *            the source
	 * @param x
	 *            left
	 * @param y
	 *            top
	 * @param width
	 * @param height
	 * @param target
	 * @param dx
	 * @param dy
	 */
	private void doCopyBitmapToOffscreen(OffScreen source, int x, int y, int width, int height, OffScreen target, int dx, int dy)
	{
		if (!target.isDrawable())
		{
			Log.w(TAG, "Cannot copy bitmap to an immutable bitmap...");
		}
		else
		{
			int _factorX = getScreenFeatures().getXfactor();
			int _factorY = getScreenFeatures().getYfactor();
			doCopyBitmapToOffscreen__scaled(source, x * _factorX, y * _factorY, width * _factorX, height * _factorY, target, dx
					* _factorX, dy * _factorY);
		}
	}

	/**
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param target
	 * @param dx
	 * @param dy
	 */
	public void doCopyBitmapToOffscreen__scaled(OffScreen source, int x, int y, int width, int height, OffScreen target, int dx,
			int dy)
	{
		int _width = width /*- 1*/;
		int _height = height /*- 1*/;
		Rect src = new Rect(x, y, x + _width, y + _height);
		Rect dst = new Rect(dx, dy, dx + _width, dy + _height);
		target.getCanvas().drawBitmap(source.getData(), src, dst, null);
	}

	private void doCopyBitmapToOffscreen__evenLines(OffScreen source, int x, int y, int width, int height, OffScreen target,
			int dx, int dy)
	{
		// scale coordinates
		int _factorX = getScreenFeatures().getXfactor();
		int _factorY = getScreenFeatures().getYfactor();

		int _x = x * _factorX;
		int _y = y * _factorY;
		int _width = width * _factorX;
		int _height = height * _factorY;
		int _dx = dx * _factorX;
		int _dy = dy * _factorY;

		// delta
		int _delta = _dy & 1; // 0 for even y, 1 for odd y.

		doCopyBitmapToOffscreen__oneLineOfTwo__scaled(source, _x, _y, _width, _height, target, _dx, _dy, _delta);
	}

	private void doCopyBitmapToOffscreen__oddLines(OffScreen source, int x, int y, int width, int height, OffScreen target, int dx,
			int dy)
	{
		// scale coordinates
		int _factorX = getScreenFeatures().getXfactor();
		int _factorY = getScreenFeatures().getYfactor();

		int _x = x * _factorX;
		int _y = y * _factorY;
		int _width = width * _factorX;
		int _height = height * _factorY;
		int _dx = dx * _factorX;
		int _dy = dy * _factorY;

		int _delta = 1 - (_dy & 1); // 1 for even y, 0 for odd y.
		doCopyBitmapToOffscreen__oneLineOfTwo__scaled(source, _x, _y, _width, _height, target, _dx, _dy, _delta);
	}

	/**
	 * Copy a bitmap by skipping one line of two.
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param target
	 * @param dx
	 * @param dy
	 * @param delta
	 *            start the copy from y+delta, then skip one line of two.
	 */
	private void doCopyBitmapToOffscreen__oneLineOfTwo__scaled(OffScreen source, int x, int y, int width, int height,
			OffScreen target, int dx, int dy, int delta)
	{
		int _dy = dy + delta; // sync target line
		int _startY = y + delta; // min y (included)
		int _endy = y + height; // max y (exluding)
		for (int _y = _startY; _y < _endy;)
		{
			doCopyBitmapToOffscreen__scaled(source, x, _y, width, 1, target, dx, _dy);
			_dy += 2;
			_y += 2;
		}
	}

	/**
	 * @param source
	 * @param x
	 * @param width
	 * @param y
	 * @param height
	 * @param target
	 * @param dx
	 * @param dy
	 * @param mode
	 */
	private void doCopyPicture(OffScreen source, int x, int y, int width, int height, OffScreen target, int dx, int dy,
			CopyMode mode)
	{
		switch (mode)
		{
			case EVEN_LINES:
				doCopyBitmapToOffscreen__evenLines(source, x, y, width, height, target, dx, dy);
				break;
			case ODD_LINES:
				doCopyBitmapToOffscreen__oddLines(source, x, y, width, height, target, dx, dy);
				break;
			default:
				doCopyBitmapToOffscreen(source, x, y, width, height, target, dx, dy);
		}
	}

	/**
	 * @return the androidContext
	 */
	public Context getAndroidContext()
	{
		return myAndroidContext;
	}

	/**
	 * @return the currentOffscreen
	 */
	public int getCurrentOffscreen()
	{
		return myCurrentOffscreen;
	}

	/**
	 * @return
	 */
	private OffScreen getCurrentScreen()
	{
		return myOffscreens[myCurrentOffscreen];
	}

	/**
	 * The screen surface must display this bitmap.
	 * 
	 * @return
	 */
	private Bitmap getBitmapToDisplayOnScreen()
	{
		return myOffscreens[myRenderedOffscreen].getData();
	}

	/**
	 * @return the images
	 */
	public List<OffScreen> getImages()
	{
		return myImages;
	}

	@Override
	protected ResourceDefinitionLocator getResourceDefinitionLocator()
	{
		return myResourceLocator;
	}

	public ScreenFeatureSet getScreenFeatures()
	{
		return myScreenFeatures;
	}

	/**
	 * @param screenFeatures
	 *            the screenFeatures to set
	 */
	private void setScreenFeatures(ScreenFeatureSet screenFeatures)
	{
		if (null == myScreenFeatures)
		{
			updateScreenFeatureSet(screenFeatures);
		}
		else
		{
			Log.w(TAG, "Screen features has already been set.");
		}
	}

	public PictureHandler loadPicture(String ressource, boolean useBlackAsTransparentColor) throws IOException
	{
		Bitmap _bitmap = loadBitmap(ressource);
		PictureHandler _result = registerPicture(_bitmap, ressource);

		Log.i(TAG, "registered image : " + ressource + " as picture " + _result.getValue());

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
		// change the bitmap data
		OffScreen _internal = getImages().get(picture.getValue());
		Bitmap _oldBitmap = _internal.getData();
		_oldBitmap.recycle();

		Bitmap _newBitmap = loadBitmap(ressource);
		_internal.setData(_newBitmap);

		// update features
		picture.updateFeatures(_newBitmap.getWidth(), _newBitmap.getHeight());
		return picture;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#captureScreen()
	 */
	@Override
	public File captureScreen() throws IOException
	{
		// retrieve the current bitmap
		Bitmap _toSave = getBitmapToDisplayOnScreen();
		File _result = new File(getAndroidContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "capture.png");
		final FileOutputStream _fileOutputStream = new FileOutputStream(_result);
		_toSave.compress(CompressFormat.PNG, 85, _fileOutputStream);
		return _result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.MessageSender#shareMessage(java.lang.String, java.lang.String, java.io.File)
	 */
	@Override
	public void shareMessage(String subject, String message, File attachement) throws MessageSenderException
	{
		final String _title = subject;
		final String _message = message;
		final String _type;
		String _fileName = attachement.getName();
		String _extension = _fileName.substring(_fileName.lastIndexOf(".") + 1).trim().toLowerCase();
		switch (_extension)
		{
			case "png":
				_type = "image/png";
				break;
			case "jpg":
			case "jpeg":
				_type = "image/jpeg";
				break;
			default:
				_type = "*/*";
		}

		MediaScannerConnection.scanFile(getAndroidContext(), new String[]
		{
			attachement.toString()
		}, null, new MediaScannerConnection.OnScanCompletedListener()
		{
			public void onScanCompleted(String path, Uri uri)
			{
				// create the intent and send it
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, _title);
				shareIntent.putExtra(Intent.EXTRA_TITLE, _title);
				shareIntent.putExtra(Intent.EXTRA_TEXT, _message);
				shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
				shareIntent.setType(_type);
				getActivity().startActivity(Intent.createChooser(shareIntent, _title));
			}
		});

	}

	private Bitmap loadBitmap(String ressource) throws IOException
	{
		InputStream bitmapStream = null;
		Bitmap _bitmap = null;
		try
		{
			String _imagePath = computeImagePath(ressource);
			Log.i(TAG, "loading image : " + _imagePath);
			bitmapStream = getAndroidContext().getAssets().open(_imagePath);

			// API LEVEL 10 !! decoded bitmap are not modifiable.
			_bitmap = BitmapFactory.decodeStream(bitmapStream);

		}
		catch (IOException e1)
		{
			throw e1;
		}
		finally
		{
			bitmapStream.close();
		}

		return _bitmap;
	}

	/**
	 * Store the given bitmap in the image pool.
	 * 
	 * @param bitmap
	 * @return a PictureHandler to identify this image.
	 */
	private PictureHandlerAndroid registerPicture(Bitmap bitmap)
	{
		return registerPicture(bitmap, null);
	}

	/**
	 * Store the given bitmap in the image pool.
	 * 
	 * @param bitmap
	 * @param source
	 *            the path to the original file, for Offscreen regeneration.
	 * @return a PictureHandler to identify this image.
	 */
	private PictureHandlerAndroid registerPicture(Bitmap bitmap, String source)
	{
		PictureHandlerAndroid _result;
		OffScreen _newOffscreen = new OffScreen(bitmap);
		int _nextImage = getImages().size();
		getImages().add(_newOffscreen);
		int _width = bitmap.getWidth() / getScreenFeatures().getXfactor();
		int _height = bitmap.getHeight() / getScreenFeatures().getYfactor();
		if (null == source)
		{
			_result = new PictureHandlerAndroid(_nextImage, _width, _height, this);
		}
		else
		{
			_result = new PictureHandlerAndroid(_nextImage, _width, _height, source, this);
		}
		getPictureHandlers().add(_result);
		return _result;
	}

	/**
	 * @param androidContext
	 *            the androidContext to set
	 */
	private void setAndroidContext(Context androidContext)
	{
		myAndroidContext = androidContext;
		myResourceLocator = new AndroidAssetResourceDefinitionLocator(getAndroidContext());
	}

	/**
	 * Setup the view ScreenFeatureSet.
	 * 
	 * @param featureSet
	 */
	private void updateScreenFeatureSet(ScreenFeatureSet featureSet)
	{
		myScreenFeatures = featureSet;

		// init image path computing
		myImageBasePath = getScreenFeatures().getGraphicsDefinition().name() + File.separator;

		// create Bitmap and Canvas for offscreen drawing
		for (int _i = 0; _i < myOffscreens.length; _i++)
		{
			if (null == myOffscreensPictureHandlers[_i])
			{
				PictureHandlerAndroid _handler = (PictureHandlerAndroid) createOffscreenPicture();
				myOffscreensPictureHandlers[_i] = _handler;
				myOffscreens[_i] = myImages.get(_handler.getValue());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#isGameControllerAware()
	 */
	public boolean isGameControllerAware()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#isKeyboardAware()
	 */
	public boolean isKeyboardAware()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#isPointerAware()
	 */
	public boolean isPointerAware()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.PlatformBase#doUpdate()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void doUpdate()
	{
		// nothing to do
	}

	public void doExit()
	{
		// free the bitmap from memory
		for (OffScreen _offscreen : getImages())
		{
			if (null != _offscreen.getData())
			{
				_offscreen.getData().recycle();
			}
		}

		// free the soundpool
		if (null != mySoundPool)
		{
			mySoundPool.release();
			mySoundPool = null;
		}
	}

	/**
	 * Flag to freeze the run (another app, or kill).
	 */
	private boolean myFreezed = false;

	/**
	 * @return the freezed
	 */
	public boolean isFreezed()
	{
		return myFreezed;
	}

	/**
	 * @param freezed
	 *            the freezed to set
	 */
	public void setFreezed(boolean freezed)
	{
		myFreezed = freezed;
	}

	public void freeze(Game game)
	{
		if (!isFreezed())
		{
			setFreezed(true);
			game.freeze();
		}
	}

	public void unfreeze(Game game)
	{
		if (isFreezed())
		{
			setFreezed(false);
			game.unfreeze();
		}
	}

	// HERE
	protected void doRun()
	{
		Log.i(TAG, "====> [doRun()] ");
		Game game_ = getGame();
		long lastTime = getTime().getClock();
		long now, elapsed;
		while (!isFreezed() && !game_.isFinished())
		{
			update();
			now = getTime().getClock();
			elapsed = now - lastTime;
			if (elapsed < 0)
			{
				elapsed = 0;
			}
			game_.update(elapsed);
			lastTime = now;
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException _exception)
			{
				_exception.printStackTrace();
			}
			if (!game_.isFinished() && game_.isRedrawNeeded() && isRedrawPossible())
			{
				try
				{
					game_.redraw(this);
					commitDisplay();
					// actually do the display in the surface view through the surface holder
					Canvas _screen = null;
					try
					{
						_screen = getSurfaceHolder().lockCanvas();
						synchronized (getSurfaceHolder())
						{
							displayRenderedOffscreen(_screen);
						}
					}
					finally
					{
						if (null != _screen)
						{
							getSurfaceHolder().unlockCanvasAndPost(_screen);
						}
					}
				}
				catch (RuntimeException _exception)
				{
					// TODO Auto-generated catch block
					_exception.printStackTrace();
					Log.i(TAG, "oops, probably a surface destroyed...", _exception);
				}
			}
		}
		Log.i(TAG, "<---- [doRun()]");
	}

	private SurfaceHolder mySurfaceHolder;

	/**
	 * @return the surfaceHolder
	 */
	private SurfaceHolder getSurfaceHolder()
	{
		return mySurfaceHolder;
	}

	/**
	 * @param surfaceHolder
	 *            the surfaceHolder to set
	 */
	public void setSurfaceHolder(SurfaceHolder surfaceHolder)
	{
		mySurfaceHolder = surfaceHolder;
		setRedrawPossible(null != mySurfaceHolder);
		if (isRedrawPossible())
		{
			updateScreenFeatureSet(getScreenFeatures());
		}
	}

	/**
	 * Setup the game environment.
	 * 
	 * @param context
	 * @param screenFeatureSet
	 */
	public void setupEnvironment(Context context, ScreenFeatureSet screenFeatureSet)
	{
		setAndroidContext(context);
		setScreenFeatures(screenFeatureSet);
	}

	/**
	 * @return the redrawPossible
	 */
	public boolean isRedrawPossible()
	{
		return myRedrawPossible;
	}

	/**
	 * @param redrawPossible
	 *            the redrawPossible to set
	 */
	public void setRedrawPossible(boolean redrawPossible)
	{
		myRedrawPossible = redrawPossible;
	}

	public void debugPictures()
	{
		Log.i(TAG, "====> [GamePlatform.debugPictures()] ");
		Log.i(TAG, "images... ");
		int index = 0;
		for (OffScreen _offscreen : myImages)
		{
			if (null == _offscreen.getData())
			{
				Log.i(TAG, "" + index + " is null.");
			}
			else
			{
				Log.i(TAG, "" + index + " " + _offscreen.myData + " is recycled ? : " + _offscreen.myData.isRecycled());
			}
			index++;
		}
		Log.i(TAG, "<---- [GamePlatform.debugPictures()]");
	}

	/**
	 * Store created picture Handlers for recycle/regeneration steps (onStop/onStart).
	 */
	private List<PictureHandlerAndroid> myPictureHandlers = new LinkedList<GamePlatform.PictureHandlerAndroid>();

	/**
	 * dereference the recycled bitmaps
	 */
	public void disposeRecycledBitmaps()
	{
		Log.i(TAG, "====> [disposeRecycledBitmaps()] ");
		for (PictureHandlerAndroid _picture : getPictureHandlers())
		{
			Log.i(TAG, "processing picture " + _picture.getValue() + "...");
			OffScreen _offscreen = getImages().get(_picture.getValue());
			if (_offscreen.getData().isRecycled())
			{
				Bitmap _data = _offscreen.getData();
				_offscreen.setData(null);
				Log.i(TAG, "  Disposed bitmap " + _data);
			}
		}
		Log.i(TAG, "<---- [disposeRecycledBitmaps()]");
	}

	/**
	 * Reload bitmap files, recreate offscreens.
	 * 
	 * @throws IOException
	 */
	public void regenerateBitmaps() throws IOException
	{
		Log.i(TAG, "====> [regenerateBitmaps()] ");
		for (PictureHandlerAndroid _picture : getPictureHandlers())
		{
			OffScreen _offscreen = getImages().get(_picture.getValue());
			// skip valid bitmap
			if (null != _offscreen.getData() && !_offscreen.getData().isRecycled())
			{
				continue;
			}

			// ok, regenerate the missing bitmap
			Bitmap _data;
			if (null != _picture.getSource())
			{
				_data = loadBitmap(_picture.getSource());
			}
			else
			{
				final Features _features = _picture.getFeatures();
				Log.i(TAG, "Recreate bitmap " + _features.getWidth() + "x" + _features.getHeight() + "px");
				_data = createBitmap(_features.getWidth(), _features.getHeight());
			}
			_offscreen.setData(_data);
		}
		Log.i(TAG, "<---- [regenerateBitmaps()]");
	}

	/**
	 * Recreate the soundpool and reload any existing soundHandler.
	 * 
	 * @throws IOException
	 */
	public void regenerateSounds() throws IOException
	{
		if (null != mySoundPool)
		{
			mySoundPool.release();
		}
		mySoundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		for (SoundHandlerAndroid _handler : getSoundHandlers())
		{
			int _id = mySoundPool.load(getAssetFileDescriptor(_handler.getSource()), 1);
			_handler.resetValue(_id);
		}
	}

	/**
	 * @return the pictureHandlers
	 */
	private List<PictureHandlerAndroid> getPictureHandlers()
	{
		return myPictureHandlers;
	}

	/**
	 * private storage to all the sound handler to regenerate them.
	 */
	private List<SoundHandlerAndroid> mySoundHandlers = new ArrayList<SoundHandlerAndroid>(50);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.SoundAbstractionLayerInterface#loadSound(java.lang.String)
	 */
	public SoundHandler loadSound(String ressource) throws IOException
	{
		int _id = mySoundPool.load(getAssetFileDescriptor(ressource), 1);
		final SoundHandlerAndroid _soundHandler = new SoundHandlerAndroid(_id, ressource);
		mySoundHandlers.add(_soundHandler);
		return _soundHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.SoundAbstractionLayerInterface#playSound(com.sporniket.libre.game.papi.SoundHandler)
	 */
	public void playSound(SoundHandler sound)
	{
		// play the sound in the center, no frequency change, low priority
		mySoundPool.play(sound.getValue(), 1.0f, 1.0f, 0, 0, 1.0f);
	}

	private AssetFileDescriptor getAssetFileDescriptor(String pathToFile) throws IOException
	{
		return myAndroidContext.getAssets().openFd(pathToFile);
	}

	/**
	 * @return the soundHandlers
	 */
	private List<SoundHandlerAndroid> getSoundHandlers()
	{
		return mySoundHandlers;
	}

	public void connectInput(InputAbstractionLayerInterface input)
	{
		setInput(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.PlatformBase#doCheckOut()
	 */
	@Override
	public void doCheckOut()
	{
		// FIXME Auto-generated method stub

	}

}
