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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.productivity.java.syslog4j.util.Base64;

/**
 * Provides the means to encrypt data to be sent to the API.
 * 
 * @author Massimo Siani
 *
 */
public class Encryption {

	/**
	 * Constructor.
	 */
	public Encryption() {}

	/**
	 * @param string
	 * @param key
	 * @return
	 */
	public String encrypt(String string, String key) {
		String result = "";
		Random random = new Random();
		String salt = "" + random.nextInt(10000000);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(salt.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			salt = sb.toString();
			string = bitwiseXor(string, salt);
			string += salt;
			for(int count = 0; count < string.length(); count++) {
				char keyChar = key.charAt(count % key.length());
				result += (char)((int) (string.charAt(count)) + (int) (keyChar));
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return Base64.encodeBytes(result.getBytes());
	}

	/**
	 * @param encryptedString
	 * @param key
	 * @return
	 */
	public String decrypt(String encryptedString, String key) {
		String result = "";
		String string = new String(Base64.decode(encryptedString));
		for(int count = 0; count < encryptedString.length(); count++) {
			char keyChar = key.charAt((count % key.length()));
			result += (char) ((int)(string.charAt(count)) - (int)(keyChar));
		}
		String salt = result.substring(result.length()-32);
		return bitwiseXor(result.substring(0, result.length()-32), salt);
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private String bitwiseXor (String a, String b) {
		String result = "";
		for (int count = 0; count < a.length(); count++) {
			int asciiValue = (int) (a.charAt(count));
			char saltChar = b.charAt(count % b.length());
			result += Character.toString((char) (asciiValue ^ (int)(saltChar)));
		}
		return result;
	}	

}
