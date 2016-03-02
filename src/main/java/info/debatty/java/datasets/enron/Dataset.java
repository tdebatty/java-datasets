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
package info.debatty.java.datasets.enron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset extends info.debatty.java.datasets.Dataset<Email> {

    private String directory;

    public Dataset(String directory) {
        this.directory = directory;

    }

    public Iterator<Email> iterator() {
        return new EnronIterator(directory);
    }

    private static class EnronIterator implements Iterator<Email> {

        private static final int BUFFER_SIZE = 10;

        /**
         * List of folders that can be processed to extract pages. Implemented
         * as a stack to get depth first search processing...
         */
        private final Stack<File> directories = new Stack<File>();

        private final LinkedList<Email> available_emails = new LinkedList<Email>();
        private final LinkedList<File> available_files = new LinkedList<File>();
        private final String root;


        public EnronIterator(String directory) {
            root = directory;
            directories.push(new File(directory));

            // Fill the files buffer
            readNextFiles();

            // Fill the pages buffer
            readNextPages();
        }

        public boolean hasNext() {
            return !available_emails.isEmpty();
        }

        public Email next() {
            Email current = available_emails.removeFirst();
            if (available_emails.isEmpty()) {
                readNextPages();
            }

            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }

        private void readNextPages() {

            while (available_emails.size() < BUFFER_SIZE) {
                if (available_files.isEmpty()) {
                    return;
                }

                File next_file = available_files.poll();
                try {
                    available_emails.add(
                            new Email(
                                    readFile(next_file.getPath()),
                                    next_file.getParent().substring(root.length() + 1)));

                } catch (IOException ex) {
                    Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);

                } catch (MessagingException ex) {
                    Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
                    
                } catch (Exception ex) {
                    Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (available_files.isEmpty()) {
                    readNextFiles();
                }
            }
        }

        private String readFile(final String file) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            StringBuilder string_builder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            try {
                while ((line = reader.readLine()) != null) {
                    string_builder.append(line);
                    string_builder.append(ls);
                }

                return string_builder.toString();
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
                    if (file.isDirectory()) {
                        directories.push(file);

                    } else {
                        available_files.add(file);
                    }
                }
            }
        }
    }
}
