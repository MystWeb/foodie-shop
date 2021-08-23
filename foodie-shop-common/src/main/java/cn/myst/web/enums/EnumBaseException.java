package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 异常 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumBaseException {
    INCORRECT_REQUEST_PARAMETER("请求参数有误", "INCORRECT_REQUEST_PARAMETER", HttpStatus.BAD_REQUEST.value()),
    FILE_CANNOT_BE_EMPTY("文件不能为空", "FILE_CANNOT_BE_EMPTY", HttpStatus.BAD_REQUEST.value()),
    PICTURE_FORMAT_ERROR("图片格式错误", "PICTURE_FORMAT_ERROR", HttpStatus.BAD_REQUEST.value()),
    FILE_UPLOAD_SIZE_ERROR("文件上传大小不能超过500KB", "FILE_UPLOAD_SIZE_ERROR", HttpStatus.BAD_REQUEST.value()),
    PLEASE_LOGIN("请登录", "PLEASE_LOGIN", HttpStatus.BAD_REQUEST.value()),
    REMOTE_LOGIN("账号在异地登录", "REMOTE_LOGIN", HttpStatus.BAD_REQUEST.value()),
    ;

    public final String zh;
    public final String en;
    public final Integer code;

}
