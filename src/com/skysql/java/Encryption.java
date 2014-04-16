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
		salt = "" + 10;
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
				int index = (count % key.length()) - 1 < 0 ? key.length()-1 : (count % key.length()) - 1;
				char keyChar = key.charAt(index);
				result += (char)((int) (string.charAt(count)) + (int) (keyChar));
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return result;
	}

	/**
	 * @param encryptedString
	 * @param key
	 * @return
	 */
	public String decrypt(String encryptedString, String key) {
		String result = "";
		// String string = new String(Base64.decode(encryptedString));
		String string = encryptedString;
		for(int count = 0; count < encryptedString.length(); count++) {
			int index = (count % key.length()) - 1 < 0 ? key.length()-1 : (count % key.length()) - 1;
			char keyChar = key.charAt(index);
			int asciiCode = (int)(string.charAt(count)) - (int)(keyChar);
			asciiCode = asciiCode % 256;
			if (asciiCode < 0) {
				asciiCode += 256;
			}
			result += (char) (asciiCode);
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
			int index = (count % b.length()) - 1 < 0 ? b.length()-1 : (count % b.length()) - 1;
			char saltChar = b.charAt(index);
			result += Character.toString((char) (asciiValue ^ (int)(saltChar)));
		}
		return result;
	}	

}
