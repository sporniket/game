/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import com.sporniket.libre.game.papi.profile.Profile;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;
import com.sporniket.libre.game.papi.profile.TargetPlatform;

/**
 * Class of profiles for android platform.
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
public class ProfileForAndroid implements Profile {
	/**
	 * Default screen feature set to use.
	 */
	private static final ScreenFeatureSet DEFAULT__SCREEN_FEATURE_SET = ScreenFeatureSet.WVGA_PORTRAIT;

	/**
	 * Create a profile using the provided {@link ScreenFeatureSet}.
	 * @param screenFeatureSet
	 * @return
	 */
	public static Profile createProfile(ScreenFeatureSet screenFeatureSet)
	{
		ProfileForAndroid _result = new ProfileForAndroid();
		_result.setScreenFeatures(screenFeatureSet);
		
		return _result;
	}
	
	private ScreenFeatureSet myScreenFeatures = DEFAULT__SCREEN_FEATURE_SET ;

	/**
	 * @return the screenFeatureSet
	 */
	public ScreenFeatureSet getScreenFeatures()
	{
		return myScreenFeatures;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.sgpapi.Profil#getTargetPlatform()
	 */
	public TargetPlatform getTargetPlatform() {
		return TargetPlatform.ANDROID;
	}

	/**
	 * @param screenFeatureSet the screenFeatureSet to set
	 */
	private void setScreenFeatures(ScreenFeatureSet screenFeatureSet)
	{
		myScreenFeatures = screenFeatureSet;
	}

}
