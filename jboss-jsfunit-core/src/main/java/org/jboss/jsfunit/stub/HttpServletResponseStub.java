/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.jsfunit.stub;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Mock implementation of <code>HttpServletResponse</code>.</p>
 *
 * $Id$
 */

public class HttpServletResponseStub implements HttpServletResponse {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Return a default instance.</p>
     */
    public HttpServletResponseStub() { }


    // ----------------------------------------------------- Mock Object Methods


    /**
     * <p>Retrieve the first value that was set for the specified header,
     * if any.  Otherwise, return <code>null</code>.</p>
     *
     * @param name Header name to look up
     */
    public String getHeader(String name) {
        String match = name + ":";
        Iterator headers = this.headers.iterator();
        while (headers.hasNext()) {
            String header = (String) headers.next();
            if (header.startsWith(match)) {
                return header.substring(match.length() + 1).trim();
            }
        }
        return null;
    }


    /**
     * <p>Return the text message for the HTTP status that was set.</p>
     */
    public String getMessage() {
        return this.message;
    }


    /**
     * <p>Return the HTTP status code that was set.</p>
     */
    public int getStatus() {
        return this.status;
    }


    /**
     * <p>Set the <code>ServletOutputStream</code> to be returned by a call to
     * <code>getOutputStream()</code>.</p>
     *
     * @param stream The <code>ServletOutputStream</code> instance to use
     *
     * @deprecated Let the <code>getOutputStream()</code> method create and
     *  return an instance of <code>ServletOutputStreamStub</code> for you
     */
    public void setOutputStream(ServletOutputStream stream) {
        this.stream = stream;
    }


    /**
     * <p>Set the <code>PrintWriter</code> to be returned by a call to
     * <code>getWriter()</code>.</p>
     *
     * @param writer The <code>PrintWriter</code> instance to use
     *
     * @deprecated Let the <code>getWriter()</code> method create and return
     *  an instance of <code>PrintWriterStub</code> for you
     */
    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }


    // ------------------------------------------------------ Instance Variables


    private String encoding = "ISO-8859-1";
    private String contentType = "text/html";
    private List headers = new ArrayList();
    private String message = null;
    private int status = HttpServletResponse.SC_OK;
    private ServletOutputStream stream = null;
    private PrintWriter writer = null;


    // -------------------------------------------- HttpServletResponse Methods


    /** {@inheritDoc} */
    public void addCookie(Cookie cookie) {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void addDateHeader(String name, long value) {

        headers.add(name + ": " + formatDate(value));

    }


    /** {@inheritDoc} */
    public void addHeader(String name, String value) {

        headers.add(name + ": " + value);

    }


    /** {@inheritDoc} */
    public void addIntHeader(String name, int value) {

        headers.add(name + ": " + value);

    }


    /** {@inheritDoc} */
    public boolean containsHeader(String name) {

        return getHeader(name) != null;

    }


    /** {@inheritDoc} */
    public String encodeRedirectUrl(String url) {

        return encodeRedirectURL(url);

    }


    /** {@inheritDoc} */
    public String encodeRedirectURL(String url) {

        return url;

    }


    /** {@inheritDoc} */
    public String encodeUrl(String url) {

        return encodeURL(url);

    }


    /** {@inheritDoc} */
    public String encodeURL(String url) {

        return url;

    }


    /** {@inheritDoc} */
    public void sendError(int status) {

        this.status = status;

    }


    /** {@inheritDoc} */
    public void sendError(int status, String message) {

        this.status = status;
        this.message = message;

    }


    /** {@inheritDoc} */
    public void sendRedirect(String location) {

        this.status = HttpServletResponse.SC_MOVED_TEMPORARILY;
        this.message = location;

    }


    /** {@inheritDoc} */
    public void setDateHeader(String name, long value) {

        removeHeader(name);
        addDateHeader(name, value);

    }


    /** {@inheritDoc} */
    public void setHeader(String name, String value) {

        removeHeader(name);
        addHeader(name, value);

    }


    /** {@inheritDoc} */
    public void setIntHeader(String name, int value) {

        removeHeader(name);
        addIntHeader(name, value);

    }


    /** {@inheritDoc} */
    public void setStatus(int status) {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void setStatus(int status, String message) {

        throw new UnsupportedOperationException();

    }


    // ------------------------------------------------ ServletResponse Methods


    /** {@inheritDoc} */
    public void flushBuffer() {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public int getBufferSize() {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public String getCharacterEncoding() {

        return this.encoding;

    }


    /** {@inheritDoc} */
    public String getContentType() {

        return this.contentType;

    }


    /** {@inheritDoc} */
    public Locale getLocale() {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public ServletOutputStream getOutputStream() throws IOException {

        if (stream == null) {
            if (writer != null) {
                throw new IllegalStateException("Cannot call getOutputStream() after getWriter() has been called");
            }
            stream = new ServletOutputStreamStub(new ByteArrayOutputStream());
        }
        return stream;

    }


    /** {@inheritDoc} */
    public PrintWriter getWriter() throws IOException {

        if (writer == null) {
            if (stream != null) {
                throw new IllegalStateException("Cannot call getWriter() after getOutputStream() was called");
            }
            writer = new PrintWriterStub(new CharArrayWriter());
        }
        return writer;

    }


    /** {@inheritDoc} */
    public boolean isCommitted() {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void reset() {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void resetBuffer() {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void setBufferSize(int size) {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void setCharacterEncoding(String charset) {

        this.encoding = charset;

    }


    /** {@inheritDoc} */
    public void setContentLength(int length) {

        throw new UnsupportedOperationException();

    }


    /** {@inheritDoc} */
    public void setContentType(String type) {

        contentType = type;

    }


    /** {@inheritDoc} */
    public void setLocale(Locale locale) {

        throw new UnsupportedOperationException();

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>The date formatting helper we will use in <code>httpTimestamp()</code>.
     * Note that usage of this helper must be synchronized.</p>
     */
    private static SimpleDateFormat format =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    static {
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    /**
     * <p>Return a properly formatted String version of the specified
     * date/time, formatted as required by the HTTP specification.</p>
     *
     * @param date Date/time, expressed as milliseconds since the epoch
     */
    private String formatDate(long date) {
        return format.format(new Date(date));
    }


    /**
     * <p>Remove any header that has been set with the specific name.</p>
     *
     * @param name Header name to look up
     */
    private void removeHeader(String name) {
        String match = name + ":";
        Iterator headers = this.headers.iterator();
        while (headers.hasNext()) {
            String header = (String) headers.next();
            if (header.startsWith(match)) {
                headers.remove();
                return;
            }
        }
    }


}