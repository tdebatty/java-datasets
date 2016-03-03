/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

public class SiftMatch implements Comparable<SiftMatch> {

    final SiftFeature descriptor1, descriptor2;
    final double distance;

    public SiftMatch(SiftFeature descriptor1, SiftFeature descriptor2, double distance) {
        this.descriptor1 = descriptor1;
        this.descriptor2 = descriptor2;
        this.distance = distance;
    }

    public SiftFeature getDescriptor1() {
        return descriptor1;
    }

    public SiftFeature getDescriptor2() {
        return descriptor2;
    }

    public double getDistance() {
        return distance;
    }

    public int compareTo(SiftMatch match2) {
        if (this.distance < match2.distance) {
            return -1;
        } else if (this.distance > match2.distance) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return String.format("match %.2f", this.distance);
    }

}
