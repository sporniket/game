/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.gamelet.input.KeyboardStateProvider;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;
import com.sporniket.libre.game.gamelet.input.PointerStateProviderSupport;
import com.sporniket.libre.game.papi.Game;
import com.sporniket.libre.game.papi.InputAbstractionLayerInterface;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * View to instanciate to display the game.
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
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Android</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * @see http://developer.android.com/guide/topics/graphics/2d-graphics.html
 */
public class GameView extends SurfaceView implements InputAbstractionLayerInterface
{

	/**
	 * Tag for android logging.
	 */
	private static final String TAG = GameView.class.getSimpleName();

	private GameActivity myGameActivity ;
	
	private ScreenFeatureSet myScreenFeatureSet ;

	/**
	 * @param context
	 */
	public GameView(Context context, ScreenFeatureSet screenFeatureSet)
	{
		super(context);
		setScreenFeatureSet(screenFeatureSet);
	}

	/**
	 * @param measureSpec
	 */
	private void dumpMeasureSpec(String message, int measureSpec)
	{
		AndroidUtils.dumpMeasureSpec(TAG, message, measureSpec);
	}

	/**
	 * @return the gameActivity
	 */
	public GameActivity getGameActivity()
	{
		return myGameActivity;
	}

	/**
	 * @return
	 */
	private GamePlatform getPlatform()
	{
		return getGameActivity().getPlatform();
	}

	public boolean isAttachedToGameActivity()
	{
		return null != getGameActivity() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		Log.i(TAG, "====> [onMeasure(widthMeasureSpec, heightMeasureSpec)] ");

		// log requested measure, for info
		dumpMeasureSpec("widthSpec", widthMeasureSpec);
		dumpMeasureSpec("heightSpec", heightMeasureSpec);

		// Don't care about those dimensions, use the Platform screen feature instead.
		final ScreenFeatureSet _screenFeatures = getPlatform().getScreenFeatures();
		setMeasuredDimension(_screenFeatures.getWidth() * _screenFeatures.getXfactor(), _screenFeatures.getHeight()
				* _screenFeatures.getYfactor());
		
		Log.i(TAG, "<---- [onMeasure(widthMeasureSpec, heightMeasureSpec)]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		processTouchEvent(event);
		return true ;
	}
	
	/**
	 * @param gameActivity the gameActivity to set
	 */
	public void setGameActivity(GameActivity gameActivity)
	{
		myGameActivity = gameActivity;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#isGameControllerAware()
	 */
	@Override
	public boolean isGameControllerAware()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#isKeyboardAware()
	 */
	@Override
	public boolean isKeyboardAware()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#isPointerAware()
	 */
	@Override
	public boolean isPointerAware()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#update()
	 */
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#getGameControllerStateProvider()
	 */
	@Override
	public GameControllerStateProvider getGameControllerStateProvider() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Input abstraction layer does not support game controllers.");
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#getPointerStateProvider()
	 */
	@Override
	public PointerStateProvider getPointerStateProvider() throws UnsupportedOperationException
	{
		return myPointerStateProvider;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#getKeyboardStateProvider()
	 */
	@Override
	public KeyboardStateProvider getKeyboardStateProvider() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Input abstraction layer does not support keyboards.");
	}
	
	/**
	 * Pointer state provider, support multitouch for up to 12 fingers.
	 */
	private PointerStateProviderSupport myPointerStateProvider = new PointerStateProviderSupport(12);


	private void processTouchEvent(MotionEvent event)
	{
		int _actionCode = event.getActionMasked();

		// find notified pointers

		// ok, now we are ready to process...
		int _factorX = getScreenFeatureSet().getXfactor();
		int _factorY = getScreenFeatureSet().getYfactor();
		for (int _pointerId = 0; _pointerId < event.getPointerCount(); _pointerId++)
		{
			int _pointerIndex = event.findPointerIndex(_pointerId);
			if (-1 == _pointerIndex)
			{
				//current pointer is not concerned by the event
				continue;
			}
			int _x = (int) event.getX(_pointerIndex);
			int _y = (int) event.getY(_pointerIndex);
			switch (_actionCode)
			{
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
				case MotionEvent.ACTION_MOVE:
					myPointerStateProvider.recordPressedPointer(_pointerId, _x / _factorX, _y / _factorY);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					myPointerStateProvider.recordReleasedPointer(_pointerId, _x / _factorX, _y / _factorY);
					break;
				case MotionEvent.ACTION_CANCEL:
					myPointerStateProvider.recordUndefinedPointer(_pointerId, _x / _factorX, _y / _factorY);
					break;
				default:
					// do nothing...
			}
		}

	}

	/**
	 * @param _actionCode
	 * @return
	 */
	private boolean processTouchEvent__isEventInteresting(int _actionCode)
	{
		return MotionEvent.ACTION_POINTER_UP == _actionCode || MotionEvent.ACTION_POINTER_DOWN == _actionCode
				|| MotionEvent.ACTION_UP == _actionCode || MotionEvent.ACTION_DOWN == _actionCode;
	}

	/**
	 * @return the screenFeatureSet
	 */
	private ScreenFeatureSet getScreenFeatureSet()
	{
		return myScreenFeatureSet;
	}

	/**
	 * @param screenFeatureSet the screenFeatureSet to set
	 */
	private void setScreenFeatureSet(ScreenFeatureSet screenFeatureSet)
	{
		myScreenFeatureSet = screenFeatureSet;
	}

}
