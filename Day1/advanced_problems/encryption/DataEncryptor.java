package org.example.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DataEncryptor {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static SecretKey secretKey;

    static {
        // In a real application, DO NOT hardcode the key.
        // Use a secure key management mechanism.
        String hardcodedKey = "ThisIsASecretKeyForAESEncryption";
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // You might need to adjust key size based on JCE policy
            secretKey = generateKeyFromSeed(hardcodedKey.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static SecretKey generateKeyFromSeed(byte[] seed) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed);
        keyGenerator.init(256, secureRandom);
        return keyGenerator.generateKey();
    }

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecureRandom random = new SecureRandom();
            byte[] ivBytes = new byte[16];
            random.nextBytes(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            // Combine IV and ciphertext for decryption
            byte[] combined = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, combined, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, combined, ivBytes.length, encryptedBytes.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedData);
            byte[] ivBytes = new byte[16];
            byte[] encryptedBytes = new byte[combined.length - ivBytes.length];
            System.arraycopy(combined, 0, ivBytes, 0, ivBytes.length);
            System.arraycopy(combined, ivBytes.length, encryptedBytes, 0, encryptedBytes.length);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}