/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

import java.util.Locale;

/**
 *
 * @author Thibault Debatty
 */
class KeyPoint implements Cloneable, Comparable<KeyPoint> {

    public final int p;	// octave index
    public final int q;	// level index

    public final int u;	// lattice x-position
    public final int v;	// lattice y-position
    public float x;		// interpolated lattice x-position
    public float y;		// interpolated lattice y-position

    public float x_real;	// real x-position (in image coordinates)
    public float y_real;	// real y-position (in image coordinates)
    public float scale;		// absolute scale

    public final float magnitude;	// magnitude of DoG response

    public float[] orientation_histogram;	// for debugging only
    public double orientation;	// dominant orientation

//	protected KeyPoint(int p, int q, int u, int v) {
//		this.p = p;
//		this.q = q;
//		this.u = u;
//		this.v = v;
//		this.x = u;
//		this.y = v;
//	}
    protected KeyPoint(int p, int q, int u, int v, float x, float y, float x_real, float y_real, float scale, float magnitude) {
        this.p = p;
        this.q = q;
        this.u = u;
        this.v = v;
        this.x = x;
        this.y = y;
        this.x_real = x_real;
        this.y_real = y_real;
        this.scale = scale;
        this.magnitude = magnitude;
    }

    public String toString() {
        return String.format(Locale.US, "p=%d, q=%d, u=%d, v=%d, scale=%.2f, mag=%.2f", p, q, u, v, scale, magnitude);
    }

    public KeyPoint clone() {
        try {
            return (KeyPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int compareTo(KeyPoint c2) {
        //used for sorting keypoints by magnitude
        if (this.magnitude > c2.magnitude) {
            return -1;
        }
        if (this.magnitude < c2.magnitude) {
            return 1;
        } else {
            return 0;
        }
    }

}
