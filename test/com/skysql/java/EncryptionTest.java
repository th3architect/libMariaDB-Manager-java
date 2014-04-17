package com.skysql.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class EncryptionTest {
	Encryption encryption = new Encryption();
	String string = "password the quick brown fox jumps over the lazy dog and into the river which is flowing fast but the dog is a good swimmer";
	String key = "2ee38c49e262dbec7dedfd88412478c1";
	
	
	@Test
	public void decodeTest() {
		String enc = encryption.encrypt(string, key);
		assertEquals(string, encryption.decrypt(enc, key));
	}

}
