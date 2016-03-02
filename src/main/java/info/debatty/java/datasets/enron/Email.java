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
import java.io.Serializable;
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
public class Email implements Serializable {
    private final String raw;
    private final String mailbox;
    private final String user;
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final List<String> bcc;
    private final String message_id;
    private final String subject;
    private final String content;


    Email(final String raw, final String mailbox) throws MessagingException, Exception {
        this.raw = raw;
        String[] strings = mailbox.split(File.separator, 2);
        this.user = strings[0];
        this.mailbox = strings[1];

        Session s = Session.getDefaultInstance(new Properties());
        InputStream is = new ByteArrayInputStream(raw.getBytes());
        MimeMessage message = new MimeMessage(s, is);
        MimeMessageParser parser = new MimeMessageParser(message);

        from = parser.getFrom();
        to = addressToString(parser.getTo());
        cc = addressToString(parser.getCc());
        bcc = addressToString(parser.getBcc());
        subject = parser.getSubject();
        content = parser.getPlainContent();
        message_id = parser.getMimeMessage().getMessageID();


    }

    public String getUser() {
        return user;
    }

    public String getFrom() throws Exception {
        return from;
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
        return to;
    }


    public List<String> getCc() {
        return cc;
    }

    public String getMessageID() {
        return message_id;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }

    public String getRaw() {
        return raw;
    }

    @Override
    public boolean  equals(Object other) {

        Email other_email = (Email) other;
        return other_email.message_id.equals(this.message_id);

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.message_id != null ? this.message_id.hashCode() : 0);
        return hash;
    }

}
