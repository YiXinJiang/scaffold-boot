package com.jyx.oss;

import java.util.List;

/**
 * @InterfaceName: ALiOssService
 * @Description: 阿里Oss服务接口类
 * @Author: pengmingming
 * @Date: 2024-03-05 13:44
 * @Version: 1.0
 **/
public interface ALiOssService {
    /**
     * 上传文件
     * @param localPathFile 本地文件包含路径
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileName OSS文件名称
     * @throws Exception
     */
    void uploadFile(String localPathFile, String bucketName, String filePath, String fileName) throws Exception;

    /**
     * 下载文件
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileName OSS文件名称
     * @param localPathFile 本地文件
     * @throws Exception
     */
    void downloadFile(String bucketName, String filePath, String fileName, String localPathFile) throws Exception;

    /**
     * 删除单个文件
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileName OSS文件名称。
     * @throws Exception
     */
    void deleteFile(String bucketName, String filePath, String fileName) throws Exception;

    /**
     * 批量删除文件
     * @param bucketName OSS文件存储桶名称
     * @param filePath OSS文件路径
     * @param fileNames OSS文件列表
     * @throws Exception
     */
    void deleteFile(String bucketName, String filePath, List<String> fileNames) throws Exception;
}
