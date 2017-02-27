package com.sporniket.libre.game.canvas;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.util.List;

import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.omg.PortableServer._ServantLocatorStub;

import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecEntry;
import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecEntryOffscreen;
import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecEntryUrl;
import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecs;
import com.sporniket.libre.game.canvas.descriptor.CanvasSpecs;
import com.sporniket.libre.io.parser.properties.SyntaxErrorException;

public class TestCanvasManagerSpecs
{
	private static String[] ENTRIES =
	{
			"foo1:url:to/my/file",
			"foo2:offscreen",
			"foo3:offscreen color DARK_GRAY",
			"foo4:offscreen 100x50",
			"foo5:offscreen 200x150 color clear"
	};

	@Test
	public void testGetEntries() throws SyntaxErrorException
	{
		// prepare
		CanvasSpecs _selectedCanvas = new CanvasSpecs("test", 800, 600, "tst");
		CanvasManagerSpecs _specs = new CanvasManagerSpecs();
		_specs.setCanvasSpecs(ENTRIES);

		// execute
		List<CanvasManagerSpecEntry> _entries = _specs.getEntries(_selectedCanvas);

		// verify
		assertThat(_entries.size(), is(5));
		assertOffscreenSpecificationByUrl(_entries.get(0), "foo1", "to/my/file");
		assertOffscreenSpecification(_entries.get(1), "foo2", Color.BLACK, 800, 600);
		assertOffscreenSpecification(_entries.get(2), "foo3", Color.DARK_GRAY, 800, 600);
		assertOffscreenSpecification(_entries.get(3), "foo4", Color.BLACK, 100, 50);
		assertOffscreenSpecification(_entries.get(4), "foo5", new Color(255, 255, 255, 0), 200, 150);
	}

	@Test(expected = SyntaxErrorException.class)
	public void testGetEntries_unknownColor() throws SyntaxErrorException
	{
		// prepare
		CanvasSpecs _selectedCanvas = new CanvasSpecs("test", 800, 600, "tst");
		CanvasManagerSpecs _specs = new CanvasManagerSpecs();
		_specs.setCanvasSpecs(new String[]
		{
				"foo1:offscreen color blurp"
		});

		// execute
		_specs.getEntries(_selectedCanvas);
	}

	private void assertOffscreenSpecification(CanvasManagerSpecEntry entry, String expectedName, Color expectedColor,
			int expectedWidth, int expectedHeight)
	{
		assertThat(entry.getClass().getName(), is(CanvasManagerSpecEntryOffscreen.class.getName()));
		CanvasManagerSpecEntryOffscreen _offscreenSpec = (CanvasManagerSpecEntryOffscreen) entry;
		assertThat(_offscreenSpec.getName(), is(expectedName));
		assertThat(_offscreenSpec.getColor().getRGB(), is(expectedColor.getRGB()));
		assertThat(_offscreenSpec.getColor().getAlpha(), is(expectedColor.getAlpha()));
		assertThat(_offscreenSpec.getWidth(), is(expectedWidth));
		assertThat(_offscreenSpec.getHeight(), is(expectedHeight));
	}

	private void assertOffscreenSpecificationByUrl(CanvasManagerSpecEntry entry, String expectedName, String expectedUrl)
	{
		assertThat(entry.getClass().getName(), is(CanvasManagerSpecEntryUrl.class.getName()));
		CanvasManagerSpecEntryUrl _offscreenSpec = (CanvasManagerSpecEntryUrl) entry;
		assertThat(_offscreenSpec.getName(), is(expectedName));
		assertThat(_offscreenSpec.getUrl(), is(expectedUrl));
	}

}
