/*
 * This is a common dao with basic CRUD operations and is not limited to any 
 * persistent layer implementation
 * 
 * Copyright (C) 2008  Imran M Yousuf (imyousuf@smartitengineering.com)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.smartitengineering.version.api.spi;

import com.smartitengineering.version.api.Content;
import com.smartitengineering.version.api.Resource;
import java.io.InputStream;

/**
 * MutableResource represents an object intended to versioned.
 * @author imyousuf
 */
public interface MutableResource
    extends Resource {

    /**
     * Sets the content of the resource. Setting the content directly will
     * reset the contentAsStream, contentLoaded and contentSize.
     * @param content Can not be null
     * @throws IllegalArgumentException If and only if content is NULL
     */
    public void setContent(Content content) throws IllegalArgumentException;
    
    /**
     * Return the ID of the resource.
     * @param id Identifier for the resource
     */
    public void setId(String id);
    
    /**
     * Set whether the resource's current state represents whether the its been
     * deleted or not.
     * @param deleted True if resource has been deleted.
     */
    public void setDeleted(boolean deleted);
    
    /**
     * Set the mime-type of the resource.
     * @param mimeType MIME Type of the resource
     */
    public void setMimeType(String mimeType);
}
