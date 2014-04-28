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
 * The Monitor data entity of the API.
 * 
 * @author Massimo Siani
 *
 */
public class GsonMonitorData extends GsonErrors {
	private MonitorData monitor_data;
	private MonitorData monitor_rawdata;
	
	/**
	 * The Monitor data object of the API.
	 * 
	 * @return the monitor_data object.
	 */
	private MonitorData getMonitor_data() {
		return monitor_data;
	}
	/**
	 * The Monitor Raw data object of the API.
	 * 
	 * @return the monitor_rawdata object.
	 */
	private MonitorData getMonitor_rawdata() {
		return monitor_rawdata;
	}
	
	/**
	 * Returns a <code>monitor_data</code> object if available,
	 * or a <code>monitor_rawdata</code> object.
	 * If both of them are <code>null</code>, returns <code>null</code>.
	 * 
	 * @return	the MonitorData object, <code>null</code> if not set
	 */
	public MonitorData getMonitorData() {
		if (getMonitor_data() != null) return getMonitor_data();
		else if (getMonitor_rawdata() != null) return getMonitor_rawdata();
		else return null;
	}

	/**
	 * The class that encodes the monitor_data object.
	 * 
	 * @author Massimo Siani
	 *
	 */
	public static class MonitorData {
		private List<String> timestamp;
		private List<String> value;
		private List<String> repeats;
		
		/**
		 * @return the timestamp
		 */
		public List<String> getTimestamp() {
			return timestamp;
		}
		/**
		 * @return the value
		 */
		public List<String> getValue() {
			return value;
		}
		/**
		 * @return the repeats
		 */
		public List<String> getRepeats() {
			return repeats;
		}
	}
}
