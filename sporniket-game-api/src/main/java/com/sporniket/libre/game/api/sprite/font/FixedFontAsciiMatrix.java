/**
 * 
 */
package com.sporniket.libre.game.api.sprite.font;

import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.game.api.sprite.Actor;
import com.sporniket.libre.game.api.sprite.ActorBank;
import com.sporniket.libre.game.api.sprite.ActorBankSet;
import com.sporniket.libre.game.api.sprite.Sequence;
import com.sporniket.libre.game.api.sprite.SequenceBank;
import com.sporniket.libre.game.api.sprite.Sprite;
import com.sporniket.libre.game.api.sprite.SpriteBank;
import com.sporniket.libre.game.api.types.BlocDefinition;
import com.sporniket.libre.game.api.types.Position.Vector;

/**
 * Simulate a console screen with a fixed font {@link ActorBankSet}.
 * 
 * To display the console, retrieve the sprites and actors ({@link #getSpriteBank()} and {@link #getActorBank()}) and use them with
 * the matching glyph picture.
 * 
 * <p>
 * The Actor bank set will have two variant of each glyph. Thus there will be two group of sprite for glyphes 32 to 126 (ascii
 * codes).
 * 
 * <p>
 * The first sprite definition will be used as reference to know the glyph bounding box and the usual hotspot.
 * 
 * <p>
 * This class use only the {@link SpriteBank} and the {@link SequenceBank}, and use it's own {@link ActorBank}.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * api</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class FixedFontAsciiMatrix
{
	/**
	 * Text alignement for {@link FixedFontAsciiMatrix#printWordWrappedTextFromLine(int, String, TextAlign)} and
	 * {@link FixedFontAsciiMatrix#printWordWrappedTextHighlightedFromLine(int, String, TextAlign)}.
	 * <p>
	 * &copy; Copyright 2010-2013 David Sporn
	 * </p>
	 * <hr>
	 * 
	 * <p>
	 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
	 * 
	 * <p>
	 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of
	 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
	 * your option) any later version.
	 * 
	 * <p>
	 * <i>The Sporniket Game Library &#8211; api</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
	 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
	 * License for more details.
	 * 
	 * <p>
	 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
	 * api</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
	 * 
	 * <hr>
	 * 
	 * @author David SPORN 
	 * 
	 * @version 0-SNAPSHOT
	 * @since 0-SNAPSHOT
	 */
	public static enum TextAlign
	{
		CENTER,
		LEFT,
		RIGHT;
	}

	/**
	 * valid ascii codes are below this value (excluded).
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final int ASCII_CODE__MAX = 127;

	/**
	 * Invalid chars will be converted to a question mark.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final int ASCII_CODE__QUESTION_MARK = '?';

	/**
	 * Valid codes are above this value (included), also used to compute the glyph to use.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final int ASCII_CODE__SPACE = 32;

	/**
	 * Number of glyphes from code ascii 32 to 126 included, used as increment to display the other font glyphes.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final int SPRITE_GROUP_SIZE = 95;

	/**
	 * The bank of actor created, each for a character in the matrix, used for the redraw.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final ActorBank myActorBank = new ActorBank();

	/**
	 * Number of characters per line.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final int myColumnCount;

	/**
	 * Each character of the matrix is directly accessible through this array.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final Actor[] myDirectAccessToChars;

	/**
	 * Number of lines in the matrix.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final int myRowCount;

	/**
	 * The sequence bank will have only one definition that will be used to instanciate each actor.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final SequenceBank mySequenceBank;

	/**
	 * Glyph set, has two groups, each describing glyph from ascii code 32 to 126 included.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private final SpriteBank mySpriteBank;

	/**
	 * @param spriteBank
	 *            Glyph set, has two groups, each describing glyph from ascii code 32 to 126 included.
	 * @param sequenceBank
	 *            The sequence bank will have only one definition that will be used to instanciate each actor.
	 * @param displayArea
	 *            The area in which the matrix will be displayed.
	 * @since 0-SNAPSHOT
	 */
	public FixedFontAsciiMatrix(SpriteBank spriteBank, SequenceBank sequenceBank, BlocDefinition displayArea)
	{
		mySpriteBank = spriteBank;
		mySequenceBank = sequenceBank;

		// Matrix features computing
		Sprite _referenceGlyph = mySpriteBank.get(0);
		int _glyphWidth = _referenceGlyph.getBloc().getWidth();
		int _glyphHeight = _referenceGlyph.getBloc().getHeight();
		int _interlineHeight = _glyphHeight / 4;
		int _lineHeight = _glyphHeight + _interlineHeight;

		myColumnCount = displayArea.getWidth() / _glyphWidth;
		myRowCount = (displayArea.getHeight() + _interlineHeight) / _lineHeight;
		int _cellCount = myColumnCount * getRowCount();
		myDirectAccessToChars = new Actor[_cellCount];

		// Matrix initialisation
		Sequence _referenceSequence = mySequenceBank.get(0);

		int _currentCell = 0;
		int _currentY = displayArea.getTop() + _referenceGlyph.getHotSpotY();
		for (int _row = 0; _row < getRowCount(); _row++)
		{
			int _currentX = displayArea.getLeft() + _referenceGlyph.getHotSpotX();
			for (int _col = 0; _col < myColumnCount; _col++)
			{
				Actor _newChar = Actor.createFromSequence(_referenceSequence);
				_newChar.setActive(false);
				_newChar.getPosition().setCurrentPositionVector(new Vector(_currentX, _currentY));

				myActorBank.add(_newChar);
				myDirectAccessToChars[_currentCell] = _newChar;

				// next
				_currentX += _glyphWidth;
				_currentCell++;
			}

			// next line
			_currentY += _lineHeight;
		}
	}

	/**
	 * clear the matrix (a.k.a <code>CLS</code>)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void clear()
	{
		for (Actor _char : myDirectAccessToChars)
		{
			_char.getSequence().setActorBase(0);
			_char.setActive(false);
		}
	}

	/**
	 * @param message
	 * @param startCell
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private int computeEndCell(String message, int startCell)
	{
		int _endCell = startCell + message.length();
		if (_endCell > myDirectAccessToChars.length)
		{
			_endCell = myDirectAccessToChars.length;
		}
		return _endCell;
	}

	/**
	 * @param line
	 * @param col
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private int computeStartCell(int line, int col)
	{
		return line * getColumnCount() + col;
	}

	/**
	 * Find the sprite to use for the character.
	 * 
	 * @param character
	 *            character to convert.
	 * @return the sprite index.
	 * @since 0-SNAPSHOT
	 */
	private int convertToHighlightedGlyph(char character)
	{
		return character - ASCII_CODE__SPACE + SPRITE_GROUP_SIZE;
	}

	/**
	 * Find the sprite to use for the character.
	 * 
	 * @param character
	 *            character to convert.
	 * @return the sprite index.
	 * @since 0-SNAPSHOT
	 */
	private int convertToNormalGlyph(char character)
	{
		return character - ASCII_CODE__SPACE;
	}

	/**
	 * @param line
	 * @param message
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private List<String> createWordWrappedLines(int line, String message)
	{
		List<String> _lines = new ArrayList<String>();
		int _currentLine = line;
		String[] _words = message.split(" ");
		int _currentWord = 0;
		while (_currentLine < getRowCount() && _currentWord < _words.length)
		{
			StringBuilder _messagePart = new StringBuilder();
			// case : first word is too big
			String _word = _words[_currentWord];
			if (_word.length() > getColumnCount())
			{
				_messagePart.append(_word.substring(0, getColumnCount()));
				_words[_currentWord] = _word.substring(getColumnCount());
			}
			else
			{
				_messagePart.append(_word);
				int _charLeft = getColumnCount() - _messagePart.length();
				_currentWord++;
				while (_currentWord < _words.length && _charLeft > 0)
				{
					_word = " " + _words[_currentWord];
					if (_word.length() <= _charLeft)
					{
						_messagePart.append(_word);
						_currentWord++;
						_charLeft -= _word.length();
					}
					else
					{
						break;
					}
				}
			}

			// commit
			_lines.add(_messagePart.toString());
			_currentLine++;
		}
		return _lines;
	}

	/**
	 * Get actorBank, use this for drawing the console.
	 * 
	 * @return the actorBank
	 * @since 0-SNAPSHOT
	 */
	public ActorBank getActorBank()
	{
		return myActorBank;
	}

	/**
	 * Compute the position of the text to be aligned on the line.
	 * 
	 * @param message
	 *            the message to align.
	 * @param align
	 *            the align to perform.
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private int getAlignedPosition(String message, TextAlign align)
	{
		int _remainingSpace = getColumnCount() - message.length();
		int _result = 0; // case Left
		switch (align)
		{
			case RIGHT:
				_result = _remainingSpace;
				break;
			case CENTER:
				_result = _remainingSpace / 2;
				break;
			default:
				break;
		}
		return _result;
	}

	/**
	 * Get spriteBank, use this for drawing the console.
	 * 
	 * @return the spriteBank
	 * @since 0-SNAPSHOT
	 */
	public SpriteBank getSpriteBank()
	{
		return mySpriteBank;
	}

	/**
	 * @param character
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private boolean isValidChar(char character)
	{
		return character >= ASCII_CODE__SPACE && character < ASCII_CODE__MAX;
	}

	/**
	 * Print message using the "normal" glyphes.
	 * 
	 * @param line
	 *            line to start at.
	 * @param col
	 *            column to start at.
	 * @param message
	 *            message to print.
	 * @return the line next to the printed text.
	 * @since 0-SNAPSHOT
	 */
	public int printAt(int line, int col, String message)
	{
		int _startCell = computeStartCell(line, col);
		int _endCell = computeEndCell(message, _startCell);

		int _currentCell = _startCell;
		char[] _chars = message.toCharArray();
		for (char _char : _chars)
		{
			if (!(_currentCell < _endCell))
			{
				break;
			}

			int _glyph = ASCII_CODE__QUESTION_MARK;
			if (isValidChar(_char))
			{
				_glyph = convertToNormalGlyph(_char);
			}

			Actor _cell = myDirectAccessToChars[_currentCell];
			_cell.setActive(true);
			_cell.getSequence().setActorBase(_glyph);

			// next
			_currentCell++;
		}
		int _line = _currentCell / getColumnCount();
		int _row = _currentCell % getColumnCount();
		if (_row > 0)
		{
			_line++;
		}
		return _line;
	}

	/**
	 * Print message using the "highlighted" glyphes.
	 * 
	 * @param line
	 *            line to start at.
	 * @param col
	 *            column to start at.
	 * @param message
	 *            message to print.
	 * @return the line next to the printed text.
	 * @since 0-SNAPSHOT
	 */
	public int printHighlightedAt(int line, int col, String message)
	{
		int _startCell = computeStartCell(line, col);
		int _endCell = computeEndCell(message, _startCell);

		int _currentCell = _startCell;
		char[] _chars = message.toCharArray();
		for (char _char : _chars)
		{
			if (!(_currentCell < _endCell))
			{
				break;
			}

			int _glyph = ASCII_CODE__QUESTION_MARK;
			if (isValidChar(_char))
			{
				_glyph = convertToHighlightedGlyph(_char);
			}

			Actor _cell = myDirectAccessToChars[_currentCell];
			_cell.setActive(true);
			_cell.getSequence().setActorBase(_glyph);

			// next
			_currentCell++;
		}

		// compute the next line
		int _line = _currentCell / getColumnCount();
		int _row = _currentCell % getColumnCount();
		if (_row > 0)
		{
			_line++;
		}
		return _line;
	}

	/**
	 * Print a text accross several lines, word-wrapped and aligned.
	 * 
	 * @param line
	 *            print the message from this line.
	 * @param message
	 *            the message to print.
	 * @param align
	 *            text alignement.
	 * @return the line next to the printed text.
	 * @since 0-SNAPSHOT
	 */
	public int printWordWrappedTextFromLine(int line, String message, TextAlign align)
	{
		// first split the message into word-wrapped lines
		List<String> _lines = createWordWrappedLines(line, message);

		// print each lines, taking into account the alignment
		int _currentLine = line;
		for (String _line : _lines)
		{
			int _xpos = getAlignedPosition(_line, align);
			printAt(_currentLine, _xpos, _line);
			_currentLine++;
		}
		return _currentLine;
	}

	/**
	 * Print a highlighted text accross several lines, word-wrapped and aligned.
	 * 
	 * @param line
	 *            print the message from this line.
	 * @param message
	 *            the message to print.
	 * @param align
	 *            text alignement.
	 * @return the line next to the printed text.
	 * @since 0-SNAPSHOT
	 */
	public int printWordWrappedTextHighlightedFromLine(int line, String message, TextAlign align)
	{
		// first split the message into word-wrapped lines
		List<String> _lines = createWordWrappedLines(line, message);

		// print each lines, taking into account the alignment
		int _currentLine = line;
		for (String _line : _lines)
		{
			int _xpos = getAlignedPosition(_line, align);
			printHighlightedAt(_currentLine, _xpos, _line);
			_currentLine++;
		}
		return _currentLine;
	}

	/**
	 * Get columnCount.
	 * @return the columnCount
	 * @since 0-SNAPSHOT
	 */
	public int getColumnCount()
	{
		return myColumnCount;
	}

	/**
	 * Get rowCount.
	 * @return the rowCount
	 * @since 0-SNAPSHOT
	 */
	public int getRowCount()
	{
		return myRowCount;
	}

}
