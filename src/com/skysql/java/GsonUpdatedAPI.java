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

/**
 * Encodes whether an API update or create request succeeded.
 * 
 * @author Massimo Siani
 *
 */
public class GsonUpdatedAPI extends GsonErrors {
	private Integer updatecount;
	private Integer insertkey;
	
	/**
	 * @return	the number of updated rows
	 */
	public Integer getUpdateCount() {
		return updatecount;
	}
	/**
	 * @return	the number of new keys
	 */
	public Integer getInsertedKey() {
		return insertkey;
	}
}
