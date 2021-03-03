package com.aait.oms.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordBecrypt {
/*    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "AES";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;*/

    String AES = "AES";
    SecretKey key;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String username ,String password) throws Exception {
        String encryptedString = null;
        try {
            SecretKeySpec key = generetKey(password);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] value= cipher.doFinal(username.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
             encryptedString = encoder.encodeToString(value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String becrypt(String username,String password) throws Exception {
        String becryptedString = null;
        try {
            SecretKeySpec key = generetKey(password);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE,key);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytesvalue = decoder.decode(username);
            byte[] value= cipher.doFinal(bytesvalue);
             becryptedString = new String(value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return becryptedString;
    }

    private SecretKeySpec generetKey(String password) throws Exception {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] key = messageDigest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;


    }


}
