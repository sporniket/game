/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import java.util.ArrayList;

import com.sporniket.libre.game.api.InvalidValueException;
import com.sporniket.libre.game.api.OutOfRangeException;
import com.sporniket.libre.game.api.sprite.SequenceItem.Type;
import com.sporniket.libre.game.api.types.Updatable;

/**
 * A sequence instance reference a sequence being played, memorizing the current item and the time since the last change.
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
 */
public class SequenceInstance implements Updatable
{

	/**
	 * Offset to add to change the frame while using the same sequence.
	 * 
	 * Exemple of use : a starship having different appearance according to a level, but animated using the same sequence.
	 */
	private int myActorBase = 0;

	private Long myClock = 0L;

	private Integer myPosition = 0;

	private Sequence mySequence = null;

	/**
	 * Copy of the sequence, optimised for direct access.
	 */
	private ArrayList<SequenceItem> mySequenceCache = new ArrayList<SequenceItem>();

	/**
	 * Check that all field are set.
	 */
	private void checkState()
	{
		if (null == getSequence())
		{
			throw new NullPointerException("Sequence is null");
		}
		if (null == getPosition())
		{
			throw new NullPointerException("Position is null");
		}
		else if (getPosition().compareTo(getSequence().size()) >= 0)
		{
			throw new OutOfRangeException("Position is outside the sequence.");
		}
		if (null == getClock())
		{
			throw new NullPointerException("Clock is null");
		}
	}

	/**
	 * @return the actorBase
	 */
	public int getActorBase()
	{
		return myActorBase;
	}

	/**
	 * @return the clock
	 */
	public Long getClock()
	{
		return myClock;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition()
	{
		return myPosition;
	}

	/**
	 * @return the sequence
	 */
	public Sequence getSequence()
	{
		return mySequence;
	}

	/**
	 * @return the sequenceCache
	 */
	public ArrayList<SequenceItem> getSequenceCache()
	{
		return mySequenceCache;
	}

	/**
	 * The current value according to the sequence.
	 */
	private int myCurrentValue = -1;

	/**
	 * @return the currentValue
	 */
	public int getCurrentValue()
	{
		return myCurrentValue;
	}

	public int getCurrentSprite()
	{
		return getActorBase() + getCurrentValue();
	}

	/**
	 * Go back to the beginning of the sequence.
	 */
	public void reset()
	{
		setPosition(new Integer(0));
		setClock(new Long(0));
		updateCurrentValue(retrieveCurrentItem());
	}

	/**
	 * @param actorBase
	 *            the actorBase to set
	 */
	public void setActorBase(int actorBase)
	{
		myActorBase = actorBase;
	}

	/**
	 * @param clock
	 *            the clock to set
	 */
	public void setClock(Long clock)
	{
		myClock = clock;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer position)
	{
		if (position < 0)
		{
			throw new InvalidValueException("Position MUST be greater than or equal to 0.");
		}
		if (getPosition() >= getSequenceCache().size())
		{
			throw new InvalidValueException("Position MUST be less than getSequenceCache().size()=" + getSequenceCache().size());
		}
		myPosition = position;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(Sequence sequence)
	{
		mySequence = sequence;
		mySequenceCache = new ArrayList<SequenceItem>(sequence.size());
		mySequenceCache.addAll(mySequence);
		reset();
	}

	public void update(Long timeElapsed)
	{
		checkState();
		SequenceItem _currentItem = retrieveCurrentItem();
		switch (_currentItem.getType())
		{
			case STILL:
				// do nothing
				updateCurrentValue(_currentItem);
				return;
			case FRAME:
				// count time, and move if appliable
				myClock += timeElapsed;
				if (getClock() > _currentItem.getDuration())
				{
					myClock -= _currentItem.getDuration();
					if (getPosition() < (getSequenceCache().size() - 1))
					{
						++myPosition;
						_currentItem = retrieveCurrentItem();
					}
					updateCurrentValue(_currentItem);
					//2012-10-17:DSP:Animation shows sprite 0 when looping.
					if (_currentItem.getType() == Type.LOOP)
					{
						followLoop(_currentItem);
					}
				}
				break;
			case LOOP:
				followLoop(_currentItem);
				setClock(0L);
				break;

			default:
				break;
		}
	}

	/**
	 * @param loopItem
	 */
	private void followLoop(SequenceItem loopItem)
	{
		setPosition(loopItem.getValue());
		loopItem = retrieveCurrentItem();
		updateCurrentValue(loopItem);
	}

	/**
	 * @return
	 */
	private SequenceItem retrieveCurrentItem()
	{
		SequenceItem _currentItem = getSequenceCache().get(getPosition());
		return _currentItem;
	}

	/**
	 * @param currentItem
	 */
	private void updateCurrentValue(SequenceItem currentItem)
	{
		myCurrentValue = currentItem.getValue();
	}
}
