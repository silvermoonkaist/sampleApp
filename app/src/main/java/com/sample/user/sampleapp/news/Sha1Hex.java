package com.sample.user.sampleapp.news;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Hex {

    public static String makeSHA1Hash(String input)
        {
            MessageDigest md = null;
            String hexStr = "";
            try {
                md = MessageDigest.getInstance("SHA1");
                md.reset();
                byte[] buffer = new byte[0];
                buffer = input.getBytes("UTF-8");
                md.update(buffer);
                byte[] digest = md.digest();

                for (int i = 0; i < digest.length; i++) {
                    hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return hexStr;
        }
}