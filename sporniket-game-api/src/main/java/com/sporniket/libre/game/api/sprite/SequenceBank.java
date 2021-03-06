package com.sporniket.libre.game.api.sprite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.sporniket.libre.game.api.types.Bank;

/**
 * Sequence instances are grouped in an SequenceBank.
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
 * @deprecated use a list
 */
public class SequenceBank implements List<Sequence>, Bank {

	private List<Sequence> myContainer = new ArrayList<Sequence>(
			Bank.INITIAL_CAPACITY);

	/**
	 * @return the container
	 */
	private List<Sequence> getContainer() {
		return myContainer;
	}

	public boolean add(Sequence item) {
		return getContainer().add(item);
	}

	public void add(int index, Sequence item) {
		getContainer().add(index, item);
	}

	public boolean addAll(Collection<? extends Sequence> collection) {
		return getContainer().addAll(collection);
	}

	public boolean addAll(int index, Collection<? extends Sequence> collection) {
		return getContainer().addAll(index, collection);
	}

	public void clear() {
		getContainer().clear();
	}

	public boolean contains(Object item) {
		return getContainer().contains(item);
	}

	public boolean containsAll(Collection<?> collection) {
		return getContainer().containsAll(collection);
	}

	public Sequence get(int index) {
		return getContainer().get(index);
	}

	public int indexOf(Object item) {
		return getContainer().indexOf(item);
	}

	public boolean isEmpty() {
		return getContainer().isEmpty();
	}

	public Iterator<Sequence> iterator() {
		return getContainer().iterator();
	}

	public int lastIndexOf(Object item) {
		return getContainer().lastIndexOf(item);
	}

	public ListIterator<Sequence> listIterator() {
		return getContainer().listIterator();
	}

	public ListIterator<Sequence> listIterator(int index) {
		return getContainer().listIterator(index);
	}

	public boolean remove(Object item) {
		return getContainer().remove(item);
	}

	public Sequence remove(int index) {
		return getContainer().remove(index);
	}

	public boolean removeAll(Collection<?> collection) {
		return getContainer().removeAll(collection);
	}

	public boolean retainAll(Collection<?> collection) {
		return getContainer().retainAll(collection);
	}

	public Sequence set(int index, Sequence item) {
		return getContainer().set(index, item);
	}

	public int size() {
		return getContainer().size();
	}

	public List<Sequence> subList(int start, int end) {
		return getContainer().subList(start, end);
	}

	public Object[] toArray() {
		return getContainer().toArray();
	}

	public <T> T[] toArray(T[] target) {
		return getContainer().toArray(target);
	}

}