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

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import static org.apache.commons.math3.stat.StatUtils.max;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset extends info.debatty.java.datasets.Dataset<double[]> {
    public static final String DEFAULT_DELIMITER = ";\t";

    private final ArrayList<Center> centers;
    private int size = -1;
    private final long random_seed;

    /**
     *
     */
    public Dataset() {
        this.centers = new ArrayList<Center>();
        Random rand = new Random();
        this.random_seed = rand.nextLong();
    }

    /**
     * Create a dataset of a given size.
     * @param size
     */
    public Dataset(final int size) {
        this();
        this.size = size;
    }

    public void saveCsv(OutputStream stream) {
        saveCsv(stream, DEFAULT_DELIMITER);
    }

    public void saveCsv(OutputStream stream, String delimiter) {
        PrintWriter writer = new PrintWriter(stream);
        for (double[] item : this) {
            writer.println(arrToString(item, delimiter));
        }

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
    public final Iterator<double[]> iterator() {
        return new GaussianIterator(centers, size, random_seed);
    }

    @Override
    public LinkedList<double[]> getAll() {
        if (this.size < 0) {
            throw new IllegalArgumentException("Cannot return all items of an"
                    + "infinite dataset! Set a dataset size!");
        }

        return super.getAll();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.centers != null ? this.centers.hashCode() : 0);
        hash = 97 * hash + this.size;
        hash = 97 * hash + (int) (this.random_seed ^ (this.random_seed >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dataset other = (Dataset) obj;
        if (this.size != other.size) {
            return false;
        }
        if (this.random_seed != other.random_seed) {
            return false;
        }
        if (this.centers != other.centers
                && (this.centers == null || !this.centers.equals(other.centers))) {
            return false;
        }
        return true;
    }

    private static String arrToString(double[] item, String delimiter) {
        String r = "";
        for (int i = 0; i < item.length; i++) {
            r += item[i] + delimiter;
        }
        return r;
    }

    /**
     * Builder that helps you create a random Gaussian Mixture dataset using
     * only a few simple parameters (dimensionality, centers, overlap).
     */
    public static class Builder {
        private final int dimensionality;
        private final int num_centers;
        private int size = -1;
        private Overlap overlap = Overlap.NONE;
        private boolean vary_weight = true;
        private boolean vary_deviation = true;

        /**
         * Various possibilities for clusters overlap.
         */
        public enum Overlap {
            /**
             * The clusters will not overlap.
             */
            NONE(6.0),

            /**
             * The clusters will slightly overlap, but graphical analysis allows
             * to easily recognize the different clusters.
             */
            MEDIUM(2.5),

            /**
             * The clusters will have a large overlap, which makes it difficult
             * to distinguish them.
             */
            HIGH(1.4);

            private final double value;

            Overlap(final double value) {
                this.value = value;
            }
        }


        /**
         * Constructor with mandatory parameters.
         * @param dimensionality
         * @param num_centers
         */
        public Builder(
                final int dimensionality,
                final int num_centers) {
            this.dimensionality = dimensionality;
            this.num_centers = num_centers;
        }

        /**
         * Define the overlap between clusters (default is none).
         * @param overlap
         * @return
         */
        public final Builder setOverlap(final Overlap overlap) {
            this.overlap = overlap;
            return this;
        }

        /**
         * Set the size of the dataset to build.
         * @param size
         * @return
         */
        public final Builder setSize(final int size) {
            this.size = size;
            return this;
        }

        /**
         * Should the different clusters contain different number of points.
         * Default: true
         * @param vary_weight
         * @return
         */
        public final Builder varyWeight(final boolean vary_weight) {
            this.vary_weight = vary_weight;
            return this;
        }

        /**
         * Should the different clusters have different deviation.
         * Default: true, the clusters will have different geographical extent.
         * @param vary_deviation
         * @return
         */
        public final Builder varyDeviation(final boolean vary_deviation) {
            this.vary_deviation = vary_deviation;
            return this;
        }

        private static final double DEVIATION_DEFAULT = 10.0;
        private static final double DEVIATION_MULTIPLIER = 100.0;
        private static final double DEFAULT_MAX = 1E6;
        private static final double DISTANCE_CORRECTION_COEF = 1.6;

        /**
         * Build the dataset.
         * @return
         */
        public final Dataset build() {
            Dataset dataset = new Dataset(size);
            Random rand = new Random();

            double[] zero = new double[dimensionality];

            for (int i = 0; i < num_centers; i++) {
                int weight = 1;
                if (vary_weight) {
                    weight += rand.nextInt(10);
                }

                double[] random_center = new double[dimensionality];
                double[] deviation = new double[dimensionality];

                do {
                    for (int j = 0; j < dimensionality; j++) {
                        if (i == 0) {
                            random_center[j] = 0;
                        } else {
                            random_center[j] = (rand.nextDouble() - 0.5)
                                    * DEFAULT_MAX;
                        }

                        deviation[j] = DEVIATION_DEFAULT;
                        if (vary_deviation) {
                            deviation[j] +=
                                    rand.nextDouble() * DEVIATION_MULTIPLIER;
                        }
                    }
                } while (distance(random_center, zero)
                        > DEFAULT_MAX * Math.sqrt(dimensionality) / 2);

                if (i == 0) {
                    dataset.addCenter(
                            new Center(weight, random_center, deviation));
                    continue;
                }

                Center nearest_center =
                        findNearestCenter(dataset, random_center);

                double[] target_distances = new double[dimensionality];
                double[] ratios = new double[dimensionality];

                for (int j = 0; j < dimensionality; j++) {
                    target_distances[j] = (overlap.value + rand.nextDouble())
                            * (deviation[j] + nearest_center.getDeviation(j))
                            / DISTANCE_CORRECTION_COEF;
                    ratios[j] = target_distances[j]
                            / distance(
                                    nearest_center.getCenter(),
                                    random_center);
                }

                double ratio = max(ratios);

                double[] new_center = new double[dimensionality];

                // Project the point at correct distance
                for (int j = 0; j < dimensionality; j++) {
                    new_center[j] =
                            nearest_center.getCenter(j)
                            + ratio
                            * (random_center[j] - nearest_center.getCenter(j));
                }

                dataset.addCenter(new Center(weight, new_center, deviation));
            }

            return dataset;
        }

        private double distance(
                final double[] center1, final  double[] center2) {
            double agg = 0;
            for (int i = 0; i < dimensionality; i++) {
                agg += Math.pow(center1[i] - center2[i], 2);
            }

            return Math.sqrt(agg);
        }

        private Center findNearestCenter(
                final Dataset dataset, final double[] new_center) {
            Center nearest_center = null;
            double nearest_distance = Double.MAX_VALUE;
            for (Center center : dataset.centers) {
                double distance = distance(new_center, center.getCenter());
                if (distance < nearest_distance) {
                    nearest_center = center;
                    nearest_distance = distance;
                }
            }

            return nearest_center;
        }
    }
}

/**
 * Iterator implementation for the Gaussian Mixture Dataset.
 * @author Thibault Debatty
 */
final class GaussianIterator implements Iterator<double[]> {
    private final Random rand;
    private final int dimension;
    private final Center[] lookup_table;
    private int total_weight;
    private final int size;
    private int count;

    GaussianIterator(
            final ArrayList<Center> centers,
            final int size,
            final long random_seed) {

        this.size = size;
        dimension = centers.get(0).getDimension();
        rand = new Random(random_seed);
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

    public double[] next() {
        count++;
        Center center = lookup_table[rand.nextInt(total_weight)];

        double[] point = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            point[i] = center.getCenter(i)
                    + rand.nextGaussian() * center.getDeviation(i);
        }
        return point;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported!");
    }
}
