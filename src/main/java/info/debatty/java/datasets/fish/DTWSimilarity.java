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
package info.debatty.java.datasets.fish;

import java.util.LinkedList;
import java.util.List;

/**
 * Compute similarity based on Dynamic Time Warping between two time series.
 *
 * @author tibo
 */
public class DTWSimilarity {

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        double[] n2 = {1.5, 3.9, 4.1, 3.3};
        double[] n1 = {2.1, 2.45, 3.673, 4.32, 2.05, 1.93, 5.67, 6.01};
        DTWSimilarity dtw = new DTWSimilarity();
        System.out.println(dtw.similarity(n1, n2));

        Dataset data = new Dataset();
        LinkedList<Image> sample = data.get(20);
        List<TimeSerie> time_series = Dataset.extractTimeSeries(sample, 0);
        System.out.println(
                dtw.similarity(
                        time_series.get(123).values,
                        time_series.get(321).values));
    }

    /**
     * Compute similarity between two time series.
     *
     * @param serie1
     * @param serie2
     * @return
     */
    public final double similarity(
            final double[] serie1, final double[] serie2) {
        DTWImpl impl = new DTWImpl(serie1, serie2);
        double distance = impl.getDistance();
        return 1.0 / (1.0 + distance);
    }

}

/**
 * This class implements the Dynamic Time Warping algorithm given two sequences.
 * <pre>
 *   X = x1, x2,..., xi,..., xn
 *   Y = y1, y2,..., yj,..., ym
 * </pre>
 *
 * @author Cheol-Woo Jung (cjung@gatech.edu)
 * @version 1.0
 */
class DTWImpl {

    private final double[] seq1;
    private final double[] seq2;
    private int[][] warping_path;

    private final int n;
    private final int m;
    private int k;

    private double warping_distance;

    /**
     * Constructor.
     *
     * @param query
     * @param seq2
     */
    DTWImpl(final double[] seq1, final double[] seq2) {
        this.seq1 = seq1;
        this.seq2 = seq2;

        n = seq1.length;
        m = seq2.length;
        k = 1;

        warping_path = new int[n + m][2]; // max(n, m) <= K < n + m
        warping_distance = 0.0;

        this.compute();
    }

    public final void compute() {
        double accumulated_distance;

        double[][] d = new double[n][m]; // local distances
        double[][] global_distances = new double[n][m]; // global distances

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = distanceBetween(seq1[i], seq2[j]);
            }
        }

        global_distances[0][0] = d[0][0];

        for (int i = 1; i < n; i++) {
            global_distances[i][0] = d[i][0] + global_distances[i - 1][0];
        }

        for (int j = 1; j < m; j++) {
            global_distances[0][j] = d[0][j] + global_distances[0][j - 1];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                accumulated_distance = Math.min(
                        Math.min(
                                global_distances[i - 1][j],
                                global_distances[i - 1][j - 1]),
                        global_distances[i][j - 1]);
                accumulated_distance += d[i][j];
                global_distances[i][j] = accumulated_distance;
            }
        }
        accumulated_distance = global_distances[n - 1][m - 1];

        int i = n - 1;
        int j = m - 1;

        warping_path[k - 1][0] = i;
        warping_path[k - 1][1] = j;

        while ((i + j) != 0) {
            if (i == 0) {
                j -= 1;
            } else if (j == 0) {
                i -= 1;
            } else { // i != 0 && j != 0
                double[] array = {
                    global_distances[i - 1][j],
                    global_distances[i][j - 1],
                    global_distances[i - 1][j - 1]};
                int min_index = this.getIndexOfMinimum(array);

                switch (min_index) {
                    case 0:
                        i -= 1;
                        break;
                    case 1:
                        j -= 1;
                        break;
                    case 2:
                        i -= 1;
                        j -= 1;
                        break;
                    default:
                        break;
                }
            } // end else
            k++;
            warping_path[k - 1][0] = i;
            warping_path[k - 1][1] = j;
        } // end while
        warping_distance = accumulated_distance / k;

        this.reversePath(warping_path);
    }

    /**
     * Changes the order of the warping path (increasing order).
     *
     * @param path the warping path in reverse order
     */
    void reversePath(final int[][] path) {
        int[][] new_path = new int[k][2];
        for (int i = 0; i < k; i++) {
            System.arraycopy(path[k - i - 1], 0, new_path[i], 0, 2);
        }
        warping_path = new_path;
    }

    /**
     * Returns the warping distance.
     *
     * @return
     */
    public double getDistance() {
        return warping_distance;
    }

    /**
     * Computes a distance between two points.
     *
     * @param p1 the point 1
     * @param p2 the point 2
     * @return the distance between two points
     */
    double distanceBetween(final double p1, final double p2) {
        return (p1 - p2) * (p1 - p2);
    }

    /**
     * Finds the index of the minimum element from the given array.
     *
     * @param array the array containing numeric values
     * @return the min value among elements
     */
    int getIndexOfMinimum(final double[] array) {
        int index = 0;
        double val = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < val) {
                val = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Returns a string that displays the warping distance and path.
     */
    @Override
    public String toString() {
        String result = "Warping Distance: " + warping_distance + "\n";
        result += "Warping Path: {";
        for (int i = 0; i < k; i++) {
            result += "(" + warping_path[i][0] + ", " + warping_path[i][1]
                    + ")";
            result += (i == k - 1) ? "}" : ", ";

        }
        return result;
    }
}
