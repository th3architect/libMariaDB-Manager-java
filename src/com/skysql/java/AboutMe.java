/*
 * This file is distributed as part of the MariaDB Manager. It is free
 * software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Copyright 2014 SkySQL Corporation Ab
 */

package com.skysql.java;

/**
 * Provides information about the library, such as the
 * version number, which provides the progressive build number,
 * and the release number, which identifies the API code
 * the library is compatible with.
 * 
 * @author Massimo Siani
 *
 */
public class AboutMe {
	/**
	 * The name of the project.
	 */
	public final static String		NAME = "libMariaDB-Manager-java";
	/**
	 * The internal version number.
	 */
	public final static String		VERSION = "0.1-11";
	/**
	 * The MariaDB-Manager release number this library is fully compatible with.
	 */
	public final static String		RELEASE = "1.0.2";
	/**
	 * The date the last change has been applied.
	 */
	public final static String		DATE = "Tue, 15 Apr 2014 11:17:57 -0400";
	
}
