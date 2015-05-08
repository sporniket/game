package test.sporniket.libre.game.papi.resource;

import java.io.File;
import java.util.Locale;

import junit.framework.TestCase;

import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;
import com.sporniket.libre.game.papi.resource.ResourceDescriptor;
import com.sporniket.libre.game.papi.resource.ResourceNameContext;
import com.sporniket.libre.game.papi.resource.ResourceNameProviderActor;

/**
 * Test suit for {@link ResourceNameProviderActor}.
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
public class TestResourceNameProviderActor extends TestCase
{

	/**
	 * basic test
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testBasic()
	{
		ResourceDescriptor _testResource = new ResourceDescriptor("test", "actors");
		ResourceNameProviderActor _provider = new ResourceNameProviderActor();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.Native.Landscape.FHD, Locale.getDefault());
		String _result = _provider.getName(_testResource, _context);
		String _expected = "data" + File.separator + "test--FHD-1-1.actors";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}

	/**
	 * test using a screen featureset with x and y factors > 1.
	 * 
	 * @since 0-SNAPSHOT
	 */
	public final void testFactors()
	{
		ResourceDescriptor _testResource = new ResourceDescriptor("test", "actors");
		ResourceNameProviderActor _provider = new ResourceNameProviderActor();
		ResourceNameContext _context = new ResourceNameContext(ScreenFeatureSet.VGA_LANDSCAPE, Locale.getDefault());
		String _result = _provider.getName(_testResource, _context);
		String _expected = "data" + File.separator + "test--VGA-2-2.actors";
		if (!_result.equals(_expected))
		{
			fail("Expected : [" + _expected + "] ; result : [" + _result + "]");
		}
	}
}
