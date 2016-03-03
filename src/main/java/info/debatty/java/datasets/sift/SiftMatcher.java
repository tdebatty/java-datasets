/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

import info.debatty.java.datasets.sift.VectorNorm.NormType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SiftMatcher {

    public static class Parameters {

        /**
         * Specify type of distance norm
         */
        public NormType norm = NormType.L2;
        /**
         * Max. ratio between best and second-best match
         */
        public double rho_max = 0.8;
        /**
         * Set true to sort matches
         */
        public boolean sort = true;
    }

    private final Parameters params;
    private final SiftFeature[] fA;
    private final VectorNorm am;

    // constructor - using default parameters
    public SiftMatcher(List<SiftFeature> sfA) {
        this(sfA, new Parameters());
    }

    // constructor - using specific parameters
    public SiftMatcher(List<SiftFeature> sfA, Parameters params) {
        this.fA = sfA.toArray(new SiftFeature[0]);
        this.params = params;
        am = params.norm.create();
    }

    public List<SiftMatch> matchDescriptors(List<SiftFeature> sfB) {
        SiftFeature[] fB = sfB.toArray(new SiftFeature[0]);
        List<SiftMatch> matches = new ArrayList<SiftMatch>(fA.length);

        for (int i = 0; i < fA.length; i++) {
            SiftFeature si = fA[i];
            int i1 = -1;
            int i2 = -1;
            double d1 = Double.MAX_VALUE;
            double d2 = Double.MAX_VALUE;

            for (int j = 0; j < fB.length; j++) {
                double d = dist(si, fB[j]);
                if (d < d1) {	// new absolute minimum distance
                    i2 = i1;	// old best becomes second-best
                    d2 = d1;
                    i1 = j;
                    d1 = d;
                } else // not a new absolute min., but possible second-best
                if (d < d2) { // new second-best
                    i2 = j;
                    d2 = d;
                }
            }
            if (i2 >= 0 && d2 > 0.001 && d1 / d2 < params.rho_max) {
                SiftFeature s1 = fB[i1];
                SiftMatch m = new SiftMatch(si, s1, d1);
                matches.add(m);
            }
        }
        if (params.sort) {
            Collections.sort(matches);  // sort matches to ascending distance d1
        }
        return matches;
    }

    double dist(SiftFeature d1, SiftFeature d2) {
        //final ArrayMatcher matcher = params.norm.matcher;
        return am.distance(d1.getFeatures(), d2.getFeatures());
    }

}
