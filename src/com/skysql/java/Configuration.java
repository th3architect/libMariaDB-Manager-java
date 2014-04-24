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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

/**
 * Loads the configuration from an appropriate configuration file.
 * The standard MariaDB-Manager configuration file is located in
 * /usr/local/skysql/config/mariadb-manager.ini
 * and contains sections for the different components, the API
 * and the Remote Execution package.
 * The main use of this class is to load the set of keys/values
 * from one of the configuration files, in the specified format,
 * and provide them to a Java application.
 * 
 * @author Massimo Siani
 *
 */
public class Configuration {
	private final static String		DEFAULT_FILEPATH = "/etc/mariadbmanager/manager.ini";
	private static DEFAULT_SECTION	RUNNING_APPLICATION = DEFAULT_SECTION.MONITOR;
	/**
	 * Full path name.
	 */
	private String					m_filePath;
	/**
	 * (section, key, value).
	 */
	private Map<String, Map<String, String>>	m_config;
	/**
	 * Configuration object. It is similar to a Map<K, V>.
	 */
	private Ini						m_ini;
	
	/**
	 * Using this <code>enum</code> instead of explicit
	 * section names increases maintainability over time.
	 * @author Massimo Siani
	 *
	 */
	public enum DEFAULT_SECTION {
		APIKEYS("apikeys"),
		APIHOST("apihost"),
		MONITOR("monitor"),
		WEBUI("ui");
		
		private final String sectionName;
		DEFAULT_SECTION (String sectionName) {
			this.sectionName = sectionName;
		}
		public String getSectionName () {
			return sectionName;
		}
	}
	
	/**
	 * Constructor that uses the default parameters. The full file name
	 * of the configuration file is hard coded:
	 * /usr/local/skysql/config/manager.ini
	 */
	public Configuration() {
		this(DEFAULT_FILEPATH);
	}

	/**
	 * This constructor allows to set the file name, full path is necessary.
	 * @param filePath		the full path name of the configuration file
	 */
	public Configuration(String filePath) {
		m_config = new HashMap<String, Map<String,String>>();
		m_filePath = filePath;
		reloadFile();
		reloadAll();
	}
	
	public static DEFAULT_SECTION getApplication() {
		return RUNNING_APPLICATION;
	}
	
	public static void setApplication(DEFAULT_SECTION application) {
		RUNNING_APPLICATION = application;
	}
	
	/**
	 * Reloads the configuration file, despite it does not update the
	 * internal state of the application.
	 */
	private void reloadFile() {
		Logging.info("Loading configuration file " + m_filePath);
		try {
			m_ini = new Ini(new File(m_filePath));
		} catch (InvalidFileFormatException e) {
			Logging.error(e.getMessage());
			return;
		} catch (IOException e) {
			Logging.error("I/O error while reading file " + m_filePath);
			return;
		}
	}
	
	/**
	 * Loads, or reloads, the configuration from a specific section.
	 * This is provided to
	 * avoid having to restart the application each time the
	 * configuration file changes. The values can be retrieved by
	 * the <code>getConfig</code> method.
	 * @see						getConfig
	 * @param section			the section in the configuration file to reload
	 * @param reloadFile		if <code>true</code>, reloads the configuration file
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 */
	public void reload(DEFAULT_SECTION section, boolean reloadFile) {
		String sectionName = section.getSectionName();
		Logging.info("Loading configuration section [" + sectionName + "]");
		if (reloadFile) {
			reloadFile();
		}
		Section sectionConfig = m_ini.get(sectionName);
		if (sectionConfig == null) {
			Logging.info("Section " + sectionName + " not found");
			return;
		}
		for (String key : sectionConfig.keySet()) {
			sectionConfig.put(key, validateValue(sectionConfig.get(key)));
		}
		m_config.put(sectionName, sectionConfig);
	}
	
	/**
	 * Loads, or reloads, the configuration from all the default sections.
	 * This is provided to
	 * avoid having to restart the application each time the
	 * configuration file changes. The values can be retrieved by
	 * the <code>getConfig</code> method.
	 * @see			getConfig
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public void reloadAll() {
		for (DEFAULT_SECTION section : DEFAULT_SECTION.values()) {
			reload(section, false);
		}
	}
	
	/**
	 * Returns a map of the (section, key, value).
	 * @return	a map of the (section, key, value)
	 */
	public Map<String, Map<String, String>> getConfig() {
		return m_config;
	}
	
	/**
	 * Returns a map of the keys / values within the given section.
	 * @param section		the section to be retrieved
	 * @return	a map of the keys / values within the given section
	 */
	public Map<String, String> getConfig(String section) {
		return m_config.get(section);
	}
	
	/**
	 * Returns a map of the keys / values within the given section. This method
	 * guarantees more maintainability when retrieving one the default sections.
	 * @param section		the section to be retrieved
	 * @return	a map of the keys / values within the given section
	 */
	public Map<String, String> getConfig(DEFAULT_SECTION section) {
		return m_config.get(section.getSectionName());
	}
	
	/**
	 * The following defines the rules used in the configuration file.
	 * <p>
	 * For strings, quotes are optional.
	 * For numbers, quotes are optional.
	 * There is no name validation.
	 * Boolean flags can be turned on using the values 1, On, True or Yes.
	 * They can be turned off using the values 0, Off, False or No.
	 * An empty string can be denoted by simply not writing anything after the
	 * equal sign, or by using the None keyword:
	 * foo =         ; sets foo to an empty string
	 * foo = None    ; sets foo to an empty string
	 * foo = "None"  ; sets foo to the string 'None'
	 * 
	 * @param value		the value to be validate with the rules above
	 */
	private String validateValue (String value) {
		String result = "";
		if ("None".equalsIgnoreCase(value) || value.isEmpty()) {
			result = "";
		} else if (equalsOneOf(value, "On", "True", "Yes")) {
			result = "true";
		} else if (equalsOneOf(value, "Off", "False", "No")) {
			result = "false";
		} else {
			result = value.replaceAll("^\"|\"$", "");
		}
		return result;
	}
	
	/**
	 * Returns <code>true</code> if the first parameter matches any of
	 * the other parameters, <code>false</code> otherwise.
	 * <p>
	 * *Case-insensitive*.
	 * @param value			the parameter that should be checked for matching
	 * @param strings		the list of values to be compared to <code>value</code>
	 * @return				<code>true</code> if <code>value</code> matches at least
	 * one parameter, <code>false</code> otherwise
	 */
	private boolean equalsOneOf (String value, String...strings) {
		boolean result = false;
		for (String string : strings) {
			result = result || value.equalsIgnoreCase(string);
		}
		return result;
	}

}
