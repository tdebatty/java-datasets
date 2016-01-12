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
package info.debatty.java.datasets.wikipedia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset extends info.debatty.java.datasets.Dataset<Page> {

    private String directory;

    public Dataset(String directory) {
        this.directory = directory;

    }

    public Iterator<Page> iterator() {
        return new WikipediaIterator(directory);
    }

    private static class WikipediaIterator implements Iterator<Page> {

        private static final int BUFFER_SIZE = 10;

        /**
         * List of folders that can be processed to extract pages. Implemented
         * as a stack to get depth first search processing...
         */
        private final Stack<File> directories = new Stack<File>();

        private final LinkedList<Page> available_pages = new LinkedList<Page>();
        private final LinkedList<File> available_files = new LinkedList<File>();

        public WikipediaIterator(String directory) {
            directories.push(new File(directory));
            
            // Fill the files buffer
            readNextFiles();
            
            // Fill the pages buffer
            readNextPages();
        }

        public boolean hasNext() {
            return !available_pages.isEmpty();
        }

        public Page next() {
            Page current = available_pages.removeFirst();
            if (available_pages.isEmpty()) {
                readNextPages();
            }

            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }

        private void readNextPages() {
            
            while(available_pages.size() < BUFFER_SIZE) {
                if (available_files.isEmpty()) {
                    return;
                }
                
                File next_file = available_files.poll();
                try {
                    available_pages.add(new Page(readFile(next_file.getPath())));
                } catch (IOException ex) {
                    Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (available_files.isEmpty()) {
                    readNextFiles();
                }
            }
        }

        private String readFile(String file) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                }

                return stringBuilder.toString();
            } finally {
                reader.close();
            }
        }

        private void readNextFiles() {
            
            while (available_files.isEmpty()) {
                if (directories.empty()) {
                    return;
                }

                File current_folder = directories.pop();

                for (File file : current_folder.listFiles()) {
                    if (file.getName().endsWith(".html")) {
                        available_files.add(file);
                    } else if (file.isDirectory()) {
                        directories.push(file);
                    }
                }
            }
        }
    }
}
