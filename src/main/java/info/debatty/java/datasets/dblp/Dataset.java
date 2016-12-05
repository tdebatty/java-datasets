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

package info.debatty.java.datasets.dblp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Thibault Debatty
 */
public class Dataset extends info.debatty.java.datasets.Dataset<Publication> {

    private final String file;

    public Dataset(String file) {
        this.file = file;
    }

    public Iterator iterator() {
        return new DblpIterator(file);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.file != null ? this.file.hashCode() : 0);
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
}


class DblpIterator implements Iterator<Publication> {
    private static final int BUFFER_SIZE = 20;
    private static final String[] PUBLICATIONS_TYPES = new String[] {
        "article", "inproceedings", "incollection"};

    private static boolean in_publication_types(String localPart) {
        return in_array(PUBLICATIONS_TYPES, localPart);
    }

    private static boolean in_array(String[] haystack, String needle) {
        for (String string : haystack) {
            if (string.equals(needle)) {
                return true;
            }
        }

        return false;
    }

    XMLEventReader xmlEventReader;
    LinkedList<Publication> next_publications = new LinkedList<Publication>();
    private boolean in_publication = false;

    DblpIterator(String file) {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));
        } catch (FileNotFoundException ex) {

        } catch (XMLStreamException ex) {
            Logger.getLogger(DblpIterator.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            readNext();
        } catch (XMLStreamException ex) {
            Logger.getLogger(DblpIterator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readNext() throws XMLStreamException {
        Publication publication = new Publication();
        while(xmlEventReader.hasNext()) {
           XMLEvent xmlEvent = xmlEventReader.nextEvent();
           if (xmlEvent.isStartElement()){
               StartElement startElement = xmlEvent.asStartElement();

               if(in_publication_types(startElement.getName().getLocalPart())){
                   publication = new Publication();
                   publication.type = startElement.getName().getLocalPart();
                   in_publication = true;
                   continue;
               }

               if(in_publication && startElement.getName().getLocalPart().equals("title")){
                   xmlEvent = xmlEventReader.nextEvent();
                   publication.title = xmlEvent.toString();
                   continue;
               }
           }

           if(xmlEvent.isEndElement()){
               if(in_publication_types(xmlEvent.asEndElement().getName().getLocalPart())){
                   if (publication.title != null
                           && !publication.title.equals("")) {
                       next_publications.add(publication);
                   }
                   publication = new Publication();
                   in_publication = false;

                   if (next_publications.size() >= BUFFER_SIZE) {
                       break;
                   }
               }
           }
        }
    }

    public boolean hasNext() {
        return ! next_publications.isEmpty();
    }

    public Publication next() {
        Publication pub = next_publications.pop();
        if (next_publications.isEmpty()) {
            try {
                readNext();
            } catch (XMLStreamException ex) {
                Logger.getLogger(DblpIterator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pub;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}