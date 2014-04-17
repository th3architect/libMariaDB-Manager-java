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

import java.io.UnsupportedEncodingException;
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
		Random random = new Random();
		String salt = "" + random.nextInt(10000000);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(salt.getBytes("Cp437"));
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			salt = sb.toString();
			string = bitwiseXor(string, salt);
			string += salt;
			byte bytes[] = string.getBytes("Cp437");
			byte out[] = string.getBytes("Cp437");
			for(int count = 0; count < string.length(); count++) {
				int index = (count % key.length()) - 1 < 0 ? key.length()-1 : (count % key.length()) - 1;
				char keyChar = key.charAt(index);
				int stringChar = bytes[count] < 0 ? bytes[count] + 256 : bytes[count];
				out[count] = new Byte((byte) (stringChar + (int)keyChar));
			}
			String base64Encoded = Base64.encodeBytes(out);
			return base64Encoded;
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	/**
	 * @param encryptedString
	 * @param key
	 * @return
	 */
	public String decrypt(String encryptedString, String key) {
		String result = "";
		String string;
		byte bytes[];
		try {
			string = new String(Base64.decode(encryptedString), "Cp437");
			bytes = string.getBytes("Cp437");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		for(int count = 0; count < string.length(); count++) {
			int index = (count % key.length()) - 1 < 0 ? key.length()-1 : (count % key.length()) - 1;
			int asciiCode = bytes[count] - (int)(key.charAt(index));
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
