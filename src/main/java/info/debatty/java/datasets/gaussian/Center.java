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

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Thibault Debatty
 */
public class Center implements Serializable {

    private final int weight;
    private final double[] center;
    private final double[] deviation;

    /**
     * Create a new center.
     * @param weight the weight of this center
     * @param center the center (must be d dimensions)
     * @param deviation the deviation (must be d dimensions)
     */
    public Center(
            final int weight, final double[] center, final double[] deviation) {
        this.weight = weight;
        this.center = center;
        this.deviation = deviation;
    }

    /**
     * Get the weight of this center.
     * @return the weight
     */
    public final int getWeight() {
        return weight;
    }

    /**
     * Get the value of center for dimension i.
     * @param i dimension
     * @return value of center for dimension i
     */
    public final double getCenter(final int i) {
        return center[i];
    }

    /**
     * Get the coordinates of the center.
     * @return
     */
    public final double[] getCenter() {
        return center;
    }

    /**
     * Get the deviation of this center for dimension i.
     * @param i dimension
     * @return value of deviation for dimension i
     */
    public final double getDeviation(final int i) {
        return deviation[i];
    }

    /**
     * Return the dimensionality of this center.
     * @return dimension
     */
    public final int getDimension() {
        return center.length;
    }

    /**
     * Get the deviation for each dimension.
     * @return
     */
    public final double[] getDeviation() {
        return deviation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.weight;
        hash = 73 * hash + Arrays.hashCode(this.center);
        hash = 73 * hash + Arrays.hashCode(this.deviation);
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
        final Center other = (Center) obj;
        if (this.weight != other.weight) {
            return false;
        }
        if (!Arrays.equals(this.center, other.center)) {
            return false;
        }
        if (!Arrays.equals(this.deviation, other.deviation)) {
            return false;
        }
        return true;
    }

    

}
