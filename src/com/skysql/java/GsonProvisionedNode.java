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
 * Handles the response from the provisionednode API.
 * 
 * @author Massimo Siani
 *
 */
public class GsonProvisionedNode extends GsonErrors {
	private final static int ERROR_INT_RETURN = -1;
	private List<ProvisionedNodes> provisionednodes;
	
	/**
	 * The API returns a list of objects representing the provisioned
	 * nodes. The list is retrieved by this method.
	 * 
	 * @return the list of provisioned nodes objects
	 */
	public List<ProvisionedNodes> getProvisionedNodes() {
		return provisionednodes;
	}

	/**
	 * The object that encodes one provisioned node entity.
	 * 
	 * @author Massimo Siani
	 *
	 */
	public static class ProvisionedNodes {
		private String systemid;
		private String nodeid;
		private String name;
		private String hostname;
		private String privateip;
		private String dbusername;
		private String dbpassword;
		private String systemtype;
		
		/**
		 * @return the systemid field of the JSON
		 * If it cannot be parsed as an integer, returns <code>-1</code>.
		 */
		public Integer getSystemid() {
			try {
				return Integer.parseInt(systemid);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the nodeid field of the JSON
		 * If it cannot be parsed as an integer, returns <code>-1</code>.
		 */
		public Integer getNodeid() {
			try {
				return Integer.parseInt(nodeid);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the name field of the JSON
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return the hostname field of the JSON
		 */
		public String getHostname() {
			return hostname;
		}
		/**
		 * @return the privateip field of the JSON
		 */
		public String getPrivateip() {
			return privateip;
		}
		/**
		 * @return the dbusername field of the JSON
		 */
		public String getDbusername() {
			return dbusername;
		}
		/**
		 * @return the dbpassword field of the JSON
		 */
		public String getDbpassword() {
			return dbpassword;
		}
		/**
		 * @return the systemtype field of the JSON
		 */
		public String getSystemtype() {
			return systemtype;
		}
	}

	/**
	 * Constructor. It is empty.
	 */
	public GsonProvisionedNode() {}

}
