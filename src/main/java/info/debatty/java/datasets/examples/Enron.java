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

import info.debatty.java.datasets.enron.Dataset;
import info.debatty.java.datasets.enron.Email;


/**
 *
 * @author Thibault Debatty
 */
public class Enron {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
         Dataset enron_dataset = new Dataset(
                DBLP.class.getClassLoader().getResource("mini-enron")
                .getFile());

        for (Email email : enron_dataset) {
            //System.out.println(email.getRaw());

            System.out.println(email.getUser());

            // This might be "inbox", "sent", "archive/holidays" etc.
            System.out.println(email.getMailbox());

            System.out.println(email.getSubject());
            System.out.println(email.getFrom());
            for (String address : email.getTo()) {
                System.out.println(address);
            }

            System.out.println("---");
        }
    }
}
