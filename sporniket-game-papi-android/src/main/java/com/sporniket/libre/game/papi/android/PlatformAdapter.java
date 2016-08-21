package com.sporniket.libre.game.papi.android;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.ClipboardManager;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;
import com.sporniket.libre.game.api.ResourceDefinitionLocator;
import com.sporniket.libre.game.api.SimpleTextFileResourceDefinitionLoaderV00;
import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.SequenceBank;
import com.sporniket.libre.game.api.sprite.SequenceInstanceBank;
import com.sporniket.libre.game.api.sprite.SpriteBank;
import com.sporniket.libre.game.api.types.CopyMode;
import com.sporniket.libre.game.gamelet.input.GameControllerStateProvider;
import com.sporniket.libre.game.gamelet.input.KeyboardStateProvider;
import com.sporniket.libre.game.gamelet.input.PointerStateProvider;
import com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface;
import com.sporniket.libre.game.papi.InputAbstractionLayerInterface;
import com.sporniket.libre.game.papi.MessageSender;
import com.sporniket.libre.game.papi.MessageSenderException;
import com.sporniket.libre.game.papi.PictureHandler;
import com.sporniket.libre.game.papi.Platform;
import com.sporniket.libre.game.papi.PlatformBase;
import com.sporniket.libre.game.papi.SoundAbstractionLayerInterface;

