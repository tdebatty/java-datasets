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

import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset
        extends info.debatty.java.datasets.Dataset<SiftDescriptor> {

    private final String directory;

    /**
     * Creates a SIFT dataset from a directory containing pictures.
     * @param directory
     */
    public Dataset(final String directory) {
        this.directory = directory;

    }

    /**
     * Allows to iterate over the sift representation of images.
     * @return
     */
    public final Iterator<SiftDescriptor> iterator() {
        return new SiftIterator(directory);
    }

    /**
     * Real implemenation of the sift iterator.
     */
    private static class SiftIterator implements Iterator<SiftDescriptor> {

        private static final int BUFFER_SIZE = 10;

        /**
         * List of folders that can be processed to extract pages. Implemented
         * as a stack to get depth first search processing...
         */
        private final Stack<File> directories = new Stack<File>();

        private final LinkedList<SiftDescriptor> available_descriptors =
                new LinkedList<SiftDescriptor>();
        private final LinkedList<File> available_files = new LinkedList<File>();

        SiftIterator(final String directory) {
            directories.push(new File(directory));

            // Fill the files buffer
            readNextFiles();

            // Fill the pages buffer
            readNextPages();
        }

        public boolean hasNext() {
            return !available_descriptors.isEmpty();
        }

        public SiftDescriptor next() {
            SiftDescriptor current = available_descriptors.removeFirst();
            if (available_descriptors.isEmpty()) {
                readNextPages();
            }

            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }

        private void readNextPages() {

            while (available_descriptors.size() < BUFFER_SIZE) {
                if (available_files.isEmpty()) {
                    return;
                }

                File next_file = available_files.poll();
                ImagePlus image = IJ.openImage(next_file.getPath());
                ImageProcessor processor = image.getProcessor();
                FloatProcessor fp = new FloatProcessor(
                        processor.getFloatArray());
                SiftDetector detector = new SiftDetector(fp);
                available_descriptors.add(
                        new SiftDescriptor(
                                next_file.getPath(),
                                detector.getSiftFeatures()));

                if (available_files.isEmpty()) {
                    readNextFiles();
                }
            }
        }

        private void readNextFiles() {

            while (available_files.isEmpty()) {
                if (directories.empty()) {
                    return;
                }

                File current_folder = directories.pop();

                for (File file : current_folder.listFiles()) {
                    if (
                            file.getName().toLowerCase().endsWith("jpg")
                            || file.getName().toLowerCase().endsWith("png")) {

                        available_files.add(file);

                    } else if (file.isDirectory()) {
                        directories.push(file);
                    }
                }
            }
        }
    }
}
