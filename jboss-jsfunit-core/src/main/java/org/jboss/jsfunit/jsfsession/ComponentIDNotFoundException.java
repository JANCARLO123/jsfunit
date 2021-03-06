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

package org.jboss.jsfunit.jsfsession;

/**
 * Exception indicates that a component ID could not be found on the current
 * page (client side) or component tree (server side).
 *
 * @author Stan Silvert
 * @since 1.0
 */
public class ComponentIDNotFoundException extends RuntimeException
{
   
   /**
    * Create a new instance of ComponentIDNotFoundException.
    * 
    * @param componentID The JSF component id (or a suffix of the client ID)
    */
   public ComponentIDNotFoundException(String componentID)
   {
      super("No component ID was found for " + componentID);
   }
}
