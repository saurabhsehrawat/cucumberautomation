package com.bdd.helper;

import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncyptDecryptHelper {
	
	  public static void main(String[] args) throws IllegalBlockSizeException,
	  BadPaddingException {
	  System.out.println("Encrypt value:- "+decrypt("2cXQush1YJMdbnFVMcY+Mg=="));
	  
	  }
	 

	public static String encrypt(String value) {
		String encrypt_key = "8082345678808080";
		String iv = "8080808080808080";
		byte[] encrypted = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = encrypt_key.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv_key = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv_key);
			encrypted = cipher.doFinal(value.getBytes("utf-8"));
		} catch (Exception e) {

		}
		return new String(Base64.getEncoder().encode(encrypted));

	}
	public static String decrypt(String value) {
		String encrypt_key = "8082345678808080";
		String iv = "8080808080808080";
		byte[] decypted = null;
		
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = encrypt_key.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv_key = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv_key);
			decypted = cipher.doFinal(Base64.getDecoder().decode(value));
		} catch (Exception e) {

		}
		return new String(decypted);

	}

	
}
