package com.zyh.toolslibrary.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static void main(String[] args) {
		System.out.println(md5(md5("admin")));
	}
	
	public final static String md5(String s) {
		  char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				  'a', 'b', 'c', 'd', 'e', 'f' };
		  try {
		   byte[] strTemp = s.getBytes();
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		   mdTemp.update(strTemp);
		   byte[] md = mdTemp.digest();
		   int j = md.length;
		   char str[] = new char[j * 2];
		   int k = 0;
		   for (int i = 0; i < j; i++) {
		    byte b = md[i];
		   
		    str[k++] = hexDigits[b >> 4 & 0xf];
		    str[k++] = hexDigits[b & 0xf];
		   }
		   return new String(str);
		  } catch (Exception e) {return null;}
	 }
	
	public static String md5_default(String str) throws NoSuchAlgorithmException {  
      String encode = str;
      StringBuilder stringbuilder = new StringBuilder();  
      MessageDigest md5 = MessageDigest.getInstance("MD5");  
      md5.update(encode.getBytes());
      byte[] str_encoded = md5.digest();  
      for (int i = 0; i < str_encoded.length; i++) {  
          if ((str_encoded[i] & 0xff) < 0x10) {  
              stringbuilder.append("0");  
          }  
          stringbuilder.append(Long.toString(str_encoded[i] & 0xff, 16));  
         }  
         return stringbuilder.toString();  
      } 
}

