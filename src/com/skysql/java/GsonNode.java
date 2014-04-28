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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contains the fields for the "node" API call.
 * 
 * @author Massimo Siani
 *
 */
public class GsonNode extends GsonErrors {
	private final static int ERROR_INT_RETURN = -1;
	private Nodes node;
	private List<Nodes> nodes;
	
	/**
	 * Constructor for one node. Initializes the <code>node</code> field.
	 * 
	 * @param node	the node to be included in the object
	 */
	public GsonNode(Nodes node) {
		this.node = node;
	}
	/**
	 * Constructor for more than a node. Initializes the <code>nodes</code> list field.
	 * 
	 * @param nodes	the list of nodes to be included in the object
	 */
	public GsonNode(List<Nodes> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * Gets the <code>node</code> field. Returns <code>null</code> if it was not
	 * included in the JSON or initialized by the constructor.
	 * 
	 * @return the node
	 */
	private Nodes getNode() {
		return node;
	}

	/**
	 * Gets the <code>nodes</code> field. Returns <code>null</code> if it was not
	 * included in the JSON or initialized by the constructor.
	 * 
	 * @return the nodes in this object as a list. If a single node is retrieved,
	 * it is wrapped in a list.
	 */
	public List<Nodes> getNodes() {
		if (nodes != null) return nodes;
		else if (getNode() != null) {
			List<Nodes> listNodes = new ArrayList<Nodes>(1);
			listNodes.add(getNode());
			return listNodes;
		}
		return null;
	}
	/**
	 * Returns a single Node object at a given position.
	 * The first element is at position 0.
	 * 
	 * @param index		the position index in the node list
	 * @return			the corresponding MonitorClass object
	 */
	public Nodes getNode(int index) {
		if (index < 0) return null;
		List<Nodes> dummy = getNodes();
		if (dummy == null || dummy.size() < index+1) return null;
		else return dummy.get(index);
	}

	/**
	 * This class contains the information about a node entity.
	 * 
	 * @author Massimo Siani
	 *
	 */
	public static class Nodes {
		private String systemid;
		private String nodeid;
		private String name;
		private String state;
		private String updated;
		private String hostname;
		private String publicip;
		private String privateip;
		private String port;
		private String instanceid;
		private String dbusername;
		private String dbpassword;
		private String repusername;
		private String reppassword;
		private List<Commands> commands;
		private GsonSharedMonitorLatest monitorlatest;
		private String lastmonitored;
		private String command;
		private String taskid;
		
		/**
		 * @return the systemid field of the JSON.
		 * If it cannot be parsed as a number, returns <code>-1</code>.
		 */
		public int getSystemId() {
			try {
				return Integer.parseInt(systemid);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the nodeid field of the JSON.
		 * If it cannot be parsed as a number, returns <code>-1</code>.
		 */
		public int getNodeId() {
			try {
				return Integer.parseInt(nodeid);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the name field of the JSON.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return the state field of the JSON.
		 */
		public String getState() {
			return state;
		}
		/**
		 * @return the hostname field of the JSON.
		 */
		public String getHostname() {
			return hostname;
		}
		/**
		 * @return the publicIP field of the JSON.
		 */
		public String getPublicIP() {
			return publicip;
		}
		/**
		 * @return the privateIP field of the JSON.
		 */
		public String getPrivateIP() {
			return privateip;
		}
		/**
		 * @return the port field of the JSON.
		 */
		public String getPort() {
			return port;
		}
		/**
		 * @return the instanceID field of the JSON.
		 * If it is empty, returns <code>0</code>.
		 * If it cannot be parsed as a number, returns <code>-1</code>.
		 */
		public int getInstanceID() {
			if (instanceid.isEmpty()) return 0;
			try {
				return Integer.parseInt(instanceid);
			} catch (NumberFormatException nfex) {
				return ERROR_INT_RETURN;
			}
		}
		/**
		 * @return the dbusername field of the JSON.
		 */
		public String getDbUserName() {
			return dbusername;
		}
		/**
		 * @return the dbpassword field of the JSON.
		 */
		public String getDbPassword() {
			return dbpassword;
		}
		/**
		 * @return the commands field of the JSON.
		 */
		public List<Commands> getCommands() {
			return commands;
		}
		/**
		 * @return the monitorlatest field of the JSON.
		 */
		public GsonSharedMonitorLatest getMonitorLatest() {
			return monitorlatest;
		}
		/**
		 * @return the lastmonitored field of the JSON.
		 */
		public String getLastMonitored() {
			return lastmonitored;
		}
		/**
		 * @return the command field of the JSON.
		 */
		public String getCommand() {
			return command;
		}
		/**
		 * @return the taskid field of the JSON.
		 */
		public String getTaskId() {
			return taskid;
		}
		/**
		 * If set, this value overrides the corresponding
		 * one in the System class.
		 * 
		 * @return the repusername field of the JSON.
		 */
		public String getRepUserName() {
			return repusername;
		}
		/**
		 * If set, this value overrides the corresponding
		 * one in the System class.
		 * 
		 * @return the reppassword field of the JSON.
		 */
		public String getRepPassword() {
			return reppassword;
		}
		/**
		 * The timestamp for the last change. 
		 * 
		 * @return the string representing the last update time
		 */
		public String getLastUpdateTime() {
			return updated;
		}
	}
	
	/**
	 * Sub-object of the Node entity.
	 * 
	 * @author Massimo Siani
	 *
	 */
	public static class Commands {
		private String command;
		private String description;
		private String icon;
		private String steps;
		
		/**
		 * @return the command field of the JSON.
		 */
		public String getCommand() {
			return command;
		}
		/**
		 * @return the description field of the JSON.
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @return the icon field of the JSON.
		 */
		public String getIcon() {
			return icon;
		}
		/**
		 * @return the steps field of the JSON.
		 */
		public String getSteps() {
			return steps;
		}
	}
	
	/**
	 * Get the list of node id's.
	 * 
	 * @return a list of available nodes, or null if no node is found.
	 */
	public List<Integer> getNodeIdList() {
		List<Integer> result = new ArrayList<Integer>();
		if (this.getNodes() != null) {
			Iterator<GsonNode.Nodes> it = getNodes().iterator();
			while (it.hasNext()) {
				result.add(it.next().getNodeId());
			}
		} else return null;
		return result;
	}
	/**
	 * Fetch the states of the available nodes.
	 * 
	 * @return the list of states, null if no node is found
	 */
	public List<String> getNodeStateList() {
		List<String> result = new ArrayList<String>();
		if (this.getNodes() != null) {
			Iterator<GsonNode.Nodes> it = getNodes().iterator();
			while (it.hasNext()) {
				result.add(it.next().getState());
			}
		} else if (this.getNode() != null) {
			result.add(this.getNode().getState());
		} else result = null;
		return result;
	}
	
}
