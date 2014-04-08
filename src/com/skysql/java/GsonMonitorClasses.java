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
import java.util.Iterator;
import java.util.List;

/**
 * Contains the fields for the "monitorclass" API call.
 * 
 * @author Massimo Siani
 *
 */
public class GsonMonitorClasses extends GsonErrors {
	private final static int ERROR_INT_RETURN = -1;
	private MonitorClasses monitorclass;
	private List<MonitorClasses> monitorclasses;

	/**
	 * Constructor.
	 * This is only necessary to initialize the class with existing data,
	 * for instance cached data.
	 * 
	 * @param monitorClass		a single monitor class object
	 */
	public GsonMonitorClasses (MonitorClasses monitorClass) {
		this.monitorclass = monitorClass;
	}
	/**
	 * Constructor.
	 * This is only necessary to initialize the class with existing data,
	 * for instance cached data.
	 * 
	 * @param monitorClasses	a list of monitor class objects
	 */
	public GsonMonitorClasses (List<MonitorClasses> monitorClasses) {
		this.monitorclasses = monitorClasses;
	}
	
	/**
	 * Return a single MonitorClass object at a given position.
	 * The first element is at position 0.
	 * 
	 * @param index		the position 
	 * @return			the corresponding MonitorClass object
	 */
	public MonitorClasses getMonitorClass(int index) {
		if (index < 0) return null;
		if (getMonitorClasses() == null || getMonitorClasses().size() < index+1) return null;
		return getMonitorClasses().get(index);
	}
	/**
	 * Gets the list of cached monitor classes. If a list exists, called monitorclasses, it is returned.
	 * If a list does not exist but a single monitor class exists, a list of one element is returned.
	 * If nothing is set, returns null.
	 * 
	 * @return the monitorclasses list, or the monitorclass wrapped in a list, or null
	 */
	public List<MonitorClasses> getMonitorClasses() {
		if (monitorclasses != null) return monitorclasses;
		if (monitorclass == null) return null;
		List<MonitorClasses> listMonitorClasses = new ArrayList<MonitorClasses>(1);
		listMonitorClasses.add(monitorclass);
		return listMonitorClasses;
	}

	public static class MonitorClasses {
		private String systemtype;
		private String monitor;
		private String name;
		private String sql;
		private String description;
		private String decimals;
		private String mapping;
		private String charttype;
		private String delta;
		private String monitortype;
		private String systemaverage;
		private String interval;
		private String unit;
		private String monitorid;
		
		/**
		 * @return the systemtype field of the JSON
		 */
		public String getSystemType() {
			return systemtype;
		}
		/**
		 * @return the monitor field of the JSON
		 */
		public String getMonitor() {
			return monitor;
		}
		/**
		 * @return the name field of the JSON
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return the sql field of the JSON
		 */
		public String getSql() {
			return sql;
		}
		/**
		 * @return the description field of the JSON
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @return the decimals field of the JSON
		 */
		public String getDecimals() {
			return decimals;
		}
		/**
		 * @return the mapping field of the JSON
		 */
		public String getMapping() {
			return mapping;
		}
		/**
		 * @return the charttype field of the JSON
		 */
		public String getChartType() {
			return charttype;
		}
		/**
		 * @return the delta field of the JSON, if it can be parsed as an integer value,
		 * or <code>-1</code>
		 */
		public int getDelta() {
			try {
				return Integer.parseInt(delta);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the monitortype field of the JSON
		 */
		public String getMonitorType() {
			return monitortype;
		}
		/**
		 * @return the systemaverage field of the JSON, if it can be parsed as an integer value,
		 * or <code>-1</code>
		 */
		public int getSystemAverage() {
			try {
				return Integer.parseInt(systemaverage);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the interval field of the JSON, if it can be parsed as an integer value,
		 * or <code>-1</code>
		 */
		public int getInterval() {
			try {
				return Integer.parseInt(interval);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the unit field of the JSON
		 */
		public String getUnit() {
			return unit;
		}
		/**
		 * @return the monitorid field of the JSON, if it can be parsed as an integer value,
		 * or <code>-1</code>
		 */
		public int getMonitorId() {
			try {
				return Integer.parseInt(monitorid);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
	}
	
	/**
	 * Constructor. It is empty.
	 */
	public GsonMonitorClasses() {}
	
	/**
	 * Get the list of monitor id's.
	 * 
	 * @return a list with the monitor id's, or null if no system
	 * if defined.
	 */
	public List<Integer> getMonitorIdList() {
		List<Integer> result = new ArrayList<Integer>();
		if (this.getMonitorClasses() != null) {
			Iterator<GsonMonitorClasses.MonitorClasses> it = getMonitorClasses().iterator();
			while (it.hasNext()) {
				result.add(it.next().getMonitorId());
			}
		} else return null;
		return result;
	}

}