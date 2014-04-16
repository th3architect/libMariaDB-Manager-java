package com.skysql.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class EncryptionTest {
	Encryption encryption = new Encryption();
	String string = "password the quick brown fox jumps over the lazy dog and into the river which is flowing fast but the dog is a good swimmer";
	String key = "2ee38c49e262dbec7dedfd88412478c1";
	String encoded = "cTelfIGTqYZRqYw6Rqmpwb2Tebx6wrNDTjaPgEdGhcJxSXhwgompSoW9iXeKuaqxfIq+t6i4ujlOQZB0kHyEvYZ2pnKCialKiL2NOI54vat8iL2/d8C6Ok42gXd7fJKqdXascY9Ms42YdY1IRrl0t7mPtXp7qrVAkzV0lmebcZdlaJ2VZZmXaGuaa21nmcaYm5yayZWcl51wZmE=";
	

	@Test
	public void encodeTest() {
		assertEquals(encoded, encryption.encrypt(string, key));
	}
	
	@Test
	public void decodeTest() {
		String enc = encryption.encrypt(string, key);
		assertEquals(string, encryption.decrypt(enc, key));
	}

}
