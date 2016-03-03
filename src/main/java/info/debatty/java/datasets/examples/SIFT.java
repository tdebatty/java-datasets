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

import info.debatty.java.datasets.sift.Dataset;
import info.debatty.java.datasets.sift.SiftDescriptor;
import info.debatty.java.datasets.sift.SimpleSiftSimilarity;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Thibault Debatty
 */
public class SIFT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dataset dataset = new Dataset(
                SIFT.class.getClassLoader().getResource("mini-caltech101")
                .getFile());

        LinkedList<SiftDescriptor> images = new LinkedList<SiftDescriptor>();
        for (SiftDescriptor descriptor : dataset) {
            images.add(descriptor);
        }

        Random rand = new Random();
        SiftDescriptor query = images.remove(rand.nextInt(images.size()));
        System.out.println("Query: " + query.getFile());

        SimpleSiftSimilarity sift_similarity = new SimpleSiftSimilarity();
        for (SiftDescriptor candidate : images) {
            System.out.println(candidate.getFile());

            System.out.printf("Similarity: %f\n",
                            sift_similarity.similarity(query, candidate));
        }
    }
}

