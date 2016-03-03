/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

/**
 *
 * @author Thibault Debatty
 */
public class DogScaleSpace extends HierarchicalScaleSpace {

    public DogScaleSpace(GaussianScaleSpace G) {
        super(G.P, G.Q, G.sigma_s, G.sigma_0, G.botLevel, G.topLevel - 1);  //botLevel = -1, topLevel = K+1
        build(G);
    }

    private final void build(GaussianScaleSpace G) {
        // build DoG octaves:
        for (int p = 0; p < P; p++) {
            ScaleOctave Gp = G.getOctave(p);
            octaves[p] = new DogOctave(Gp);
        }
    }
}
