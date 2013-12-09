/*
 * This file is distributed as part of the SkySQL Cloud Data Suite.  It is free
 * software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation,
 * version 2.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright 2012-2014 SkySQL Ab
 */

package com.skysql.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Reads the configuration file as a JSON object.
 * 
 * @author Massimo Siani
 *
 */
public class GsonConfig {
	private ApiConfig			api;
	
	private ApiConfig getApiConfig() {
		return api;
	}
	
	/**
	 * Main object.
	 * @author Massimo Siani
	 *
	 */
	public static class ApiConfig {
		private String			uri;
		private List<Keys>		keys;
		
		public String getUri() {
			return uri;
		}
		
		public List<Keys> getKeys() {
			return keys;
		}
	}
	
	/**
	 * Key object.
	 * @author Massimo Siani
	 *
	 */
	public static class Keys {
		private String id;
		private String code;
		
		public int getId() {
			return Integer.parseInt(id);
		}
		
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Returns the API URI from the configuration file.
	 * @return		the API URI
	 */
	public String getUri() {
		String uri = getApiConfig().getUri();
		return uri;
	}
	
	/**
	 * Searches the given API ID in the list of API ID/key pairs, and returns
	 * the corresponding key. If the id does not exist, returns
	 * null.
	 * @param id		the API ID
	 * @return			the corresponding API key, or null if the ID does not exist
	 */
	public String getKey(int id) {
		String result = null;
		Iterator<Keys> keysIt = api.getKeys().iterator();
		while (keysIt.hasNext()) {
			Keys key = keysIt.next();
			if (key.getId() == id) {
				result = key.getCode();
				break;
			}
		}
		return result;
	}
	
	/**
	 * Returns the list of defined API IDs.
	 * @return		the list of defined API IDs
	 */
	public List<Integer> getIds() {
		List<Integer> result = new ArrayList<Integer>();
		Iterator<Keys> keysIt = api.getKeys().iterator();
		while (keysIt.hasNext()) {
			Keys key = keysIt.next();
			result.add(key.getId());
		}
		return result;
	}
	
	/**
	 * Returns all the API ID/key pairs.
	 * @return		the API ID/key pairs
	 */
	public HashMap<Integer, String> getIdKeyPairs() {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		Iterator<Keys> keysIt = api.getKeys().iterator();
		while (keysIt.hasNext()) {
			Keys key = keysIt.next();
			result.put((Integer)key.getId(), key.getCode());
		}
		return result;
	}

}
