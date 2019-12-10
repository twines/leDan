package com.twins.lee.utilites;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CodecTool {
    public static String encoder(String value) throws UnsupportedEncodingException {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes());
            byte b[] = md5.digest();

            StringBuffer sb = new StringBuffer("");
            for (int n = 0; n < b.length; n++) {
                int i = b[n];
                if (i < 0) i += 256;
                if (i < 16) sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();  //32位加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
