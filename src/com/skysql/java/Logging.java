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

import java.util.Random;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.message.modifier.text.PrefixSyslogMessageModifier;

/**
 * Class to handle the centralized logging.
 * This class sends messages to the syslog or rsyslog daemon,
 * with configurable parameters. By default, the syslog daemon
 * listens on the port 514, udp protocol.
 * Being a singleton, an application does not need to redefine
 * its parameters until it shuts down.
 * 
 * @author Massimo Siani
 *
 */
public class Logging {
	/** Instance to handle the logging mechanism. */
	private SyslogIF					m_syslog;
	/** Instance of this singleton. */
	private static volatile Logging		INSTANCE = null;
	/** Host that runs the syslog daemon. */
	private static String				m_host = "127.0.0.1";
	/** Syslog protocol. */
	private String						m_protocol = Syslog.UNIX_SOCKET;
	/** Syslog port. */
	private static int					m_port = 514;
	/** The prefix of every log string. */
	private String						m_prefix;
	/** The component that is logging. */
	private static String				m_component = "";
	
	/**
	 * @return	this instance
	 */
	private static Logging getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Logging();
		}
		return INSTANCE;
	}
	/**
	 * @return	The syslog interface
	 */
	private static SyslogIF getSyslog() {
		return getInstance().m_syslog;
	}
	
	/**
	 * Sets the host. The default value is 127.0.0.1.
	 * 
	 * @param host			the log host
	 * @return				the instance of the class
	 */
	public static Logging setHost(String host) {
		m_host = host;
		getSyslog().getConfig().setHost(m_host);
		return getInstance();
	}

	/**
	 * Sets the protocol. The default value is the Unix socket.
	 * 
	 * @param protocol		the protocol to set
	 * @return				the instance of the class
	 */
	public static Logging setProtocol(String protocol) {
		if (! Syslog.exists(protocol)) {
			return getInstance();
		}
		getInstance().m_protocol = protocol;
		INSTANCE = null;
		INSTANCE = new Logging();
		setHost(m_host);
		return setPort(m_port);
	}

	/**
	 * Sets the port. The default value is 514.
	 * 
	 * @param port			the port to set
	 * @return				the instance of the class
	 */
	public static Logging setPort(int port) {
		m_port = port;
		getSyslog().getConfig().setPort(m_port);
		return getInstance();
	}
	
	/**
	 * Set the component that is logging. The default value is an empty string.
	 * The string will be part of the prefix string:
	 * MariaDB-Manager-(component): [random number]
	 * 
	 * @param component		the component, ie Monitor, WebUI.
	 * @return				the instance of the class
	 */
	public static Logging setComponent(String component) {
		m_component = component;
		return getInstance();
	}

	/**
	 * Constructor for the class. Builds the prefix string from the available information.
	 */
	private Logging() {
		m_syslog = Syslog.getInstance(m_protocol);
		m_syslog.getConfig().setFacility("user");
		m_prefix = "MariaDB-Manager-" + m_component + ": ";
		Random random = new Random();
		m_prefix += "[" + random.nextInt(999999) + "]";
		PrefixSyslogMessageModifier prefixModifier = new PrefixSyslogMessageModifier(m_prefix);
		m_syslog.getConfig().removeAllMessageModifiers();
		m_syslog.getConfig().addMessageModifier(prefixModifier);
	}
	
	/**
	 * Logs with info level.
	 * 
	 * @param message		the string that will appear in the log
	 */
	public static void info(String message) {
		try {
			getSyslog().info(message);
		} catch (Exception e) {
			System.out.println("info log.");
		}
	}
	
	/**
	 * Logs with error level.
	 * 
	 * @param message		the string that will appear in the log
	 */
	public static void error(String message) {
		try {
			getSyslog().error(message);
		} catch (Exception e) {}
	}
	
	/**
	 * Logs with warning level.
	 * 
	 * @param message		the string that will appear in the log
	 */
	public static void warn(String message) {
		try {
			getSyslog().warn(message);
		} catch (Exception e) {}
	}
	/**
	 * Logs with debug level.
	 * 
	 * @param message		the string that will appear in the log
	 */
	public static void debug(String message) {
		try {
			getSyslog().debug(message);
		} catch (Exception e) {}
	}

}
