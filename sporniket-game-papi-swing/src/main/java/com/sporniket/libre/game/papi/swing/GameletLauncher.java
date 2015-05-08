/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import com.sporniket.libre.game.papi.Game;
import com.sporniket.libre.game.papi.Gamelet;
import com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface;
import com.sporniket.libre.game.papi.InputAbstractionLayerInterface;
import com.sporniket.libre.game.papi.SoundAbstractionLayerInterface;
import com.sporniket.libre.game.papi.TimeAbstractionLayerInterface;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Adapter to quickly test a gamelet, only the bare minimum is implemented.
 * 
 * <p>
 * Just write a gamelet and a main function that use one of this launcher subclass (one specific {@link ScreenFeatureSet}). If your
 * specific {@link ScreenFeatureSet} is not implemented, just write a subclass.
 * 
 * @author DSPORN
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public abstract class GameletLauncher extends Game
{
	public static class LandscapeQhd extends GameletLauncher
	{

		public LandscapeQhd(Gamelet hostedGamelet)
		{
			super(hostedGamelet);
		}

		private static final ScreenFeatureSet[] ACCEPTED_SCREEN_FEATURE_SETS =
		{
			ScreenFeatureSet.Native.Landscape.QHD
		};

		@Override
		public ScreenFeatureSet[] getAcceptedScreenFeatureSets()
		{
			return ACCEPTED_SCREEN_FEATURE_SETS;
		}
	}

	/**
	 * @param hostedGamelet
	 * @since 0-SNAPSHOT
	 */
	public GameletLauncher(Gamelet hostedGamelet)
	{
		getGamelets().add(hostedGamelet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#doExit()
	 */
	@Override
	protected void doExit()
	{
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#updatePreferences()
	 */
	@Override
	public void updatePreferences()
	{
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#freeze()
	 */
	@Override
	public void freeze()
	{
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#init(com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface,
	 * com.sporniket.libre.game.papi.SoundAbstractionLayerInterface, com.sporniket.libre.game.papi.InputAbstractionLayerInterface,
	 * com.sporniket.libre.game.papi.TimeAbstractionLayerInterface)
	 */
	@Override
	public void init()
	{
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#isValidPlatform(com.sporniket.libre.game.papi.GraphicAbstractionLayerInterface,
	 * com.sporniket.libre.game.papi.InputAbstractionLayerInterface, com.sporniket.libre.game.papi.TimeAbstractionLayerInterface)
	 */
	@Override
	public boolean isValidPlatform()
	{
		// always true
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#unfreeze()
	 */
	@Override
	public void unfreeze()
	{
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Game#putPreferenceDataIntoSession()
	 */
	@Override
	public void putPreferenceDataIntoSession()
	{
		// do nothing
	}

}
