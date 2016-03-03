/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

import ij.process.FloatProcessor;

/**
 *
 * @author Thibault Debatty
 */
class GaussianScaleSpace extends HierarchicalScaleSpace {

    public GaussianScaleSpace(FloatProcessor fp, double sigma_s, double sigma_0, int P, int Q, int botLevel, int topLevel) {
        super(P, Q, sigma_s, sigma_0, botLevel, topLevel);
        build(fp);
    }

    private final void build(FloatProcessor fp) {
        double scale_b = getAbsoluteScale(0, -1);	// absolute scale of level(0,-1)
        double sigma_b = getRelativeScale(sigma_s, scale_b);

        ScaleLevel Ginit = new ScaleLevel(fp, sigma_s);
        Ginit.filterGaussian(sigma_b);
        Ginit.setAbsoluteScale(scale_b);

        // build Gaussian octaves:
        octaves[0] = new GaussianOctave(0, Q, Ginit, botLevel, topLevel, sigma_0);
        for (int p = 1; p < P; p++) {
            ScaleLevel Gbase = octaves[p - 1].getLevel(Q - 1).decimate();
            octaves[p] = new GaussianOctave(p, Q, Gbase, botLevel, topLevel, sigma_0);
        }
    }

}
