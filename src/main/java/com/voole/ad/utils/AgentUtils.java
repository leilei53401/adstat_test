package com.voole.ad.utils;

import java.security.MessageDigest;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.techcenter.util.MD5;

public class AgentUtils {

	public static String md5Encode(String text, String secretKey)
	  {
	    String text_secretKey = text + secretKey;
	    String textSecrect = new MD5().getMD5ofStr(text_secretKey);
	    return textSecrect;
	  }
	  
	  public static String guva32BitMd5Encode(String text, String secretKey)
	  {
	    String text_secretKey = text + secretKey;
	    HashCode hashCode = Hashing.md5().hashString(text_secretKey, Charsets.UTF_8);
	    String md5 = hashCode.toString();
	    return md5;
	  }
	  
	  public static String toMD5(String code) {
			try {
				byte[] source = code.getBytes("utf-8");
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(source);
				StringBuffer buf = new StringBuffer();
				for (byte b : md.digest())
					buf.append(String.format("%02x", b & 0xff));
				return buf.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
}
