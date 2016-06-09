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

package info.debatty.java.datasets.gaussian;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset extends info.debatty.java.datasets.Dataset<Double[]> {
    private final ArrayList<Center> centers;
    private int size = -1;

    /**
     *
     */
    public Dataset() {
        this.centers = new ArrayList<Center>();
    }

    /**
     * Create a dataset of a given size.
     * @param size
     */
    public Dataset(final int size) {
        this.centers = new ArrayList<Center>();
        this.size = size;
    }

    /**
     * Add a Gaussian center to the mixture.
     * @param center to add to the mixture
     */
    public final void addCenter(final Center center) {
        centers.add(center);
    }

    /**
     *
     * @return an iterator of points (Double[])
     */
    public final Iterator<Double[]> iterator() {
        return new GaussianIterator(centers, size);
    }

    /**
     *
     */
    private static final class GaussianIterator implements Iterator<Double[]> {
        private final Random rand;
        private final int dimension;
        private final Center[] lookup_table;
        private int total_weight;
        private final int size;
        private int count;

        private GaussianIterator(
                final ArrayList<Center> centers, final int size) {
            this.size = size;
            dimension = centers.get(0).getDimension();
            rand = new Random();
            total_weight = 0;
            for (Center center : centers) {
                total_weight += center.getWeight();
            }
            lookup_table = new Center[total_weight];
            int i = 0;
            for (Center center : centers) {
                for (int j = 0; j < center.getWeight(); j++) {
                    lookup_table[i] = center;
                    i++;
                }
            }

        }

        public boolean hasNext() {
            return (size != count);
        }

        public Double[] next() {
            count++;
            Center center = lookup_table[rand.nextInt(total_weight)];

            Double[] point = new Double[dimension];
            for (int i = 0; i < dimension; i++) {
                point[i] = center.getCenter(i)
                        + rand.nextGaussian() * center.getDeviation(i);
            }
            return point;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }
    }
}
