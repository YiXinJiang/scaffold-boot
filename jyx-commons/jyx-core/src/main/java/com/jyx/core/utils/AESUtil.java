package com.jyx.core.utils;


import org.apache.commons.lang3.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

/**
 * @ClassName: AESUtil
 * @Description: AES加解密工具类
 * @Author: zls
 * @Date: 2024-03-05 15:07
 * @Version: 1.0
 **/
public class AESUtil {
    public static final String KEY_DES = "pzy0123456789pzy";

    /**
     * AES加密文本
     *
     * @param content    明文
     * @param encryptKey 秘钥，必须为16个字符组成
     * @return 密文
     */
    public static String aesEncryptForFront(String content, String encryptKey) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(encryptKey)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

            byte[] encryptStr = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptStr);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密文本
     *
     * @param encryptStr 密文
     * @param decryptKey 秘钥，必须为16个字符组成
     * @return 明文
     */
    public static String aesDecryptForFront(String encryptStr, String decryptKey) {
        if (StringUtils.isEmpty(encryptStr) || StringUtils.isEmpty(decryptKey)) {
            return null;
        }
        try {
            byte[] encryptByte = Base64.getDecoder().decode(encryptStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptByte);
            return new String(decryptBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES加密文件
     *
     * @param bytes      字节
     * @param encryptKey 秘钥key
     * @return 字节byte
     */
    public static byte[] aesEncryptForFront(byte[] bytes, String encryptKey) {
        if (ObjectUtils.isEmpty(bytes) || StringUtils.isEmpty(encryptKey)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密文件
     *
     * @param bytes      字节
     * @param decryptKey 解密key
     * @return 解密字节
     */
    public static byte[] aesDecryptForFront(byte[] bytes, String decryptKey) {
        if (ObjectUtils.isEmpty(bytes) || StringUtils.isEmpty(decryptKey)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            return cipher.doFinal(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件AES加密并输出
     *
     * @param fileSourcePath 文件原始路径
     * @param outEncryptPath 加密数据文件路径
     * @throws IOException io异常
     */
    public static void fileAcsEncrypt(String fileSourcePath, String outEncryptPath) throws IOException {

        System.out.println("加密开始!");

        File file = new File(fileSourcePath);
        // 以 byte 的形式读取，不改变文件数据的编码格式
        byte[] bytes = Files.readAllBytes(file.toPath());

        byte[] outFile = aesEncryptForFront(bytes, KEY_DES);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(outEncryptPath));
        assert outFile != null;
        out.write(outFile);
        out.flush();
    }

    /**
     * 文件AES解密并输出
     *
     * @param fileEncryptPath 加密后文件路径
     * @param outDecryptPath  解密后文件路径
     * @throws IOException io异常
     */
    public static void fileAcsDecrypt(String fileEncryptPath, String outDecryptPath) throws IOException {
        File file = new File(fileEncryptPath);
        // 以 byte 的形式读取，不改变文件数据的编码格式
        byte[] outFile = aesDecryptForFront(Files.readAllBytes(file.toPath()), KEY_DES);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(outDecryptPath));
        assert outFile != null;
        out.write(outFile);
        out.flush();

        System.out.println("解密结束!");
    }

    public static void main(String[] args) throws Exception {
        String old = "12345678";
        String target = "9QCu2fu3CbxBlkbivKA2fA=="; // 加密后的字符串
        //加密
        String a = aesEncryptForFront(old, KEY_DES);
        System.out.println(a);
        //解密
        String b = aesDecryptForFront(target, KEY_DES);
        System.out.println(b);

        //加密/解密(生成加密后的文件后缀可以自定义)
        fileAcsEncrypt("D:\\pzy.txt", "D:\\pzy1.txt");
        fileAcsDecrypt("D:\\pzy1.txt", "D:\\pzy2.txt");
    }

}



