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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dataset dataset = new Dataset.Builder(2, 4, Dataset.Builder.Overlap.NONE)
                .setSize(500).build();

        float[][] float_array = new float[500][];
        int i = 0;
        for (Double[] vector : dataset) {
            GaussianMixture.println(vector);
            float_array[i] = toFloatArray(vector);
            i++;
        }

        final FastScatterPlotDemo demo = new FastScatterPlotDemo(
                "Gaussian Mixture Plot", transposeMatrix(float_array));
        demo.pack();
        demo.setVisible(true);
    }

    private static float[] toFloatArray(Double[] arr) {
        if (arr == null) {
            return null;
        }
        int n = arr.length;
        float[] ret = new float[n];
        for (int i = 0; i < n; i++) {
            ret[i] = arr[i].floatValue();
        }
        return ret;
    }

    public static float[][] transposeMatrix(float [][] m){
        float[][] temp = new float[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

}


class FastScatterPlotDemo extends ApplicationFrame {


    /**
     * Creates a new fast scatter plot demo.
     *
     * @param title  the frame title.
     */
    FastScatterPlotDemo(final String title, float[][] data) {

        super(title);
        final NumberAxis x_axis = new NumberAxis("X");
        //x_axis.setAutoRangeIncludesZero(true);
        final NumberAxis y_axis = new NumberAxis("Y");
        //y_axis.setAutoRangeIncludesZero(true);

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