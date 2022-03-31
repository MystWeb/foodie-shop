package cn.myst.web.service.impl;

import cn.myst.web.service.OSSService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ziming.xing
 * Create Date：2022/3/30
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OSSServiceImpl implements OSSService {

    private final FastFileStorageClient fastFileStorageClient;

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
}
