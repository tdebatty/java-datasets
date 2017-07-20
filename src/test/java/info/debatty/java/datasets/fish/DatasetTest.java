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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author tibo
 */
public class DatasetTest extends TestCase {

    public DatasetTest(String testName) {
        super(testName);
    }

    /**
     * Test of extractTimeSeries method, of class Dataset.
     */
    public void testExtractTimeSeries() {
        System.out.println("extractTimeSeries");
        Dataset dataset = new Dataset();
        LinkedList<Image> images = dataset.getAll();
        List<TimeSerie> time_series = Dataset.extractTimeSeries(images, 0);
        assertEquals("Not all time series...", 6612, time_series.size());
        assertEquals(
                "Time serie is not of expected length",
                240,
                time_series.get(0).values.length);

        System.out.println(time_series.get(0));
    }

    /**
     * Test of iterator method, of class Dataset.
     */
    public final void testIterator() {
        System.out.println("iterator");
        Dataset dataset = new Dataset();
        LinkedList<Image> images = dataset.getAll();
        assertEquals("Not all images were loaded!", 240, images.size());
    }

}
