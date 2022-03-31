package cn.myst.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ziming.xing
 * Create Date：2022/3/30
 */
public interface OSSService {

    /**
     * @param file        文件流
     * @param fileExtName 文件后缀名
     * @return 完整路径
     */
    String upload(MultipartFile file, String fileExtName) throws IOException;
}
