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

package info.debatty.java.datasets.textfile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset extends info.debatty.java.datasets.Dataset<String> {
    private final String file;

    public Dataset(String file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.file != null ? this.file.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dataset other = (Dataset) obj;
        if ((this.file == null) ? (other.file != null) : !this.file.equals(other.file)) {
            return false;
        }
        return true;
    }

    public Iterator<String> iterator() {
        return new DatasetIterator(file);
    }
}

class DatasetIterator implements Iterator<String> {

    private BufferedReader bufferedReader;
    private String next_line;

    DatasetIterator(String file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            next_line = bufferedReader.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatasetIterator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatasetIterator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean hasNext() {
        if (next_line == null) {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(DatasetIterator.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        return true;
    }

    public String next() {
        String n = next_line;
        try {
            next_line = bufferedReader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(DatasetIterator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported!");
    }

}
