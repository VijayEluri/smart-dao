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
package com.smartitengineering.exim;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A configuration API for all association in an exim-able object. Since in Java
 * all types are of type {@link Object} we will considering every member (a
 * field or a property) an association and thus store a configuration for it.
 * @author imyousuf
 * @since 0.4
 */
public interface AssociationConfig {

		/**
		 * The accessor name for the property, if field is being used to access then
		 * it will be the name of the field else it will be the name of the method
		 * being used to read it.
		 * @return Accessor string, it will be non-blank
		 */
		public String getAccessorName();

		/**
		 * Defines whether the associate is to be exported as URI or not.
		 * @return True if its expected to export as URI else false
		 */
		public boolean isItToBeExportedAsUri();

		/**
		 * Defines whether the association is to be ignored for when the domain is
		 * exported or imported.
		 * @return True if it is to be ignored for Ex-Im.
		 */
		public boolean isTransient();

		/**
		 * Defines whether the object should be imported eagerly or on demand. It
		 * will only be supported if the resource is exported using properties. If
		 * any association config has it set then it will use CGLIB for proxying the
		 * resources.
		 * @return True if association is to be fetched eagerly.
		 * @see AssociationConfig#isItToBeExportedAsUri()
		 * @see AssociationConfig#isTransient()
		 */
		public boolean isEagerSet();

		/**
		 * Defines the name representing the association in the exported doc.
		 * @return Name to be used in export/import; if null then field/property
		 *          name will be used instead.
		 */
		public String getName();

		/**
		 * Defines whether the association has its own string value generator
		 * implementation, (if its not exported as URI) if it does have a provider
		 * then it will be used else toString will be used.
		 * @return True if it has a string provider implemented.
		 * @see AssociationConfig#isItToBeExportedAsUri()
		 */
		public boolean isStringProviderImplemented();

		/**
		 * Defines the type of association this configuration represents
		 * @return Type of the associaiton
		 */
		public AssociationType getAssociationType();

		/**
		 * It defimes types that impacts how the data is used in order to be
		 * exported.
		 */
		public enum AssociationType {

				/**
				 * Represents anything thats not any form of collection
				 */
				TYPE_OBJECT("array", Object.class),
				/**
				 * Represents List
				 */
				TYPE_LIST("list", List.class),
				/**
				 * Represents Set
				 */
				TYPE_SET("set", Set.class),
				/**
				 * Represents Collection
				 */
				TYPE_COLLECTION("collection", Collection.class),
				/**
				 * Represents Array
				 */
				TYPE_ARRAY("object", Object.class),
				/**
				 * Represents Map
				 */
				TYPE_MAP("map", Map.class),
				/**
				 * Represents type 'double'
				 */
				TYPE_DOUBLE("double", double.class, Double.class),
				/**
				 * Represents type 'float'
				 */
				TYPE_FLOAT("float", float.class, Float.class),
				/**
				 * Represents type 'long'
				 */
				TYPE_LONG("long", long.class, Long.class),
				/**
				 * Represents type 'int'
				 */
				TYPE_INTEGER("integer", int.class, Integer.class),
				/**
				 * Represents type 'String'
				 */
				TYPE_STRING("string", String.class),
				/**
				 * Represents type 'boolean'
				 */
				TYPE_BOOLEAN("boolean", boolean.class, Boolean.class),
				/**
				 * Represents type 'java.util.Date'
				 */
				TYPE_DATE("date", Date.class),;
				private Class[] rootClasses;
				private String simpleName;

				AssociationType(String simpleName,
												Class... rootClass) {
						if (rootClass == null || rootClass.length <= 0) {
								throw new IllegalArgumentException(
												"There should be at least one type!");
						}
						this.rootClasses = rootClass;
						this.simpleName = simpleName;
				}

				/**
				 * The root {@link Class} of the type its representing
				 * @return The class type
				 */
				public Class[] getRootClasses() {
						return rootClasses;
				}

				public String getSimpleName() {
						return simpleName;
				}

				public boolean isAssignableFrom(Class clazz) {
						if (clazz == null) {
								return false;
						}
						for (Class myClazz : getRootClasses()) {
								if (myClazz.isAssignableFrom(clazz)) {
										return true;
								}
						}
						return false;
				}

				/**
				 * Given any {@link Class} it will determine the {@link AssociationType}
				 * for that class.
				 * @param clazz The class whose type to determine
				 * @return Type of the clazz
				 * @throws NullPointerException If clazz is null
				 */
				public static AssociationType getAssociationType(Class clazz)
								throws NullPointerException {
						if (clazz.isArray()) {
								return TYPE_ARRAY;
						}
						if (TYPE_MAP.isAssignableFrom(clazz)) {
								return TYPE_MAP;
						}
						if (TYPE_LIST.isAssignableFrom(clazz)) {
								return TYPE_LIST;
						}
						if (TYPE_SET.isAssignableFrom(clazz)) {
								return TYPE_SET;
						}
						if (TYPE_COLLECTION.isAssignableFrom(clazz)) {
								return TYPE_COLLECTION;
						}
						if (TYPE_DATE.isAssignableFrom(clazz)) {
								return TYPE_DATE;
						}
						if (TYPE_BOOLEAN.isAssignableFrom(clazz)) {
								return TYPE_BOOLEAN;
						}
						if (TYPE_STRING.isAssignableFrom(clazz)) {
								return TYPE_STRING;
						}
						if (TYPE_INTEGER.isAssignableFrom(clazz)) {
								return TYPE_INTEGER;
						}
						if (TYPE_LONG.isAssignableFrom(clazz)) {
								return TYPE_LONG;
						}
						if (TYPE_FLOAT.isAssignableFrom(clazz)) {
								return TYPE_FLOAT;
						}
						if (TYPE_DOUBLE.isAssignableFrom(clazz)) {
								return TYPE_DOUBLE;
						}
						return TYPE_OBJECT;
				}
		}
}