/**
 * Default Implementation of some methods.
 * 
 * This base class allow to implements some function depending from others.
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
//FIXME DO NOT IMPLEMENT INPUT ABSTRACTION LAYER (INFINITE LOOP OF DOOM), IT MUST BE GAMEACTIVITY OR SOMETHING ELSE
public abstract class PlatformAdapter extends PlatformBase implements GraphicAbstractionLayerInterface, SoundAbstractionLayerInterface,
		ResourceDefinitionLoader, InputAbstractionLayerInterface, MessageSender
{

	private SimpleTextFileResourceDefinitionLoaderV00 myResourceDefinitionLoader = new SimpleTextFileResourceDefinitionLoaderV00();

	public PlatformAdapter()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#copyPicture(com.sporniket.libre.game.papi.PictureHandler,
	 * int, int, int, int, int, int)
	 */
	public void copyPicture(PictureHandler source, int x, int y, int width, int height, int dx, int dy)
	{
		copyPicture(source, x, y, width, height, dx, dy, CopyMode.NORMAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#copyPicture(com.sporniket.libre.game.papi.PictureHandler,
	 * int, int, int, int, com.sporniket.libre.game.papi.PictureHandler, int, int)
	 */
	public final void copyPicture(PictureHandler source, int x, int y, int width, int height, PictureHandler destination, int dx,
			int dy)
	{
		copyPicture(source, x, y, width, height, destination, dx, dy, CopyMode.NORMAL);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#displayPicture(com.sporniket.libre.game.papi.PictureHandler,
	 * int, int)
	 */
	public final void displayPicture(PictureHandler picture, int x, int y)
	{
		displayPicture(picture, x, y, CopyMode.NORMAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface#displayPicture(com.sporniket.libre.game.papi.PictureHandler,
	 * int, int, com.sporniket.libre.game.api.types.CopyMode)
	 */
	public final void displayPicture(PictureHandler picture, int x, int y, CopyMode mode)
	{
		copyPicture(picture, 0, 0, picture.getFeatures().getWidth(), picture.getFeatures().getHeight(), x, y, mode);
	}

	/**
	 * Override this if the implementation support game controller.
	 * 
	 * @return
	 */
	protected GameControllerStateProvider doGetGameControllerStateProvider()
	{
		return null;
	}

	/**
	 * Override this if the implementation support game controller.
	 * 
	 * @return
	 */
	protected KeyboardStateProvider doGetKeyboardStateProvider()
	{
		return null;
	}

	/**
	 * Override this if the implementation support game controller.
	 * 
	 * @return
	 */
	protected PointerStateProvider doGetPointerStateProvider()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#getGameControllerStateProvider()
	 */
	public final GameControllerStateProvider getGameControllerStateProvider() throws UnsupportedOperationException
	{
		if (!isGameControllerAware())
		{
			throw new UnsupportedOperationException("Input abstraction layer does not support game controllers.");
		}
		return doGetGameControllerStateProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#getKeyboardStateProvider()
	 */
	public final KeyboardStateProvider getKeyboardStateProvider() throws UnsupportedOperationException
	{
		if (!isKeyboardAware())
		{
			throw new UnsupportedOperationException("Input abstraction layer does not support keyboards.");
		}
		return doGetKeyboardStateProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.InputAbstractionLayerInterface#getPointerStateProvider()
	 */
	public final PointerStateProvider getPointerStateProvider() throws UnsupportedOperationException
	{
		if (!isPointerAware())
		{
			throw new UnsupportedOperationException("Input abstraction layer does not support pointers.");
		}
		return doGetPointerStateProvider();
	}

	/**
	 * @return the resourceDefinitionLoader
	 */
	private SimpleTextFileResourceDefinitionLoaderV00 getResourceDefinitionLoader__private()
	{
		return myResourceDefinitionLoader;
	}

	/**
	 * @return the resourceDefinitionLoader
	 */
	protected ResourceDefinitionLoader getResourceDefinitionLoader()
	{
		return getResourceDefinitionLoader__private();
	}
	
	public ResourceDefinitionLoader getLoader()
	{
		return getResourceDefinitionLoader() ;
	}

	protected abstract ResourceDefinitionLocator getResourceDefinitionLocator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Platform#init()
	 */
	public void doInit()
	{
		setGraal(this);
		//FIXME MOVE SETINPUT IN GAME PLATFORM
		//setInput(this);
		setSound(this);
		setSender(this);
		setTime(new TimeAbstractionLayer());
		initResourceLoader();
	}

	private void initResourceLoader()
	{
		getResourceDefinitionLoader__private().setResourceDefinitionLocator(getResourceDefinitionLocator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.ResourceDefinitionLoader#loadSpriteAndActorDefinitions(java.io.File,
	 * com.sporniket.libre.game.api.sprite.SpriteBank, com.sporniket.libre.game.api.sprite.SequenceBank,
	 * com.sporniket.libre.game.api.sprite.SequenceInstanceBank, com.sporniket.libre.game.api.sprite.ActorBank)
	 */
	public final void loadSpriteAndActorDefinitions(File file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		getResourceDefinitionLoader__private().loadSpriteAndActorDefinitions(file, sprites, sequences, sequenceInstances, actors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.ResourceDefinitionLoader#loadSpriteAndActorDefinitions(java.io.InputStream,
	 * com.sporniket.libre.game.api.sprite.SpriteBank, com.sporniket.libre.game.api.sprite.SequenceBank,
	 * com.sporniket.libre.game.api.sprite.SequenceInstanceBank, com.sporniket.libre.game.api.sprite.ActorBank)
	 */
	public final void loadSpriteAndActorDefinitions(InputStream inputStream, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		getResourceDefinitionLoader__private().loadSpriteAndActorDefinitions(inputStream, sprites, sequences, sequenceInstances, actors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.api.ResourceDefinitionLoader#loadSpriteAndActorDefinitions(java.lang.String,
	 * com.sporniket.libre.game.api.sprite.SpriteBank, com.sporniket.libre.game.api.sprite.SequenceBank,
	 * com.sporniket.libre.game.api.sprite.SequenceInstanceBank, com.sporniket.libre.game.api.sprite.ActorBank)
	 */
	public final void loadSpriteAndActorDefinitions(String file, SpriteBank sprites, SequenceBank sequences,
			SequenceInstanceBank sequenceInstances, ActorBank actors) throws IOException
	{
		getResourceDefinitionLoader__private().loadSpriteAndActorDefinitions(file, sprites, sequences, sequenceInstances, actors);
	}

	/**
	 * Link to the activity to start external activities.
	 */
	private Activity myActivity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.MessageSender#openWebPage(java.lang.String)
	 */
	public void openWebPage(String url) throws MessageSenderException
	{
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		if (null != getActivity())
		{
			getActivity().startActivity(Intent.createChooser(i, "Open"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.MessageSender#shareMessage(java.lang.String)
	 */
	public void shareMessage(String subject, String message) throws MessageSenderException
	{
		// Facebook workaround without specific API : copy the message into the Clipboard
		// ClipboardManager _clipBoard = (ClipboardManager) getActivity().getApplicationContext().getSystemService(
		// Context.CLIPBOARD_SERVICE);
		// try
		// {
		// //use reflection to use the new ClipBoard Framework.
		// @SuppressWarnings("rawtypes")
		// Class _clipDataClass = Class.forName("android.content.ClipData");
		// Method _newPlainText = _clipDataClass.getMethod("newPlainText", CharSequence.class, CharSequence.class);
		// Object ClipData = _newPlainText.invoke(null, subject, message);
		// Method _setPrimaryClip = ClipboardManager.class.getMethod("setPrimaryClip", _clipDataClass);
		// _setPrimaryClip.invoke(_clipBoard, ClipData);
		// }
		// catch (ClassNotFoundException _exception)
		// {
		// _clipBoard.setText(message);
		// }
		// catch (SecurityException _exception)
		// {
		// // TODO Auto-generated catch block
		// _exception.printStackTrace();
		// }
		// catch (NoSuchMethodException _exception)
		// {
		// // TODO Auto-generated catch block
		// _exception.printStackTrace();
		// }
		// catch (IllegalAccessException _exception)
		// {
		// // TODO Auto-generated catch block
		// _exception.printStackTrace();
		// }
		// catch (IllegalArgumentException _exception)
		// {
		// // TODO Auto-generated catch block
		// _exception.printStackTrace();
		// }
		// catch (InvocationTargetException _exception)
		// {
		// // TODO Auto-generated catch block
		// _exception.printStackTrace();
		// }

		// Launch share intent
		Intent sharingIntent = createMessageIntent(subject, message);
		if (null != getActivity())
		{
			getActivity().startActivity(Intent.createChooser(sharingIntent, subject));
		}

	}

	/**
	 * @param subject
	 * @param message
	 * @return
	 */
	protected Intent createMessageIntent(String subject, String message)
	{
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		return sharingIntent;
	}
	
	

	/**
	 * @return the activity
	 */
	public Activity getActivity()
	{
		return myActivity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(Activity activity)
	{
		myActivity = activity;
	}

}