package com.inschos.proposal.kit;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

public class Des3Util {

		// 密钥

		//Hom563WqjzYdIPIx3ImJ8j3hLzysqldK 	e4EispyU
		//cejFalk3fAw3rp2L3d2c3o23	DESede
		private final static String secretKey = "cejFalk3fAw3rp2L3d2c3o23";//ConstantUtil.getInfoMD5();
		// 向量
		private final static String iv = "DESede";
		// 加解密统一使用的编码方式
		private final static String encoding = "utf-8";

		/**
		 * 3DES加密
		 * 
		 * @param plainText 普通文本
		 * @return
		 * @throws Exception 
		 */
		public static String encode(String plainText) throws Exception {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return Base64Util.encode(encryptData);
		}

		/**
		 * 3DES解密
		 * 
		 * @param encryptText 加密文本
		 * @return
		 * @throws Exception
		 */
		public static String decode(String encryptText) throws Exception {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));

			return new String(decryptData, encoding);
		}

		
	}

