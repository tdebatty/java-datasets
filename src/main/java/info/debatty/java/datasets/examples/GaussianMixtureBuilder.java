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

package info.debatty.java.datasets.examples;

import info.debatty.java.datasets.gaussian.Dataset;
import java.awt.RenderingHints;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Thibault Debatty
 */
public class GaussianMixtureBuilder {

    private static final int DIMENSIONALITY = 2;
    private static final int CENTERS = 10;
    private static final int SIZE = 10000;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Dataset dataset = new Dataset.Builder(DIMENSIONALITY, CENTERS)
                .setOverlap(Dataset.Builder.Overlap.MEDIUM)
                .varyDeviation(true)
                .varyWeight(true)
                .setSize(SIZE).build();

        // You can serialize and save your Dataset.
        // This will not save all the points, but only the Dataset oject
        // (including eventual random seeds),
        // which allows to reproduce the dataset using only a small amount of
        // memory
        File file = File.createTempFile("testfile", ".ser");
        dataset.save(new FileOutputStream(file));

        Dataset d2 = (Dataset) Dataset.load(new FileInputStream(file));

        // You can also save to complete data to disk if needed
        // (e.g. for plotting with Gnuplot)
        d2.saveCsv(new BufferedOutputStream(
                new FileOutputStream(File.createTempFile("gaussian", ".dat"))));

        float[][] float_array = new float[SIZE][];
        int i = 0;
        for (double[] vector : d2) {
            //GaussianMixture.println(vector);
            float_array[i] = toFloatArray(vector);
            i++;
        }

        final FastScatterPlot2D demo = new FastScatterPlot2D(
                "Gaussian Mixture Plot", transposeMatrix(float_array));
        demo.pack();
        demo.setVisible(true);
    }

    private static float[] toFloatArray(final double[] arr) {
        if (arr == null) {
            return null;
        }
        int n = arr.length;
        float[] ret = new float[n];
        for (int i = 0; i < n; i++) {
            ret[i] = (float) arr[i];
        }
        return ret;
    }

    private static float[][] transposeMatrix(final float[][] m) {
        float[][] temp = new float[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }

}

/**
 * Draws a scatter plot using the first two dimensions in the data.
 * @author Thibault Debatty
 */
class FastScatterPlot2D extends ApplicationFrame {


    /**
     * Creates a new fast scatter plot demo.
     *
     * @param title  the frame title.
     */
    FastScatterPlot2D(final String title, final float[][] data) {

        super(title);
        final NumberAxis x_axis = new NumberAxis("X");
        x_axis.setAutoRangeIncludesZero(false);
        final NumberAxis y_axis = new NumberAxis("Y");
        y_axis.setAutoRangeIncludesZero(false);

        final FastScatterPlot plot = new FastScatterPlot(
                data, x_axis, y_axis);
        final JFreeChart chart = new JFreeChart(title, plot);

        // force aliasing of the rendered content..
        chart.getRenderingHints().put(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(1024, 768));

        setContentPane(panel);
    }

}