package cn.dyoon.review.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * cn.dyoon.review.util
 *
 * @author majhdk
 * @date 2020/2/8
 */
public class AESUtil {
    /**
     * Key必须为16位长度
     */
    private static final String KEY_AES = "system%pwd@7602&";
    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";

    private AESUtil() {
    }

    public static String encrypt(String src) throws Exception {
        byte[] raw = KEY_AES.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Hex.encodeHexString(cipher.doFinal(src.getBytes()));
    }

    public static String decrypt(String src) throws Exception {
        byte[] raw = KEY_AES.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] original = cipher.doFinal(Hex.decodeHex(src));
        return new String(original);
    }

}