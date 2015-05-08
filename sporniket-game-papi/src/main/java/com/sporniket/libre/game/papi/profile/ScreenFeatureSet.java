package com.sporniket.libre.game.papi.profile;

/**
 * Describe some screen feature set (resolution, orientation, unit to pixel converter factor for resolution independant redraw).
 * 
 * This feature set, especially dimension, might be not the real value, but an ideal value. That is because there might be several
 * dimension that have the same designation (e.g. the mess of WQVGA resolutions found on the wikipedia page...).
 * 
 * Built-in Screen dimension are in independant unit and will thus be 320x240 in normal mode, 400x240 in wide mode.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * @see http://en.wikipedia.org/wiki/Graphic_display_resolutions
 */
public class ScreenFeatureSet
{
	/**
	 * Natives (i.e. x and y factor = 1) screen features.
	 * 
	 * @author dsporn
	 * 
	 */
	public static class Native
	{
		/**
		 * Natives Screen features for Portrait orientation.
		 * 
		 * @author dsporn
		 * 
		 */
		public static class Portrait
		{
			public static final ScreenFeatureSet QVGA = new ScreenFeatureSet(240, 320, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.QVGA, 1, 1);

			public static final ScreenFeatureSet HVGA = new ScreenFeatureSet(320, 480, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.HVGA, 1, 1);

			public static final ScreenFeatureSet VGA = new ScreenFeatureSet(480, 640, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.VGA, 1, 1);

			public static final ScreenFeatureSet WVGA_WITH_ACTION_BAR = new ScreenFeatureSet(480, 742, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.VGA, 1, 1);

			public static final ScreenFeatureSet WVGA = new ScreenFeatureSet(480, 800, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.VGA, 1, 1);

			public static final ScreenFeatureSet QHD_WITH_ACTION_BAR = new ScreenFeatureSet(540, 892, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.QHD, 1, 1);

			public static final ScreenFeatureSet QHD = new ScreenFeatureSet(540, 960, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.QHD, 1, 1);

			public static final ScreenFeatureSet HD_WITH_ACTION_BAR = new ScreenFeatureSet(720, 1190, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.HD, 1, 1);

			public static final ScreenFeatureSet HD = new ScreenFeatureSet(720, 1280, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.HD, 1, 1);

			public static final ScreenFeatureSet FHD_WITH_ACTION_BAR = new ScreenFeatureSet(1080, 1834, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.FHD, 1, 1);

			public static final ScreenFeatureSet FHD = new ScreenFeatureSet(1080, 1920, false, ScreenOrientation.PORTRAIT,
					ScreenDefinition.FHD, 1, 1);
		}

		/**
		 * Natives Screen features for Landscape orientation.
		 * 
		 * @author dsporn
		 * 
		 */
		public static class Landscape
		{
			public static final ScreenFeatureSet QVGA = new ScreenFeatureSet(320, 240, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.QVGA, 1, 1);

			public static final ScreenFeatureSet HVGA = new ScreenFeatureSet(480, 320, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.HVGA, 1, 1);

			public static final ScreenFeatureSet VGA = new ScreenFeatureSet(640, 480, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.VGA, 1, 1);

			public static final ScreenFeatureSet WVGA_WITH_ACTION_BAR = new ScreenFeatureSet(742, 480, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.VGA, 1, 1);

			public static final ScreenFeatureSet WVGA = new ScreenFeatureSet(800, 480, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.VGA, 1, 1);

			public static final ScreenFeatureSet QHD_WITH_ACTION_BAR = new ScreenFeatureSet(892, 540, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.QHD, 1, 1);

			public static final ScreenFeatureSet QHD = new ScreenFeatureSet(960, 540, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.QHD, 1, 1);

			public static final ScreenFeatureSet HD_WITH_ACTION_BAR = new ScreenFeatureSet(1190, 720, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.HD, 1, 1);

			public static final ScreenFeatureSet HD = new ScreenFeatureSet(1280, 720, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.HD, 1, 1);

