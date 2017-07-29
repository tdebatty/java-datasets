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
package info.debatty.java.datasets.tv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import junit.framework.TestCase;

/**
 *
 * @author tibo
 */
public class SequenceTest extends TestCase {

    /**
     * Test of getSummary method, of class Sequence.
     */
    public void testSequence() {
        System.out.println("constructor");

        InputStream stream = SequenceTest.class
                .getResourceAsStream("/tv/BBC-10.txt");

        List<String> lines = new BufferedReader(new InputStreamReader(stream,
                StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        assertEquals(10, lines.size());

        LinkedList<Sequence> sequences = new LinkedList<>();
        lines.forEach((line) -> {
            sequences.add(new Sequence(line));
        });

        assertEquals(10, sequences.size());

        for (Sequence seq : sequences) {
            double[] normalized_summary = Sequence.normalize(seq.getSummary());
            Arrays.stream(normalized_summary).forEach((value) -> {
                assertTrue(value >= 0.0);
                assertTrue(value <= 1.0);
            });
        }
    }

}
