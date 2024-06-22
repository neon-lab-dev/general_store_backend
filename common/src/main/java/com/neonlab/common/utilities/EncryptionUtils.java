package com.neonlab.common.utilities;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtils {

    public static final String HMAC_SHA256 = "HmacSHA256";

    public static String encryptUUID(String uuid) throws Exception {
        // Create AES cipher
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(generateAESKey().getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(uuid.getBytes());

        // Encode encrypted bytes to Base64
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // 128-bit key
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String getBase64Token(String username, String password){
        var str = String.format("%s:%s", username, password);
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String getHmacSignature(String key, String message, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        var secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        var mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return encodeHexString(mac.doFinal(message.getBytes()));
    }

    public static String encodeHexString(byte[] byteArray) {
        StringBuilder hexStringBuffer = new StringBuilder();
        for (byte b : byteArray) {
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

}
