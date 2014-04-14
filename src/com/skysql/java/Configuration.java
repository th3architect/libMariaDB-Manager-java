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
	/**
	 * Full path name.
	 */
	private String					m_filePath;
	/**
	 * (section, key, value).
	 */
	private Map<String, Map<String, String>>	m_config;
	
	/**
	 * Using this <code>enum</code> instead of explicit
	 * section names increases maintainability over time.
	 * @author Massimo Siani
	 *
	 */
	public enum DEFAULT_SECTIONS {
		APIKEYS("apikeys"),
		APIHOST("apihost"),
		MONITOR("mariadb-manager-monitor"),
		WEBUI("mariadb-manager-webui");
		
		private final String sectionName;
		DEFAULT_SECTIONS (String sectionName) {
			this.sectionName = sectionName;
		}
		public String getSectionName () {
			return sectionName;
		}
	}

	/**
	 * Constructor for this class.
	 * @param filePath		the full path name of the configuration file
	 */
	public Configuration(String filePath) {
		m_filePath = filePath;
		m_config = new HashMap<String, Map<String,String>>();
	}
	
	/**
	 * Loads, or reloads, the configuration from a specific section.
	 * This is provided to
	 * avoid having to restart the application each time the
	 * configuration file changes. The values can be retrieved by
	 * the getConfig() method.
	 * @see						getConfig
	 * @param section			the section in the configuration file to reload
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 */
	public void reload(DEFAULT_SECTIONS section) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(new File(m_filePath));
		Map<String, String> monitorConfig = ini.get(section);
		m_config.put(section.getSectionName(), monitorConfig);
	}
	
	/**
	 * Loads, or reloads, the configuration from all the default sections.
	 * This is provided to
	 * avoid having to restart the application each time the
	 * configuration file changes. The values can be retrieved by
	 * the getConfig() method.
	 * @see			getConfig
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public void reloadAll() throws InvalidFileFormatException, IOException {
		for (DEFAULT_SECTIONS section : DEFAULT_SECTIONS.values()) {
			reload(section);
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

}
