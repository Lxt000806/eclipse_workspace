package com.house.framework.commons.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.house.framework.commons.conf.SystemConfig;
import com.sun.org.apache.bcel.internal.generic.NEW;
 
/**
 * 加密解密工具包
 */
public class RSAUtils {
	
    public static void main(String[] args) {
    	getRSAKeys();
    	String privateKey = SystemConfig.getProperty("privateKey","","rsaKey");
    	String publicKey = SystemConfig.getProperty("publicKey","","rsaKey");
    	RSAUtils.getPublicKey(publicKey);
    	
    	String encryptDataString = "PhuA6/5iYFGHTiEN7AjafUacErssc4JS27j4DWP3NqrfX2ZzUJvHgUVZhOglPJ95ItP7hVFtceIwJ2edwOBLj1YTyHkOfj+c4Oj2EaOlYS1zcP+DN1WD/qNk55IR6LfS3ddfU23p+Ps/dpUCqBqu2DCsVpjWLgEsryqUPipNX4F7YZwRgAl9cLSEMvMZ7cDvFLPUCNxism1XkfgqN0GkEjyFcKp7fZpWVZby0sU+t19cFG5ORsggTl3kL3Ma+DknW2R1f9WN7u51hY+hz69cWBtEMm+9aLTlIYMwn41utF5zq/UEXgYZ0coaAYiFcL/+g4h1zOeOFQQzHXlUXGtB2g==";
    	
    	System.out.println(new String(Base64.decodeBase64(encryptDataString)));
    	
    	byte[] encryptData = RSAUtils.encryptByPublicKey("hellp".getBytes(), publicKey);
    	System.out.println(new String(encryptData));
    	byte[] decryptData = decryptByPrivateKey(Base64.decodeBase64(encryptDataString), privateKey);
    	System.out.println("----------------");
/*    	for(int i = 0;i < encryptData.length;i++){
    		System.out.println(encryptData[i]);
    	}*/
    	System.out.println(new String(decryptData));
    	
    }
    
	@SuppressWarnings("finally")
	public static Map<String, Object> getRSAKeys(){
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	RSAPublicKey rsaPublicKey = null;
    	RSAPrivateKey rsaPrivateKey = null;
    	try{
        	KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();  
        	keyPairGenerator.initialize(2048, random);
        	KeyPair keyPair = keyPairGenerator.generateKeyPair();
        	rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        	rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        	System.out.println("private");
        	System.out.println(Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        	System.out.println("public");
        	System.out.println(Base64.encodeBase64String(rsaPublicKey.getEncoded()));
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		returnMap.put("privateKey", rsaPrivateKey);
    		returnMap.put("publicKey", rsaPublicKey);
    		return returnMap;
    	}
    }
    
	@SuppressWarnings("finally")
	public static RSAPublicKey getPublicKey(String publicKey){
		RSAPublicKey rsaPublicKey = null;
		try{
			byte[] keyBytes = Base64.decodeBase64(publicKey);  
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return rsaPublicKey;
		}
	}
	
	@SuppressWarnings("finally")
	public static RSAPrivateKey getPrivateKey(String privateKey){
		RSAPrivateKey rsaPrivateKey = null;
		try{
			byte[] keyBytes = Base64.decodeBase64(privateKey);  
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return rsaPrivateKey;
		}
	}
	
	@SuppressWarnings("finally")
	public static byte[] decryptByPrivateKey(byte[] data, String privateKey){
    	if(data == null || data.length == 0){
    		return null;
    	}
		byte[] decryptData = null;
		try{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKey rsaPrivateKey = RSAUtils.getPrivateKey(privateKey);
			Cipher cipher =  Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
			decryptData = cipher.doFinal(data);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return decryptData;
		}
	}
	
	@SuppressWarnings("finally")
	public static byte[] encryptByPublicKey(byte[] data, String publicKey){
    	if(data == null || data.length == 0){
    		return null;
    	}
		byte[] encryptData = null;
		try{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKey rsaPublicKey = RSAUtils.getPublicKey(publicKey);
			Cipher cipher =  Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
			encryptData = cipher.doFinal(data);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return encryptData;
		}
	}
}
