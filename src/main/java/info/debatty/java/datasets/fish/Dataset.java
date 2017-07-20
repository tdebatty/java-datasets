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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibo
 */
public class Dataset
    extends info.debatty.java.datasets.Dataset<Image> {

    /**
     *
     * @param images
     * @param layer
     * @return
     */
    public static List<TimeSerie> extractTimeSeries(
            final List<Image> images,
            final int layer) {

        List<TimeSerie> series = new LinkedList<TimeSerie>();
        for (int x = 0; x < Image.WIDTH; x++) {
            for (int y = 0; y < Image.HEIGHT; y++) {
                TimeSerie serie = new TimeSerie(x, y);
                serie.values = new double[images.size()];
                for (int i = 0; i < images.size(); i++) {
                    serie.values[i] = images.get(i).get(layer).getPixel(x, y);
                }
                series.add(serie);
            }
        }

        return series;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    public Iterator<Image> iterator() {
        return new FishIterator();
    }

    /**
     *
     */
    private static class FishIterator implements Iterator<Image> {

        private final LinkedList<File> fishes = new LinkedList<File>();

        FishIterator() {
            File fish_folder;
            try {
                fish_folder =
                        new File(getClass().getResource("/fish/").toURI());
            } catch (URISyntaxException ex) {
                return;
            }

            // Sort !!
            for (File file : fish_folder.listFiles()) {
                if (file.getName().toLowerCase().endsWith(".bin")) {
                    fishes.add(file);
                }
            }
        }

        public boolean hasNext() {
            return fishes.size() > 0;
        }

        public Image next() {
            try {
                return Image.load(fishes.pop().toPath());
            } catch (IOException ex) {
                Logger.getLogger(
                        Dataset.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

        /**
         * Not supported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
