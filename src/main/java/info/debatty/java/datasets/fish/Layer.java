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

import static info.debatty.java.datasets.fish.Image.HEIGHT;
import static info.debatty.java.datasets.fish.Image.WIDTH;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import javax.imageio.ImageIO;


/**
 *
 * @author tibo
 */
public class Layer implements Serializable {

    private final int[] buffer;

    /**
     *
     * @param buffer
     */
    public Layer(final int[] buffer) {
        this.buffer = buffer;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public final int getPixel(final int x, final int y) {
        return buffer[x * Image.WIDTH + y];
    }

    /**
     *
     * @param format
     * @param file
     * @throws IOException
     */
    public final void write(final String format, final File file)
            throws IOException {

        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, WIDTH, HEIGHT, buffer, 0, WIDTH);
        ImageIO.write(image, format, file);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return Arrays.toString(buffer);
    }
}
