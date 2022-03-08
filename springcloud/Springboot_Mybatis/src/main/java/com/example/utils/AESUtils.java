package com.example.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;


/**
 * 
 * 
 * @author suzhiyong
 * @version $Id: AESUtils.java, v 0.1 2021年3月30日 上午9:58:56 suzhiyong Exp $
 */
@Slf4j
public class AESUtils {

    private static final byte[] INIT_VECTOR = {0x31, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38, 0x27, 0x36, 0x35, 0x33, 0x23, 0x32, 0x33};

    private static final String AESTYPE = "AES/ECB/PKCS5Padding";
    private static final String AESTYPE_TTL = "AES/CBC/PKCS5Padding";

    /**
     * Encrypt the content with a given key using aes algorithm.
     *
     * @param content
     * @param apiKey  must contain exactly 32 characters
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String apiKey) throws Exception {
        return encrypt(content, apiKey, INIT_VECTOR);
    }

    /**
     * Encrypt the content with a given key using aes algorithm.
     *
     * @param content
     * @param apiKey  must contain exactly 32 characters
     * @param iv      iv
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String apiKey, byte[] iv) throws Exception {
        if (apiKey == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        String encrypted = null;
        byte[] keyBytes = apiKey.getBytes();
        int length1 = 32;
        int length2 = 24;
        int length3 = 16;
        if (keyBytes.length != length1 && keyBytes.length != length2 && keyBytes.length != length3) {
            throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
        }
        byte[] encryptedBytes = null;
        encryptedBytes = encrypt(content.getBytes(), keyBytes, iv);
        encrypted = new String(Hex.encode(encryptedBytes));
        return encrypted;
    }

    /**
     * Decrypt the content with a given key using aes algorithm.
     *
     * @param content
     * @param apiKey  must contain exactly 32 characters
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String apiKey) throws Exception {
        if (apiKey == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        String decrypted = null;
        byte[] encryptedContent = Hex.decode(content);
        byte[] keyBytes = apiKey.getBytes();
        byte[] decryptedBytes = null;
        int length1 = 32;
        int length2 = 24;
        int length3 = 16;
        if (keyBytes.length != length1 && keyBytes.length != length2 && keyBytes.length != length3) {
            throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
        }
        decryptedBytes = decrypt(encryptedContent, keyBytes, INIT_VECTOR);
        decrypted = new String(decryptedBytes);
        return decrypted;
    }

    /**
     * Encrypt data.
     *
     * @param plain
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), INIT_VECTOR);
        aes.init(true, ivAndKey);
        return cipherData(aes, plain);
    }

    /**
     * Decrypt data.
     *
     * @param cipher
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private static byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(false, ivAndKey);
        return cipherData(aes, cipher);
    }

    /**
     * Encrypt or decrypt data.
     *
     * @param cipher
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws Exception {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] result = new byte[actualLength];
        System.arraycopy(outBuf, 0, result, 0, result.length);
        return result;
    }

    /**
     * aes加密通配php
     *
     * @param keyStr
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String keyStr, String plainText) throws Exception {
        byte[] encrypt = null;
        Key key = generateKey(keyStr);
        Cipher cipher = Cipher.getInstance(AESTYPE);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encrypt = cipher.doFinal(plainText.getBytes());
        return parseByte2HexStr(encrypt);
    }

    /**
     * aes解密通配php
     *
     * @param keyStr
     * @param encryptData
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String keyStr, String encryptData) throws Exception {
        byte[] decrypt = null;
        Key key = generateKey(keyStr);
        Cipher cipher = Cipher.getInstance(AESTYPE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        decrypt = cipher.doFinal(parseHexStr2Byte(encryptData));
        return new String(decrypt).trim();
    }

    /**
     * @param key
     * @return
     * @throws Exception
     */
    private static Key generateKey(String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        return keySpec;
    }

