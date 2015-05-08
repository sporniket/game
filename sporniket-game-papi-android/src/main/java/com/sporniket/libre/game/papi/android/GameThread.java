package com.sporniket.libre.game.papi.android;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

import com.sporniket.libre.game.papi.Game;

/**
 * Game thread, for running the game and redrawing the screen out of the android UI thread.
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
 * 
 */
class GameThread extends Thread
{
	private static final String TAG = GameThread.class.getSimpleName();
	
	/**
	 * Android context, to pass to the game platform.
	 */
	private Context myAndroidContext;

	/**
	 * Instance of the game to run on the platform.
	 */
	private Game myGame;

	/**
	 * Link to the view
	 */
	private GameView myGameView;

	/**
	 * Instance of the game platform.
	 */
	private GamePlatform myPlatform;

	/**
	 * Surface holder to be able to redraw.
	 */
	private SurfaceHolder mySurfaceHolder;
	
	/**
	 * Recreate the specified thread because it cannot be started again.
	 * @param deadThread
	 * @return
	 */
	public static GameThread resurrect(GameThread deadThread)
	{
		return new GameThread(deadThread.myAndroidContext, deadThread.mySurfaceHolder, deadThread.myGameView, deadThread.myPlatform, deadThread.myGame);
	}

	/**
	 * @param androidContext
	 * @param gameView
	 * @param surfaceHolder
	 * @param platform
	 * @param game
	 */
	public GameThread(Context androidContext, SurfaceHolder surfaceHolder, GameView gameView, GamePlatform platform, Game game)
	{
		super();
		myAndroidContext = androidContext;
		myGameView = gameView;
		mySurfaceHolder = surfaceHolder;
		myPlatform = platform;
		myGame = game;
	}

	/**
	 * Pause the game and stop the thread.
	 */
	public void pause()
	{
		Log.i(TAG, "====> [pause()] ");
		myPlatform.freeze(myGame);
		Log.i(TAG, "<---- [pause()]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		Log.i(TAG, "====> [run()] ");
		myPlatform.run();
		Log.i(TAG, "<---- [run()]");
	}

	/**
	 * Resume the game running.
	 */
	public void unpause()
	{
		Log.i(TAG, "====> [unpause()] ");
		myPlatform.unfreeze(myGame);
		Log.i(TAG, "<---- [unpause()]");
	}
	
	/**
	 * @return <code>true</code> if the platform is paused.
	 */
	public boolean isPaused()
	{
		return myPlatform.isFreezed();
	}

}