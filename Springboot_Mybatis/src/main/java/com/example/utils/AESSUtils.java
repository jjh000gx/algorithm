package com.example.utils;


import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESSUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);

    private final static String CHARSET = "utf-8";

    public static String encryptBase64(String seed, String content) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
            byte[] result = encrypt(rawKey, content.getBytes(CHARSET));
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            LOGGER.error("AES加密base64编码出错", e);
            throw new RuntimeException(e);
        }
    }

    public static String decryptBase64(String seed, String encrypted) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
            byte[] enc = Base64.decodeBase64(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result, CHARSET);
        } catch (Exception e) {
            LOGGER.error("AES解密base64出错", e);
            throw new RuntimeException(e);
        }
    }

    private static byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES"); // AES/CBC/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES"); // AES/CBC/PKCS5Padding
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


    /**
     * 去哪儿加密过程说明：由于AES加密时，对密钥长度有要求，本加密方法中，为了不限制业务上使用的密钥的长度，因此使用了MD5的方式
     * 将密钥转换成了定长(16字节)的字节数组,此处没有使用JDK的SecureRandom来生成随机种子对进而初始化密码器是因为SecureRandom
     * 具有跨平台的bug，因为它的随机值依赖于操作系统，因此可能会导致在A平台加密成功，但在B平台解密失败的问题，故此处未采用此
     * 随机数生成器，而是使用MD5签名得到的字节数组作为实际密钥来生成密码器。另一方面，由于AES加密后的密文实质上是一个byte数组，
     * byte数组不方便在Http中传输。因此本方法将此byte格式的密文数组转换成了String类型字符串，但因为这个byte数组的位数不一定刚
     * 好可以组成一个String串，因此又使用了Base64算法对此byte密文进行了编码，使之能够正确的转换为String类型进行传输。解密过程
     * 则是此过程的逆过程。上述代码是Java代码，依赖了Apache的Base64工具类。如果贵公司使用Java开发语言，可以直接使用上述代码，
     * 否则请注意上述密钥签名、加密、编码、解码、解密的过程。
     */

    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final String KEY_MD5 = "MD5";
    private static MessageDigest md5Digest;

    static {
        try {
            md5Digest = MessageDigest.getInstance(KEY_MD5);
        } catch (NoSuchAlgorithmException e) {
            //
        }
    }

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String data, String key) {
        return doAES(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        return doAES(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     *
     * @param data
     * @param key
     * @param mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (StringUtils.isBlank(data) || StringUtils.isBlank(key)) {
                return null;
            }
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = Base64.decodeBase64(data);
            }
            SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(defaultCharset)), KEY_AES);
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                return Base64.encodeBase64String(result);
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
            LOGGER.error("AES密文处理异常", e);
        }
        return "";
    }
}
