/*
 * The MIT License
 *
 * Copyright 2017 tibo.
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
package info.debatty.java.datasets.tv;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * Represents a TV sequence.
 * Labels : - +1/-1 ( Commercials/Non Commercials)
Feature
Dimension Index in feature File
Shot Length
1
Motion Distribution( Mean and Variance)
2 - 3
Frame Difference Distribution ( Mean and Variance)
4 - 5
Short time energy ( Mean and Variance)
6 – 7
ZCR( Mean and Variance)
8 - 9
Spectral Centroid ( Mean and Variance)
10 - 11
Spectral Roll off ( Mean and Variance)
12 - 13
Spectral Flux ( Mean and Variance)
14 - 15
Fundamental Frequency ( Mean and Variance)
16 - 17
Motion Distribution ( 40 bins)
18 -  58
Frame Difference Distribution ( 32 bins)
59 - 91
Text area distribution (  15 bins Mean  and 15 bins for variance )
92 - 122
Bag of Audio Words ( 4000 bins)
123 -  4123
Edge change Ratio ( Mean and Variance)
4124 - 4125
 * @author tibo
 */
public class Sequence implements Serializable {

    /**
     * Features that are used to compute a summary vector.
     */
    private static final int[] SUMMARY_FIELDS = new int[] {
        1, 2, 4, 6, 8, 10, 12, 14, 16, 4124
    };

    /**
     * Normalization coefficients for the summary vector.
     */
    private static final double[] NORMALISATION = new double[] {
        33871, 21.679216, 67.285736, 0.036905, 0.394551, 4005.922607,
        7835.905762, 8821.179688, 194.318634, 1.0
    };

    /**
     * Return a new summary vector with normalized values (all values lie
     * between 0 and 1).
     * @param summary
     * @return
     */
    public static final double[] normalize(final double[] summary) {
        double[] normalized = new double[NORMALISATION.length];
        for (int i = 0; i < NORMALISATION.length; i++) {
            normalized[i] = summary[i] / NORMALISATION[i];
        }

        return normalized;
    }

    private final boolean commercial;
    private final Map<Integer, Double> values;

    /**
     * Build a sequence by parsing a line from the dataset.
     * @param line
     */
    public Sequence(final String line) {
        String[] pieces = line.trim().split(" +");
        assert pieces.length <= 4125;

        commercial = (pieces[0].equals("1"));
        values = new HashMap<>(200);
        for (int i = 1; i < pieces.length; i++) {
            String[] key_value = pieces[i].split(":");
            Integer key = Integer.valueOf(key_value[0]);
            Double value = Double.valueOf(key_value[1]);

            values.put(key, value);
        }
    }

    /**
     * Is this sequence a commercial?
     * @return
     */
    public final boolean isCommercial() {
        return commercial;
    }

    /**
     * Get a summary containing only 10 values.
     * @return
     */
    public final double[] getSummary() {
        double[] summary = new double[SUMMARY_FIELDS.length];
        for (int i = 0; i < SUMMARY_FIELDS.length; i++) {
            summary[i] = values.get(SUMMARY_FIELDS[i]);
        }
        return summary;
    }
}
