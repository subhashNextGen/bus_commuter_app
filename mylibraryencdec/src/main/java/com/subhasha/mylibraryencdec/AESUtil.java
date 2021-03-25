package com.subhasha.mylibraryencdec;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private final static String cipherText = "AES";

    public static String decryptValue(String encriptedVal) throws Exception {
        SecretKeySpec key = generateKey();
        @SuppressLint("GetInstance") Cipher c = Cipher.getInstance(cipherText);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(encriptedVal, Base64.DEFAULT);
        byte[] decVal = c.doFinal(decodedValue);
        return new String(decVal);
    }


    public static String encryptValue(String data) throws Exception {
        Key key = generateKey();
        @SuppressLint("GetInstance") Cipher c = Cipher.getInstance(cipherText);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.encodeToString(encVal, Base64.DEFAULT);
    }

    private static SecretKeySpec generateKey() throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = "0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key, cipherText);
    }
}
