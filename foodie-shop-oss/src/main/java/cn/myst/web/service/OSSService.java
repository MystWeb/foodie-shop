package cn.myst.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ziming.xing
 * Create Date：2022/3/30
 */
public interface OSSService {

    /**
     * FastDFS 上传
     *
     * @param file        文件流
     * @param fileExtName 文件后缀名
     * @return 完整路径
     */
    String upload(MultipartFile file, String fileExtName) throws IOException;

    /**
     * 阿里云-OSS 上传
     *
     * @param file        文件流
     * @param userId      用户id
     * @param fileExtName 文件后缀名
     * @return 完整路径
     */
    String uploadOSS(MultipartFile file, String userId, String fileExtName) throws IOException;
}
