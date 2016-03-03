/*
 * Original code: BSD 2-Clause "Simplified" License
 * http://opensource.org/licenses/BSD-2-Clause
 * Copyright 2006 Wilhelm Burger and Mark J. Burge
 * http://www.imagingbook.com
 *
 * Modified by Thibault Debatty
 */
package info.debatty.java.datasets.sift;

class DogOctave extends ScaleOctave {

    // TODO: check correctness of bottom and top levels!!
    DogOctave(ScaleOctave Gp) {
        //super(0,0,0,0);
        super(Gp.p, Gp.Q, Gp.width, Gp.height, Gp.botLevelIndex, Gp.topLevelIndex - 1);
        // create DoG octave
        for (int q = botLevelIndex; q <= topLevelIndex; q++) {
            ScaleLevel Dpq = differenceOfGaussians(Gp.getLevel(q + 1), Gp.getLevel(q));
            this.setLevel(q, Dpq);
        }
    }

    public ScaleLevel differenceOfGaussians(ScaleLevel A, ScaleLevel B) {
        // A: Gaussian at level q+1
        // B: Gaussian at level q
        // C <-- A - B (scale the same as B)
        ScaleLevel C = B.duplicate();
        final float[] pixelsA = (float[]) A.getPixels();
        final float[] pixelsB = (float[]) B.getPixels();
        final float[] pixelsC = (float[]) C.getPixels();
        for (int i = 0; i < pixelsA.length; i++) {
            pixelsC[i] = pixelsA[i] - pixelsB[i];
        }
        C.setAbsoluteScale(B.getAbsoluteScale());
        return C;
    }
}
