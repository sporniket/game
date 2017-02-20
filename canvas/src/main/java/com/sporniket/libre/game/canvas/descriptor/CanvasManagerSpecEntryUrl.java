/**
 *
 */
package com.sporniket.libre.game.canvas.descriptor;

/**
 * Canvas specification entry defined by the url of a picture.
 *
 * @author dsporn
 *
 */
public class CanvasManagerSpecEntryUrl extends CanvasManagerSpecEntry
{
	private String myUrl;

	public String getUrl()
	{
		return myUrl;
	}

	public void setUrl(String url)
	{
		myUrl = url;
	}
}
