package com.jyx.oss.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.jyx.oss.ALiOssService;
import com.jyx.oss.constant.CustomConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @ClassName: ALiOssServiceImpl
 * @Description: ALiOssServiceImpl实现类
 * @Author: pengmingming
 * @Date: 2024-03-05 13:47
 * @Version: 1.0
 **/
@Slf4j
@Service
public class ALiOssServiceImpl implements ALiOssService {
    private final OSS ossClient;

    /**
     * 构造注入
     * @param ossClient
     * */
    public ALiOssServiceImpl(OSS ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * 上传文件
     * @param localPathFile 本地文件包含路径
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileName OSS文件名称
     * @throws Exception
     */
    @Override
    public void uploadFile(String localPathFile, String bucketName, String filePath, String fileName) throws Exception {
        debugOnly("入参: {} | {} | {} | {}", localPathFile, bucketName, filePath, fileName);
        if (ossClient.doesBucketExist(bucketName)) {
            ossClient.putObject(bucketName, getObjectName(filePath, fileName),
                                                        new FileInputStream(localPathFile));
            log.info("文件{}上传成功", localPathFile);
        } else {
            String errMsg = formatMsg(CustomConstant.CUSTOM_ALI_OSS_BUCKET_NOT_EXIST, bucketName);
            log.error("errMsg: {}", errMsg);
            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 下载文件
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileName OSS文件名称
     * @param localPathFile 本地文件
     * @throws Exception
     */
    @Override
    public void downloadFile(String bucketName, String filePath, String fileName, String localPathFile) throws Exception {
        debugOnly("入参: {} | {} | {} | {}", bucketName, filePath, fileName, localPathFile);
        String filePathName = getObjectName(filePath, fileName);
        if (ossClient.doesObjectExist(bucketName, filePathName)) {
            int bytesRead;
            OSSObject ossObject = null;
            byte[] buffer = new byte[1024];
            FileOutputStream outputStream = null;
            try {
                ossObject = ossClient.getObject(bucketName, filePathName);
                outputStream = new FileOutputStream(localPathFile, true);
                while ((bytesRead = ossObject.getObjectContent().read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (ossObject != null) {
                    ossObject.close();
                }
            }
            log.info("文件{}下载成功", localPathFile);
        } else {
            String errMsg = formatMsg(CustomConstant.CUSTOM_ALI_OSS_FILE_NOT_EXIST, bucketName, filePathName);
            log.error("errMsg: {}", errMsg);
            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 删除单个文件
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileName OSS文件名称。
     * @throws Exception
     */
    @Override
    public void deleteFile(String bucketName, String filePath, String fileName) throws OSSException, ClientException {
        debugOnly("入参: {} | {} | {}", bucketName, filePath, fileName);
        String filePathName = getObjectName(filePath, fileName);
        if (ossClient.doesObjectExist(bucketName, filePathName)) {
            ossClient.deleteObject(bucketName, filePathName);
            log.info("文件{}删除成功", filePathName);
        } else {
            String errMsg = formatMsg(CustomConstant.CUSTOM_ALI_OSS_FILE_NOT_EXIST, bucketName, filePathName);
            log.error("errMsg: {}", errMsg);
            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 批量删除文件
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileNames OSS文件列表
     @throws Exception
     */
    @Override
    public void deleteFile(String bucketName, String filePath, List<String> fileNames) throws OSSException, ClientException {
        for (String fileName : fileNames) {
            deleteFile(bucketName, filePath, fileName);
        }
    }

    private String getObjectName(String filePath, String fileName) {
        filePath = filePath.trim();
        fileName = fileName.trim();
        if (filePath.startsWith(CustomConstant.CUSTOM_ALI_OSS_BIAS)) {
            filePath = filePath.substring(CustomConstant.CUSTOM_ALI_OSS_BIAS.length());
        }
        if (fileName.startsWith(CustomConstant.CUSTOM_ALI_OSS_BIAS)) {
            fileName = fileName.substring(CustomConstant.CUSTOM_ALI_OSS_BIAS.length());
        }
        if (filePath.endsWith(CustomConstant.CUSTOM_ALI_OSS_BIAS)) {
            return filePath + fileName;
        } else {
            return filePath + CustomConstant.CUSTOM_ALI_OSS_BIAS + fileName;
        }
    }

    private String formatMsg(String format, Object... args) {
        return String.format(format, args);
    }

    private void debugOnly(String format, Object... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(format, arguments);
        }
    }
}
