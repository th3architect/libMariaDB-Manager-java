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
 * Copyright 2012-2014 SkySQL Corporation Ab
 */

package com.skysql.java;

import com.google.gson.Gson;

/**
 * Class to manage JSON strings, from and to Java objects.
 * This class requires the definitions of appropriate classes
 * that reflect the JSON structure.
 * If the Java class extends <code>GsonErrors</code>, the
 * errors and warnings are treated with the help of the
 * <code>Logging</code> class.
 * 
 * @author Massimo Siani
 *
 */
public class GsonManager {

	/**
	 * Gson object.
	 */
	private static Gson			gson = new Gson();

	/**
	 * Generate a JSON from an object.
	 * 
	 * @param obj the object.
	 * @return the JSON string.
	 */
	public static <T> String toJson(T obj) {
		String result = gson.toJson(obj);
		return result;
	}

	/**
	 * Convert a JSON into a Java object. If the Object class is derived
	 * from <code>GsonErrors</code> and the object contains errors, this method
	 * prints the error and/or warning messages as defined in the
	 * <code>Logging.error</code> and <code>Logging.warn</code>. 
	 * 
	 * @param inJson the JSON as a string.
	 * @param objClass the class of the object.
	 * @return the deserialized JSON as a Java object.
	 */
	public static <T> T fromJson(final String inJson, Class<T> objClass) {
		try {
			boolean errorFound = false;
			// if API returned errors or warnings
			if (GsonErrors.class.isAssignableFrom(objClass)) {
				GsonErrors gsonErrors = gson.fromJson(inJson, GsonErrors.class);
				if (gsonErrors != null) {
					// print errors
					if (gsonErrors.getErrors() != null) {
						errorFound = true;
						Logging.error("The API returned the following error(s): ");
						for (String error : gsonErrors.getErrors()) {
							Logging.error(error);
						}
					}
					// print warnings
					if (gsonErrors.getWarnings() != null) {
						errorFound = true;
						Logging.warn("The API returned the following warning(s): ");
						for (String warning : gsonErrors.getWarnings()) {
							Logging.warn(warning);
						}
					}
					if (errorFound) return null;
				}
			}
			T resultObj = gson.fromJson(inJson, objClass);
			return resultObj;
		} catch (Exception e) {
			return null;
		}
	}

}
