package edu.bionic.com.PersistenceLayer.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
	public static String md5(String text) {
		String md5 = null;
		if (text == null)
			return null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(text.trim().getBytes());

			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			md5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

}
