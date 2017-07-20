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

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 *
 * @author tibo
 */
public class Image implements Serializable {

    static final int HEIGHT = 87;
    static final int WIDTH = 76;
    private static final int LAYERS = 2;

    public static final Image load(final Path path) throws IOException {
        byte[] data = Files.readAllBytes(path);

        // Convert to an array of int values
        int[] values = new int[data.length/2];
        for (int i = 0; i < data.length/2; i++) {
            values[i] = (int) toFloat(getInt(data, i * 2));
        }

        return new Image(values);
    }

    public static int getInt(byte[] arr, int off) {
        return arr[off + 1] << 8 & 0xFF00 | arr[off] & 0xFF;
    }

    // ignores the higher 16 bits
    public static float toFloat(int hbits) {
        int mant = hbits & 0x03ff;            // 10 bits mantissa
        int exp = hbits & 0x7c00;            // 5 bits exponent
        if (exp == 0x7c00) // NaN/Inf
        {
            exp = 0x3fc00;                    // -> NaN/Inf
        } else if (exp != 0) // normalized value
        {
            exp += 0x1c000;                   // exp - 15 + 127
            if (mant == 0 && exp > 0x1c400) // smooth transition
            {
                return Float.intBitsToFloat((hbits & 0x8000) << 16
                        | exp << 13 | 0x3ff);
            }
        } else if (mant != 0) // && exp==0 -> subnormal
        {
            exp = 0x1c400;                    // make it normal
            do {
                mant <<= 1;                   // mantissa * 2
                exp -= 0x400;                 // decrease exp by 1
            } while ((mant & 0x400) == 0); // while not normal
            mant &= 0x3ff;                    // discard subnormal bit
        }                                     // else +/-0 -> +/-0
        return Float.intBitsToFloat( // combine all parts
                (hbits & 0x8000) << 16 // sign  << ( 31 - 15 )
                | (exp | mant) << 13);         // value << ( 23 - 10 )
    }

    private final LinkedList<Layer> layers = new LinkedList<Layer>();

    public Image(int[] values) throws IOException {
        // Split between the two layers as
        // Announced interleaving (in config.json) is 76 x 87 x 2
        // Real interleaving is 2 x 87 x 76
        int offset = 0;
        for (int layer = 0; layer < LAYERS; layer++) {
            int[] buffer = new int[WIDTH * HEIGHT];
            for (int pixel = 0; pixel < WIDTH * HEIGHT; pixel++) {

                buffer[pixel] = values[offset + pixel];
            }
            layers.add(new Layer(buffer));
            offset += WIDTH * HEIGHT;
        }

    }

    /**
     *
     * @param index
     * @return
     */
    public final Layer get(final int index) {
        return layers.get(index);
    }
}
