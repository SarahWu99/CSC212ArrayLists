package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ArrayWrapper;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.RanOutOfSpaceError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * FixedSizeList is a List with a maximum size.
 * @author jfoley
 *
 * @param <T>
 */
public class FixedSizeList<T> extends ListADT<T> {
	/**
	 * This is the array of fixed size.
	 */
	private ArrayWrapper<T> array;
	/**
	 * This keeps track of what we have used and what is left.
	 */
	private int fill;

	/**
	 * Construct a new FixedSizeList with a given maximum size.
	 * @param maximumSize - the size of the array to use.
	 */
	public FixedSizeList(int maximumSize) {
		this.array = new ArrayWrapper<>(maximumSize);
		this.fill = 0;
	}

	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}

	@Override
	public int size() {
		return this.fill;
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		this.array.setIndex(index, value);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		return this.array.getIndex(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		this.checkExclusiveIndex(0);
		return this.array.getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		this.checkExclusiveIndex(this.size()-1);
		return this.array.getIndex(this.size()-1);
	}

	@Override
	//Add an item with value before the item at the given index.
	public void addIndex(int index, T value) {
		checkInclusiveIndex(index);
		if (fill < array.size()) { 
		// slide to the right
			for (int i = fill-1; i >= index; i--){
				 array.setIndex(i+1, array.getIndex(i));
			 }
			array.setIndex(index, value);
			fill++;
		}
		else {
			throw new RanOutOfSpaceError();
		}
	}

	@Override
	public void addFront(T value) {
		this.addIndex(0, value);
	}

	@Override
	public void addBack(T value) {
		if (fill < array.size()) {
			array.setIndex(fill++, value);
		} else {
			throw new RanOutOfSpaceError();
		}
	}

	@Override
	public T removeIndex(int index) {
		this.checkNotEmpty();
		// grab and hold the thing to delete.
		T removed = this.getIndex(index);
		fill--;
		// Slide to the left.
		for (int i = index; i < fill; i++) {
		this.array.setIndex(i, array.getIndex(i+1));
		}
		// Erase the duplicate, last-item.
		this.array.setIndex(fill, null);
		return removed;
	}

	@Override
	public T removeBack() {
		return removeIndex(fill-1);
	}

	@Override
	public T removeFront() {
		return removeIndex(0);
	}

	/**
	 * Is this data structure full? Used in challenge: {@linkplain ChunkyArrayList}.
	 * 
	 * @return if true this FixedSizeList is full.
	 */
	public boolean isFull() {
		return this.fill == this.array.size();
	}

}
