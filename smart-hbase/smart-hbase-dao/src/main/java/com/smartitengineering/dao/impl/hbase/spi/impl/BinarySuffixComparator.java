/*
 * This is a common dao with basic CRUD operations and is not limited to any
 * persistent layer implementation
 *
 * Copyright (C) 2010  Imran M Yousuf (imyousuf@smartitengineering.com)
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
package com.smartitengineering.dao.impl.hbase.spi.impl;

import org.apache.hadoop.hbase.filter.WritableByteArrayComparable;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * A comparator which compares against a specified byte array, but only compares the
 * end part of this byte array. For the rest it is similar to {@link BinaryComparator}.
 * @author imyousuf
 */
public class BinarySuffixComparator extends WritableByteArrayComparable {

  public BinarySuffixComparator() {
  }

  /**
   * Constructor
   * @param value value
   */
  public BinarySuffixComparator(byte[] value) {
    super(value);
  }

  @Override
  public int compareTo(byte[] value) {
    final int length = this.getValue().length;
    if (value.length < length) {
      return -1;
    }
    return Bytes.compareTo(this.getValue(), 0, length, value, value.length - length, length);
  }
}
