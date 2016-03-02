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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageParser;

/**
 *
 * @author Thibault Debatty
 */
public class Email {
    private String raw;
    private final MimeMessageParser parser;
    private final String mailbox;
    private final String user;

    Email(final String raw, final String mailbox) throws MessagingException {
        this.raw = raw;
        String[] strings = mailbox.split(File.separator, 2);
        this.user = strings[0];
        this.mailbox = strings[1];
        Session s = Session.getDefaultInstance(new Properties());
        InputStream is = new ByteArrayInputStream(raw.getBytes());
        MimeMessage message = new MimeMessage(s, is);
        parser = new MimeMessageParser(message);
    }

    public String getUser() {
        return user;
    }

    public String getFrom() throws Exception {
        return parser.getFrom();
    }

    public String getMailbox() {
        return mailbox;
    }

    private static List<String> addressToString(List<Address> addresses) {
        ArrayList<String> strings = new ArrayList<String>();
        for (Address address : addresses) {
            strings.add(address.toString());
        }
        return strings;
    }

    public List<String> getTo() throws Exception {
        return addressToString(parser.getTo());
    }


    public List<String> getCc() throws Exception {
        return addressToString(parser.getCc());
    }

    public String getMessageId() throws MessagingException {
        return parser.getMimeMessage().getMessageID();
    }

    public List<String> getBcc() throws Exception {
        return addressToString(parser.getBcc());
    }

    public String getPlainContent() {
        return parser.getPlainContent();
    }

    public String getSubject() throws Exception {
        return parser.getSubject();
    }

    public String getRaw() {
        return raw;
    }

}
