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

package info.debatty.java.datasets.sift;

import java.util.List;

/**
 *
 * @author Thibault Debatty
 */
public class SimpleSiftSimilarity {

    /**
     *
     */
    public static final int N_FEATURES = 40;

    /**
     *
     * @param image1
     * @param image2
     * @return
     */
    public final double similarity(
            final  SiftDescriptor image1, final  SiftDescriptor image2) {
        SiftMatcher matcher = new SiftMatcher(image1);
        List<SiftMatch> sift_matches = matcher.matchDescriptors(image2);

        double agg = 0.0;
        for (int i = 0; i < N_FEATURES; i++) {
            if (i >= sift_matches.size()) {
                break;
            }
            SiftMatch match = sift_matches.get(i);
            agg += match.getDistance();
        }

        return 1.0 / (1.0 + agg / N_FEATURES);
    }
}
