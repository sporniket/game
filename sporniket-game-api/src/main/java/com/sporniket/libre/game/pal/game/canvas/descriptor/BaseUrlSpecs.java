/**
 * 
 */
package com.sporniket.libre.game.pal.game.canvas.descriptor;

import com.sporniket.libre.lang.string.StringTools;

/**
 * Base url that will be used to load various kind of ressources.
 * 
 * @author dsporn
 *
 */
public class BaseUrlSpecs
{
	private final String myBaseUrlForData;

	private final String myBaseUrlForJukebox;

	private final String myBaseUrlForPictures;

	private final String myBaseUrlForSoundEffects;

	private final String myBaseUrlForSprites;

	public BaseUrlSpecs(String baseUrlForData, String baseUrlForPictures, String baseUrlForSprites, String baseUrlForSoundEffects,
			String baseUrlForJukebox, CanvasSpecs canvasSpecs)
	{
		myBaseUrlForData = baseUrlForData;
		myBaseUrlForPictures = baseUrlForPictures.replace("{prefix}", canvasSpecs.getPrefix());
		myBaseUrlForSprites = baseUrlForSprites.replace("{prefix}", canvasSpecs.getPrefix());
		myBaseUrlForSoundEffects = baseUrlForSoundEffects;
		myBaseUrlForJukebox = baseUrlForJukebox;
	}

	public String getBaseUrlForData()
	{
		return myBaseUrlForData;
	}

	public String getBaseUrlForJukebox()
	{
		return myBaseUrlForJukebox;
	}

	public String getBaseUrlForPictures()
	{
		return myBaseUrlForPictures;
	}

	public String getBaseUrlForSoundEffects()
	{
		return myBaseUrlForSoundEffects;
	}

	public String getBaseUrlForSprites()
	{
		return myBaseUrlForSprites;
	}
}
