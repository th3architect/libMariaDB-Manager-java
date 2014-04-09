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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Encodes the necessary configuration for a client to connect to the API.
 * The api object contains the API URI, trimmed of the / characters,
 * and a list of the API ID/Key pairs.
 * 
 * @author Massimo Siani
 *
 */
public class GsonConfig {
	private ApiConfig			api;
	
	/**
	 * Returns the "root" api object.
	 * 
	 * @return		the api object
	 */
	private ApiConfig getApiConfig() {
		return api;
	}
	
	/**
	 * This is the root object, which contains the
	 * uri and the list of keys objects. In general, there is no
	 * need to retrieve this nested class, the objects here can
	 * be retrieved by the available methods.
	 * 
	 * @author Massimo Siani
	 *
	 */
	public static class ApiConfig {
		private String			uri;
		private List<Keys>		keys;
		
		/**
		 * Returns the API URI.
		 * 
		 * @return		the API URI
		 */
		public String getUri() {
			return uri;
		}
		
		/**
		 * Returns the list of API ID/Key pairs.
		 * 
		 * @return		the list of key objects
		 */
		public List<Keys> getKeys() {
			return keys;
		}
	}
	
	/**
	 * Encodes a single key object.
	 * 
	 * @author Massimo Siani
	 *
	 */
	public static class Keys {
		private String id;
		private String code;
		
		/**
		 * Returns the ID of the API ID/key pair.
		 * 
		 * @return		the ID of the pair
		 */
		public int getId() {
			return Integer.parseInt(id);
		}
		
		/**
		 * Returns the key of the API ID/key pair.
		 * 
		 * @return		the key of the pair
		 */
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Returns the API URI.
	 * 
	 * @return		the API URI
	 */
	public String getUri() {
		String uri = getApiConfig().getUri();
		return uri;
	}
	
	/**
	 * Searches the given API ID in the list of API ID/key pairs, and returns
	 * the corresponding key. If the id does not exist, returns <code>null</code>.
	 * 
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
	 * Returns the list of defined API ID's, or an empty list.
	 * 
	 * @return		the list of the defined API IDs
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
	 * Returns all the retrieved API ID/key pairs.
	 * 
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
