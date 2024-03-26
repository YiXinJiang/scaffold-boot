package com.jyx.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RSAUtil
 * @Description: RSA加解密工具类
 * @Author: zls
 * @Date: 2024-03-05 15:20
 * @Version: 1.0
 **/
@Slf4j
public class RSAUtil {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String RSA_PRIVATE_KEY = "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";
    //可做成接口获取
    private static final String RSA_PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAuia4PRKM4DiYUH5PyL9cyBYWzlH8zJhCYrCU+m75A+oxUPq9xewOc8n886Vt8aDML3Jjf9AER16r4mrsAYncQiwU9rYvU3DFXXBHH8SsQZT401Gd3ia+yrHSZXIwF2QWmiE5EN8UWMQWYmhTzDH+2PvxiIKwJrrdO38JS2x0h2NMUdnzp+Hd3yGq1BC0Ol8+Ro91jzCnKMRGWH1a1mEWNQzU7vi6J6a+K3KL4N1/Xt1mjNk2Tn1xHKgxl2OeV9luxm+mKbDkCCd2A55Vgcc2cQQnCCwg/Mdp7tAURH6xvuCf9YrEYn0hgLA8/HB7y7Duuf5KrCxOQt5TlBZYHktIiiS3NO5F/TFG1g1tUv9BHOSl8S6Vz3x+rSyQJzNxYuUSCWvdKpXsS+3FykhzpfuTz3eVmHU8ZA9k8/uWpg9Gd38s4PmoILdg9fbEuBitsDRtGpwsIpVnNpv69EuBh5M5C69PnQu0KPmRMf0SopmgcZ+3aBj/kcpyHTehQ2Son/MCi70jm94K7c58sEYbbvOSVmVF+oZKn6UDdPk96DtjVEBAsXVfH0pTn5m9t810RqiqzGg0c/RXFU/oizHcABMgVNONbRK753Pykm7av/MM+0y+UTn5MkkG6nsR1q2HGKCGs+Vf50y+BTy8Zdclrbhj2CjRuEk/1HuPsgUQ2zNDcKsCAwEAAQ==";
    //密钥长度
    private static final int KEY_SIZE = 4096;
    //最大加密长度
    private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;
    //最大解密长度
    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;

    private static byte[] decryptBase64(String src) {
        return Base64.getDecoder().decode(src);
    }

    /**
     * 生成公私钥
     *
     * @param keySize 密钥长度
     */
    public static Map<String, String> genKeyPair(int keySize) {
        Map<String, String> keyMap = new HashMap<>();
        try {
            //创建密钥对生成器
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keySize);
            //生成密钥对
            KeyPair keyPair = kpg.generateKeyPair();
            //公钥
            PublicKey publicKey = keyPair.getPublic();
            //私钥
            PrivateKey privateKey = keyPair.getPrivate();
            keyMap.put("publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            keyMap.put("privateKey", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("生成公私钥信息失败：" + e.getMessage());
        }
        return keyMap;
    }


    /**
     * 该base64方法会自动换行
     *
     * @param src 源数据
     * @return 返回base64编码字符串
     */
    private static String encryptBase64(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    /**
     * 公钥分段加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return byte[]
     */
    private static byte[] encryptByPublicKey(byte[] data, String publicKey) {
        ByteArrayOutputStream out = null;
        byte[] encryptData = null;
        try {
            byte[] keyBytes = decryptBase64(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > RSAUtil.MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, RSAUtil.MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * RSAUtil.MAX_ENCRYPT_BLOCK;
            }
            encryptData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("公钥分段加密失败：" + e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encryptData;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return byte[]
     */
    private static byte[] decryptByPrivateKey(byte[] data, String privateKey) {
        ByteArrayOutputStream out = null;
        byte[] decryptedData = null;
        try {
            byte[] keyBytes = decryptBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > RSAUtil.MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, RSAUtil.MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * RSAUtil.MAX_DECRYPT_BLOCK;
            }
            decryptedData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("私钥解密失败：" + e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return decryptedData;
    }

    /**
     * 加密
     *
     * @param data 原文
     * @param key 密钥
     * @return 密文
     */
    public static String encryptByPublicKey(String data, String key) {
        //log.info("公钥分段加密内容："+new String(encryptByPublicKey(data.getBytes(), key, MAX_ENCRYPT_BLOCK)).replaceAll("[+]", "@"));
        return encryptBase64(encryptByPublicKey(data.getBytes(), key)).replaceAll("[+]", "@");
    }

    /**
     * 解密
     *
     * @param data 密文
     * @param key 密钥
     * @return 原文
     */
    public static String decryptByPrivateKey(String data, String key) {
        //log.info("私钥解密内容："+new String(decryptByPrivateKey(decryptBASE64(data.replaceAll("@", "+")), getRsaKey(), MAX_DECRYPT_BLOCK)));
        return new String(decryptByPrivateKey(decryptBase64(data.replaceAll("@", "+")), key));
    }

    public static String decryptByPrivateKey(String data) {
        return decryptByPrivateKey(data, RSA_PRIVATE_KEY);
    }

    public static void main(String[] args) {
        Map<String, String> keyMap = genKeyPair(KEY_SIZE);
        System.out.println("公钥======" + keyMap.get("publicKey"));
        System.out.println("私钥======" + keyMap.get("privateKey"));
        String encryptString = encryptByPublicKey("{\\\"allBillList\\\":[{\\\"id\\\":\\\"264786\\\",\\\"billNo\\\":\\\"WB2021051700064\\\",\\\"billTypeId\\\":13,\\\"billTypeName\\\":\\\"定损维修\\\",\\\"vehicleNo\\\":\\\"京Q683976\\\",\\\"vehicleId\\\":\\\"532928\\\",\\\"vehicleOwner\\\":\\\"北京测试公司\\\",\\\"vehicleModelName\\\":\\\"宝马\\\",\\\"orderCar\\\":0,\\\"orderCarName\\\":\\\"短租\\\",\\\"cityId\\\":null,\\\"cityName\\\":\\\"北京\\\",\\\"deptId\\\":null,\\\"deptName\\\":\\\"北京测试\\\",\\\"nowCityId\\\":null,\\\"nowCityName\\\":null,\\\"nowDeptId\\\":null,\\\"nowDeptName\\\":null,\\\"belongCityId\\\":null,\\\"belongCityName\\\":\\\"北京\\\",\\\"belongDeptId\\\":null,\\\"belongDeptName\\\":\\\"知春路店\\\",\\\"costBelongAreaName\\\":\\\"知春路大片区\\\",\\\"costBelongCityName\\\":\\\"北京\\\",\\\"costBelongDeptName\\\":\\\"知春路店\\\",\\\"createTime\\\":\\\"2021-05-17 10:11\\\",\\\"modifyTime\\\":null,\\\"billStatusId\\\":109,\\\"billStatusName\\\":\\\"方案已通过\\\",\\\"createEmpName\\\":\\\"JD\\\",\\\"createEmpDeptName\\\":\\\"北京测试\\\",\\\"pickupType\\\":2,\\\"pickupTypeName\\\":\\\"上门\\\",\\\"isDelete\\\":0,\\\"isDeleteeName\\\":\\\"否\\\",\\\"garageId\\\":null}],\\\"totalCount\\\":1}\";\n",
                RSA_PUBLIC_KEY);
        System.out.println("密文======" + encryptString);
        String decryptString = decryptByPrivateKey(encryptString, RSA_PRIVATE_KEY);
        System.out.println("明文======" + decryptString);
    }
}



