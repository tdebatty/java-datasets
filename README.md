# java-datasets
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-datasets/badge.svg)](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-datasets) [![Build Status](https://travis-ci.org/tdebatty/java-datasets.svg?branch=master)](https://travis-ci.org/tdebatty/java-datasets) [![API Documentation](http://api123.web-d.be/api123-head.svg)](http://api123.web-d.be/api/java-datasets/head/index.html)

Java library for parsing various datasets:
* [DBLP dataset](./src/main/java/info/debatty/java/datasets/dblp/)
* [Reuters-21578 dataset](./src/main/java/info/debatty/java/datasets/reuters/)
* [Text file dataset](./src/main/java/info/debatty/java/datasets/textfile/)
* [ENRON email dataset](./src/main/java/info/debatty/java/datasets/enron/)
* [Wikipedia web pages](./src/main/java/info/debatty/java/datasets/wikipedia/)
* [Synthetic gaussian mixture](./src/main/java/info/debatty/java/datasets/gaussian/)
* [Scale-invariant feature transform (SIFT) dataset](./src/main/java/info/debatty/java/datasets/sift/)

These parsers are implemented using an iterator, which make them suitable for processing large datasets. You may also use it to process subparts of the dataset, as you can process items "on-the-fly", without reading the entire data.

## Download
Using maven:
```
<dependency>
    <groupId>info.debatty</groupId>
    <artifactId>java-datasets</artifactId>
    <version>RELEASE</version>
</dependency>
```

Or check the [GitHub releases](https://github.com/tdebatty/java-datasets/releases).

## Quickstart

Usually, you simply have to:
- Initialize the Dataset object, using the path to the folder or directory containing the data
- Iterate over the dataset items as long as you want...

```java
import info.debatty.java.datasets.reuters.*;

public class MyClass {

    public static void main(String[] args) {
        // We will use reuters news dataset
        Dataset reuters_dataset = new Dataset("/path/to/reuters/folder");

		// Iterate over news
        for (News news : reuters_dataset) {
            System.out.println(news.title);
        }
    }
}
```

## Random data

One of the datasets allow to easily produce random data according to a gaussian mixture:

```java
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;

public class GaussianMixtureExample {}

    private static final int DIMENSIONALITY = 2;
    private static final int CENTERS = 10;
    private static final int SIZE = 10000;


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
        File file = File.createTempFile("testfile", "ser");
        dataset.save(new FileOutputStream(file));

        // Read the dataset from memory
        Dataset d2 = (Dataset) Dataset.load(new FileInputStream(file));

        // Get all the data at once (can be very large!!)
        double[] data = d2.getAll();
    }

}
```

For the other datasets, check [the examples](./src/main/java/info/debatty/java/datasets/examples), or [the documentation](http://api123.web-d.be/api/java-datasets/head/index.html).