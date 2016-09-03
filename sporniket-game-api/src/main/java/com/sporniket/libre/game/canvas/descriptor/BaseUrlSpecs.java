/**
 * 
 */
package com.sporniket.libre.game.canvas.descriptor;

/**
 * Base url that will be used to load various kind of ressources.
 * 
 * @author dsporn
 *
 */
public class BaseUrlSpecs
{
	private String myBaseUrlForData;

	private String myBaseUrlForJukebox;

	private String myBaseUrlForPictures;

	private String myBaseUrlForSoundEffects;

	private String myBaseUrlForSprites;

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

	public void setBaseUrlForData(String baseUrlForData)
	{
		myBaseUrlForData = baseUrlForData;
	}

	public void setBaseUrlForJukebox(String baseUrlForJukebox)
	{
		myBaseUrlForJukebox = baseUrlForJukebox;
	}

	public void setBaseUrlForPictures(String baseUrlForPictures)
	{
		myBaseUrlForPictures = baseUrlForPictures;
	}

	public void setBaseUrlForSoundEffects(String baseUrlForSoundEffects)
	{
		myBaseUrlForSoundEffects = baseUrlForSoundEffects;
	}

	public void setBaseUrlForSprites(String baseUrlForSprites)
	{
		myBaseUrlForSprites = baseUrlForSprites;
	}
}
