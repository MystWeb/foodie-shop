package cn.myst.web.exception;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author ziming.xing
 * Create Date：2021/7/19
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    // 上传文件大小超过500KB，捕获异常：MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {
        return IMOOCJSONResult.errorMsg(EnumBaseException.FILE_UPLOAD_SIZE_ERROR.zh);
    }
}
