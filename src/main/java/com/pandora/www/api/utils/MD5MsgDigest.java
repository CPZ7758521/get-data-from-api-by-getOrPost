package com.pandora.www.api.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5MsgDigest {
    public static String digest(String rawString) {
        return digest(rawString, "UTF-8");
    }

    public static String digest(String rawString, String charset) {
        Charset cs = Charset.forName(charset);

        try {
            return compute(rawString, cs);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String compute(String inStr, Charset charset) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md5.digest(inStr.getBytes(charset));

        return toHexString(md5Bytes);
    }

    public static String toHexString(byte[] bytes) {
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            int val = ((int)bytes[i]) & 0xff;

            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();

    }
}
