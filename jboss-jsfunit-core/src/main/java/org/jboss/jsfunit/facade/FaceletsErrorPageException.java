/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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

package org.jboss.jsfunit.facade;

import com.meterware.httpunit.WebResponse;
import java.io.IOException;
import org.xml.sax.SAXException;

/**
 * This exception is thrown if the JSFClientSession encounters a Facelets
 * error page.
 *
 * @author Stan Silvert
 */
public class FaceletsErrorPageException extends RuntimeException
{
   private static final String HEADER = "\r\n----- EXTRACTED FROM FACELETS ERROR PAGE -------\r\n";
   private static final String FOOTER = "\r\n------END FACELETS ERROR PAGE ------------------\r\n";
   
   private static final String STACK_TRACE_BEGIN = "<div id=\"trace\" class=\"grayBox\"><pre><code>";
   private static final String STACK_TRACE_END = "</code></pre></div>";
   
   /**
    * Static utility method to determine if the current page is a
    * Facelets error page.
    * 
    * @param client The client session.
    */
   public static boolean isFaceletsErrorPage(JSFClientSession client)
         throws IOException, SAXException
   {
      WebResponse response = client.getWebResponse();
      return response.getContentType().equals("text/html") &&
             hasElement(response, "traceOff") &&
             hasElement(response, "traceOn") &&
             hasElement(response, "treeOn") &&
             hasElement(response, "treeOff") &&
             response.getText().contains("Generated by Facelets");
   }
   
   private static boolean hasElement(WebResponse response, String id)
         throws SAXException
   {
      return response.getElementWithID(id) != null;
   }
   
   FaceletsErrorPageException(JSFClientSession client) throws SAXException, IOException
   {
      super(extractStackTrace(client));
   }
   
   private static String extractStackTrace(JSFClientSession client)
         throws SAXException, IOException
   {
      String response = client.getWebResponse().getText();
      int beginning = response.indexOf(STACK_TRACE_BEGIN) + STACK_TRACE_BEGIN.length();
      int ending = response.indexOf(STACK_TRACE_END, beginning);
      return HEADER + response.substring(beginning, ending) + FOOTER;
   }
}