			public static final ScreenFeatureSet FHD_WITH_ACTION_BAR = new ScreenFeatureSet(1834, 1080, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.FHD, 1, 1);

			public static final ScreenFeatureSet FHD = new ScreenFeatureSet(1920, 1080, false, ScreenOrientation.LANDSCAPE,
					ScreenDefinition.FHD, 1, 1);
		}
	}

	public static final ScreenFeatureSet QVGA_LANDSCAPE = new ScreenFeatureSet(320, 240, false, ScreenOrientation.LANDSCAPE,
			ScreenDefinition.QVGA, 1, 1);

	public static final ScreenFeatureSet QVGA_PORTRAIT = new ScreenFeatureSet(240, 320, false, ScreenOrientation.PORTRAIT,
			ScreenDefinition.QVGA, 1, 1);

	public static final ScreenFeatureSet VGA_LANDSCAPE = new ScreenFeatureSet(320, 240, false, ScreenOrientation.LANDSCAPE,
			ScreenDefinition.VGA, 2, 2);

	public static final ScreenFeatureSet VGA_PORTRAIT = new ScreenFeatureSet(240, 320, false, ScreenOrientation.PORTRAIT,
			ScreenDefinition.VGA, 2, 2);

	public static final ScreenFeatureSet WQVGA_LANDSCAPE = new ScreenFeatureSet(400, 240, true, ScreenOrientation.LANDSCAPE,
			ScreenDefinition.QVGA, 1, 1);

	public static final ScreenFeatureSet WQVGA_PORTRAIT = new ScreenFeatureSet(240, 400, true, ScreenOrientation.PORTRAIT,
			ScreenDefinition.QVGA, 1, 1);

	public static final ScreenFeatureSet WVGA_LANDSCAPE = new ScreenFeatureSet(400, 240, true, ScreenOrientation.LANDSCAPE,
			ScreenDefinition.VGA, 2, 2);

	public static final ScreenFeatureSet WVGA_PORTRAIT = new ScreenFeatureSet(240, 400, true, ScreenOrientation.PORTRAIT,
			ScreenDefinition.VGA, 2, 2);

	/**
	 * Graphics definition required.
	 */
	private ScreenDefinition myGraphicsDefinition;

	/**
	 * Dimension in pixel independant units.
	 */
	private int myHeight;

	/**
	 * The screen orientation.
	 */
	private ScreenOrientation myOrientation;

	/**
	 * true if the screen is 16:9 or near this shape.
	 */
	private boolean myWideScreen;

	/**
	 * Dimension in pixel independant units.
	 */
	private int myWidth;

	/**
	 * Scaling factor to convert from independant unit to pixels.
	 */
	private int myXfactor;

	/**
	 * Scaling factor to convert from independant unit to pixels.
	 */
	private int myYfactor;

	private ScreenFeatureSet(int width, int height, boolean wideScreen, ScreenOrientation orientation,
			ScreenDefinition graphicsDefinition, int xfactor, int yfactor)
	{
		myWidth = width;
		myHeight = height;
		myWideScreen = wideScreen;
		myOrientation = orientation;
		myGraphicsDefinition = graphicsDefinition;
		myXfactor = xfactor;
		myYfactor = yfactor;
	}

	/**
	 * @return the graphicsDefinition
	 */
	public ScreenDefinition getGraphicsDefinition()
	{
		return myGraphicsDefinition;
	}

	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return myHeight;
	}

	/**
	 * @return the orientation
	 */
	public ScreenOrientation getOrientation()
	{
		return myOrientation;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return myWidth;
	}

	/**
	 * @return the xfactor
	 */
	public int getXfactor()
	{
		return myXfactor;
	}

	/**
	 * @return the yfactor
	 */
	public int getYfactor()
	{
		return myYfactor;
	}

	/**
	 * @return the wideScreen
	 */
	public boolean isWideScreen()
	{
		return myWideScreen;
	}

}