    /**
     * 2进制装16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 16进制转2进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        int div = 2;
        byte[] result = new byte[hexStr.length() / div];
        for (int i = 0; i < hexStr.length() / div; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * this method is used for sz run, with base64
     *
     * @param input
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptWithBase64(String input, String key) throws Exception {
        byte[] crypted = null;
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(AESTYPE);
        cipher.init(Cipher.ENCRYPT_MODE, skey);
        crypted = cipher.doFinal(input.getBytes());
        return new String(Base64.encode(crypted));
    }

    /**
     * this method is used for sz run, with base64
     *
     * @param input
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptWithBase64(String input, String key) throws Exception {
        byte[] output = null;
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(AESTYPE);
        cipher.init(Cipher.DECRYPT_MODE, skey);
        output = cipher.doFinal(Base64.decode(input.getBytes()));
        return new String(output);
    }

    /**
     * 小程序AES解密，IvParameter需要128bit全零的IV向量
     *
     * @param content
     * @param key
     * @param charset
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String key, String charset) throws Exception {

        //反序列化AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(key.getBytes()), "AES");

        //128bit全零的IV向量
        byte[] iv = new byte[16];
        //        for (int i = 0; i < iv.length; i++) {
        //            iv[i] = 0;
        //        }
        //        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        new SecureRandom().nextBytes(iv);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        //初始化加密器并加密
        Cipher deCipher = Cipher.getInstance(AESTYPE_TTL);
        deCipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = Base64.decode(content.getBytes());
        byte[] bytes = deCipher.doFinal(encryptedBytes);
        return new String(bytes);

    }

    /**
     * AES/CBC/256加密
     * base64转义返回
     */
    public static String encryptByAesCbc256(String input, String key, byte[] iv) throws Exception {
        byte[] encData = encryptAesCbc256(input, key, iv);
        return new String(Base64.encode(encData));
    }

    /**
     * AES/CBC/256 解密
     * base64转义返回
     */
    public static String decryptByAesCbc256(String input, String key, byte[] iv) throws Exception {
        return decryptAesCbc256(Base64.decode(input), key, iv);
    }

    /**
     * AES/CBC/256 加密
     * 16进制返回
     */
    public static String encryptByAesCbc256Hex(String input, String key, String ivStr) throws Exception {
        byte[] encData = encryptAesCbc256(input, key, ivStr.getBytes());
        return parseByte2HexStr(encData);
    }

    /**
     * AES/CBC/256 解密
     * 16进制返回
     */
    public static String decryptByAesCbc256Hex(String input, String key, String ivStr) throws Exception {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        return decryptAesCbc256(parseHexStr2Byte(input), key, ivStr.getBytes());
    }

    /**
     * AES/CBC/256  加密
     */
    private static byte[] encryptAesCbc256(String input, String key, byte[] iv) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(AESTYPE_TTL);
        cipher.init(Cipher.ENCRYPT_MODE, skey, new IvParameterSpec(iv));
        return cipher.doFinal(input.getBytes());
    }

    /**
     * AES/CBC/256 解密
     */
    private static String decryptAesCbc256(byte[] input, String key, byte[] iv) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(AESTYPE_TTL);
        cipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv));
        byte[] encData = cipher.doFinal(input);
        return new String(encData);
    }

    public static void main(String[] args) {
        String  tests = "";
        String secretKey = "dPIX8Bk19k6X3L6tYdtEkgHa2WUWmZkr";
        String iv = "glhUaBvwcHNvHUds";
        try {
            /*String sensPolicyNo = "7Wzsjcyiz/lT0hJFwByIXfg5NTy3NsPqV6M5g3ApbDM=";
            String policyNo = AESUtils.decryptByAesCbc256(sensPolicyNo, secretKey, iv.getBytes("utf-8"));
            log.info("decrypt policyNo:{}", policyNo);*/

            String ttt = AESUtils.encryptByAesCbc256("PI06523211000388523957", secretKey, iv.getBytes());
            log.info(ttt);

        } catch (Exception e) {
            log.error("", e);
        }
    }
}
