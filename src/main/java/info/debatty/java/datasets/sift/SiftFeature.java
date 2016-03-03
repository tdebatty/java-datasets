/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

import java.io.Serializable;

/**
 *
 * @author Thibault Debatty
 */

/**
 * SIFT descriptor. Added magnitude field and Comparable interface for easy
 * sorting.
 *
 * @author W. Burger
 *
 */
public class SiftFeature implements Comparable<SiftFeature>, Serializable {

    private final double x;
    private final double y;
    private final double scale;
    private final double magnitude;
    private final double orientation;
    private final int[] features;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getScale() {
        return scale;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getOrientation() {
        return orientation;
    }

    public int[] getFeatures() {
        return features;
    }

    public SiftFeature(double x, double y, double scale, double magnitude,
            double orientation, int[] features) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.magnitude = magnitude;
        this.orientation = orientation;
        this.features = features;
    }

	// -----------------------------
    public double getDistanceL1(SiftFeature other) {
        int[] f1 = this.features;
        int[] f2 = other.features;
        int sum = 0;
        for (int i = 0; i < f1.length; i++) {
            sum = sum + Math.abs(f1[i] - f2[i]);
        }
        return sum;
    }

    public double getDistanceL2(SiftFeature other) {
        int[] f1 = this.features;
        int[] f2 = other.features;
        int sum = 0;
        for (int i = 0; i < f1.length; i++) {
            int d = f1[i] - f2[i];
            sum = sum + d * d;
        }
        return Math.sqrt(sum);
    }

    public double getDistanceLinf(SiftFeature other) {
        int[] f1 = this.features;
        int[] f2 = other.features;
        int dmax = 0;
        for (int i = 0; i < f1.length; i++) {
            int d = Math.abs(f1[i] - f2[i]);
            dmax = Math.max(dmax, d);
        }
        return dmax;
    }


    @Override
    public String toString() {
        return String.format(
                "x=%.1f y=%.1f s=%.2f mag=%.4f angle=%.2f",
                x, y, scale, magnitude, orientation);
    }

    //used for sorting SIFT descriptors by magnitude
    public int compareTo(SiftFeature d2) {
        if (this.magnitude > d2.magnitude) {
            return -1;
        }
        if (this.magnitude < d2.magnitude) {
            return 1;

        } else {
            return 0;
        }
    }

    @Override
    public final boolean equals(final Object other) {
        SiftFeature other_feature = (SiftFeature) other;
        return this.x == other_feature.x
                && this.y == other_feature.y
                && this.scale == other_feature.scale
                && this.orientation == other_feature.orientation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.x)
                ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.y)
                ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.scale)
                ^ (Double.doubleToLongBits(this.scale) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.orientation)
                ^ (Double.doubleToLongBits(this.orientation) >>> 32));
        return hash;
    }
}
