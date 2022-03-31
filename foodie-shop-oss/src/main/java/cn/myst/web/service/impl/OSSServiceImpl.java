package cn.myst.web.service.impl;

import cn.myst.web.resource.FileResource;
import cn.myst.web.service.OSSService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ziming.xing
 * Create Date：2022/3/30
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OSSServiceImpl implements OSSService {

    private final FastFileStorageClient fastFileStorageClient;
    private final FileResource fileResource;

    @Override
    public String upload(MultipartFile file, String fileExtName) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                file.getSize(),
                fileExtName,
                null
        );
        // 完整路径
        return storePath.getFullPath();
    }

    @Override
    public String uploadOSS(MultipartFile file, String userId, String fileExtName) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(fileResource.getEndpoint(), fileResource.getAccessKeyId(), fileResource.getAccessKeySecret());

        InputStream inputStream = file.getInputStream();
        // 用户文件路径
        String userObjectName = fileResource.getObjectName() + File.separator + userId + File.separator + userId + fileExtName;
        // 创建PutObject请求。
        ossClient.putObject(fileResource.getBucketName(), userObjectName, inputStream);
        ossClient.shutdown();

        return fileResource.getOSSServerUrl() + userObjectName;
    }

}
