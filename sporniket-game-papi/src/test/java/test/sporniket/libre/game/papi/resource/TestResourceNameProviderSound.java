package test.sporniket.libre.game.papi.resource;

import java.io.File;
import java.util.Locale;

import junit.framework.TestCase;

import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;
import com.sporniket.libre.game.papi.resource.ResourceDescriptor;
import com.sporniket.libre.game.papi.resource.ResourceLocalizable;
import com.sporniket.libre.game.papi.resource.ResourceNameContext;
import com.sporniket.libre.game.papi.resource.ResourceNameProviderSound;

/**
 * Test suit for {@link ResourceNameProviderSound}.
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
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class TestResourceNameProviderSound extends TestCase
{

	/**
	 * A basic resource is not localizable.
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testBasicResource()
	{
		ResourceDescriptor _testResource = new ResourceDescriptor("test", "png");
		ResourceNameProviderSound _provider = new ResourceNameProviderSound();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.Native.Landscape.FHD, Locale.getDefault());
		String _result = _provider.getName(_testResource, _context);
		String _expected = "sfx" + File.separator + "test.png";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}

	/**
	 * A localized resource, with a fully specified locale (language-country) that matches.
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testLocalizedResource__fullyCompatible()
	{
		ResourceLocalizable _testResource = new ResourceLocalizable("test", "png", new String[]
		{
				Locale.CANADA_FRENCH.toString(), Locale.FRANCE.toString()
		});
		ResourceNameProviderSound _provider = new ResourceNameProviderSound();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.Native.Landscape.FHD, Locale.FRANCE);
		String _result = _provider.getName(_testResource, _context);
		String _expected = "sfx" + File.separator + "test-fr_FR.png";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}

	/**
	 * A localized resource, with a fully specified locale (language-country) that matches.
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testLocalizedResource__notCompatible()
	{
		ResourceLocalizable _testResource = new ResourceLocalizable("test", "png", new String[]
		{
				Locale.FRENCH.toString(), Locale.ENGLISH.toString()
		});
		ResourceNameProviderSound _provider = new ResourceNameProviderSound();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.Native.Landscape.FHD, Locale.GERMANY);
		String _result = _provider.getName(_testResource, _context);
		String _expected = "sfx" + File.separator + "test-en.png";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}

	/**
	 * A localized resource, with a fully specified locale (language-country) that matches.
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testLocalizedResource__partiallyCompatible()
	{
		ResourceLocalizable _testResource = new ResourceLocalizable("test", "png", new String[]
		{
				Locale.FRENCH.toString(), Locale.ENGLISH.toString()
		});
		ResourceNameProviderSound _provider = new ResourceNameProviderSound();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.Native.Landscape.FHD, Locale.FRANCE);
		String _result = _provider.getName(_testResource, _context);
		String _expected = "sfx" + File.separator + "test-fr.png";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}

	/**
	 * A localizable resource, but no supported locale.
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testNotLocalizedResource()
	{
		ResourceLocalizable _testResource = new ResourceLocalizable("test", "png");
		ResourceNameProviderSound _provider = new ResourceNameProviderSound();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.Native.Landscape.FHD, Locale.getDefault());
		String _result = _provider.getName(_testResource, _context);
		String _expected = "sfx" + File.separator + "test.png";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}
}
