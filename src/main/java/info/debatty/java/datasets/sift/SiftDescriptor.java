/*
 * The MIT License
 *
 * Copyright 2016 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.debatty.java.datasets.sift;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thibault Debatty
 */
public class SiftDescriptor extends LinkedList<SiftFeature>
        implements Serializable {

    private final String file;

    /**
     *
     * @param file
     * @param features
     */
    public SiftDescriptor(final String file, final List<SiftFeature> features) {
        super();
        this.file = file;
        this.addAll(features);
    }

    /**
     * Return the file name of the image.
     * @return
     */
    public final String getFile() {
        return file;
    }

    @Override
    public final boolean equals(final Object other) {
        SiftDescriptor other_descriptor = (SiftDescriptor) other;

        Iterator<SiftFeature> iterator = this.iterator();
        Iterator<SiftFeature> other_iterator = other_descriptor.iterator();

        while (iterator.hasNext()) {
            SiftFeature feature = iterator.next();
            SiftFeature other_feature = other_iterator.next();

            if (!feature.equals(other_feature)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
