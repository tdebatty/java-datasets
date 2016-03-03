/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This utility class implements a basic iterator for arbitrary arrays.
 */
class ArrayIterator<T> implements Iterator<T> {

    private int next;
    private T[] data;

    public ArrayIterator(T[] data) {
        this.data = data;
        next = 0;
    }

    public boolean hasNext() {
        return next < data.length;
    }

    public T next() {
        if (hasNext()) {
            return data[next++];
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
