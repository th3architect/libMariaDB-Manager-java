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

import java.util.Random;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.message.modifier.text.PrefixSyslogMessageModifier;

/**
 * Class to handle the centralized monitor logging.
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
	private String						m_protocol = "udp";
	/** Syslog port. */
	private static int					m_port = 514;
	/** The prefix of every log string. */
	private String						m_prefix;
	/** The component that is logging. */
	private static String				m_component;
	
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
	 * Set a host different from 127.0.0.1.
	 * 
	 * @param host			The log host
	 * @return				the instance of the class
	 */
	public static Logging setHost(String host) {
		m_host = host;
		getSyslog().getConfig().setHost(m_host);
		return getInstance();
	}

	/**
	 * Set a protocol different from the default.
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
	 * Set a port different from 514.
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
	 * Set the component that is logging.
	 * 
	 * @param component		The component, ie Monitor, WebUI.
	 * @return				the instance of the class
	 */
	public static Logging setComponent(String component) {
		m_component = component;
		return getInstance();
	}

	/**
	 * Constructor for the class.
	 */
	private Logging() {
		m_syslog = Syslog.getInstance(m_protocol);
		m_syslog.getConfig().setFacility("user");
		m_prefix = "MariaDB-Manager-" + m_component + ": ";
		Random random = new Random();
		m_prefix += "[" + random.nextInt(999999) + "] ";
		PrefixSyslogMessageModifier prefixModifier = new PrefixSyslogMessageModifier(m_prefix);
		m_syslog.getConfig().addMessageModifier(prefixModifier);
	}
	
	/**
	 * Log an info.
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
	 * Log with error level.
	 * 
	 * @param message
	 */
	public static void error(String message) {
		try {
			getSyslog().error(message);
		} catch (Exception e) {}
	}
	
	/**
	 * Log with warning level.
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		try {
			getSyslog().warn(message);
		} catch (Exception e) {}
	}
	/**
	 * Log with debug level.
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		try {
			getSyslog().debug(message);
		} catch (Exception e) {}
	}

}
