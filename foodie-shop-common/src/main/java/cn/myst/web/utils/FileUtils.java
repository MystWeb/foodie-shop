package cn.myst.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ziming.xing
 * Create Date：2021/7/19
 */
@Slf4j
public class FileUtils {
    public static final String ALLOW_UPLOAD_FILE_MIME_TYPES = "image/jpg|image/jpeg|image/gif|image/png";

    public static final String ALLOW_UPLOAD_EXCEL_FILE_MIME_TYPES = "application/vnd.ms-excel|application/zip|application/x-tika-ooxml|application/x-tika-msoffice";

    public static final String TIKA_METADATA_KEYS_RESOURCE_NAME_KEY = "resourceName";

    private FileUtils() {
    }

    /**
     * 检测文件的真实类型，是否是文档
     */
    public static String detectDocumentMimeType(MultipartFile file) {
        Tika tika = new Tika();
        String format = null;
        try {
            format = tika.detect(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 校验上传的文件类型
        if (StringUtils.isBlank(format) || !format.matches(ALLOW_UPLOAD_EXCEL_FILE_MIME_TYPES)) {
            return null;
        }
        return format;
    }

    /**
     * 获取类型
     */
    public static String getMimeType(MultipartFile file) {
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<>());
        Metadata metadata = new Metadata();
        metadata.add(TIKA_METADATA_KEYS_RESOURCE_NAME_KEY, file.getName());
        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }

    /**
     * 判断是否是图片
     */
    public static boolean isImage(MultipartFile file) {
        String type = getMimeType(file);
        log.debug(type);
        //对比对应的文件类型的mime就好了至于不知道对应的是什么的话就百度,百度一定会知道
        Pattern pattern = Pattern.compile("image/.*");
        Matcher m = pattern.matcher(type);
        return m.matches();
    }
}
