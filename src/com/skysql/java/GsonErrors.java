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
 * Copyright 2013-2014 SkySQL Corporation Ab
 * 
 * Author: Massimo Siani
 * Date: July 2013
 */

package com.skysql.java;

import java.util.List;

/**
 * Handles the errors and warnings from the API.
 * Generally, the classes that handle entities returned by the API
 * extend this class, so there is no need to redefine
 * the errors and warnings fields.
 * 
 * @author Massimo Siani
 *
 */
public class GsonErrors {
	private List<String> errors;
	private List<String> warnings;
	
	/**
	 * Get the errors returned by the API, or <code>null</code>
	 * if the field is not in the JSON.
	 * 
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	/**
	 * Get the warnings returned by the API, or <code>null</code>
	 * if the field is not in the JSON.
	 * 
	 * @return the warnings
	 */
	public List<String> getWarnings() {
		return warnings;
	}
}
