package com.manish.cipher_pro;

import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Algorithm {
    private final static int BUFFER_SIZE = 1024;
    private final static int IV_SIZE = 16;
    private final static String ALGO = "AES/CBC/PKCS5Padding";
    private final static String ALGOKEY = "AES";

    // For Text-Encryption

    public static String encrypt(String string1, String string2, String string3) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        SecretKeySpec secretKeySpec = generateKey(string1);
        IvParameterSpec ivParameterSpec = generateIV(string2);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encOut_Bytes = cipher.doFinal(string3.getBytes("UTF-8"));
        String encString = Base64.encodeToString(encOut_Bytes, Base64.DEFAULT);
        return encString;
    }

    public static String decrypt(String string1, String string2, String string3) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = generateKey(string1);
        IvParameterSpec ivParameterSpec = generateIV(string2);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decOut_Bytes = string3.getBytes("UTF-8");
        byte[] decOut_Bytes1 = Base64.decode(decOut_Bytes, Base64.DEFAULT);
        byte[] decOut_Bytes2 = cipher.doFinal(decOut_Bytes1);
        String decString = new String(decOut_Bytes2, "UTF-8");
        return decString;
    }

    public static SecretKeySpec generateKey(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = password.getBytes("UTF-8");
        digest.update(keyBytes, 0, keyBytes.length);
        byte[] keyBytes1 = digest.digest();
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes1, ALGOKEY);
        return secretKey;
    }

    public static IvParameterSpec generateIV(String ivpassword) throws UnsupportedEncodingException {
        byte[] ivBytes = new byte[IV_SIZE];
        ivBytes = ivpassword.getBytes("UTF-8");
        IvParameterSpec ivParam = new IvParameterSpec(ivBytes);
        return ivParam;
    }

    //    For image,video,audio encryption

    public static void encrypt_files(String string1, String string2, InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKeySpec secretKeySpec = generateKey(string1);
        IvParameterSpec ivParameterSpec = generateIV(string2);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        out = new CipherOutputStream(out, cipher);
        int count = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
    }

    public static void decrypt_files(String string1, String string2, InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKeySpec secretKeySpec = generateKey(string1);
        IvParameterSpec ivParameterSpec = generateIV(string2);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        out = new CipherOutputStream(out, cipher);
        int count = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
    }
}
