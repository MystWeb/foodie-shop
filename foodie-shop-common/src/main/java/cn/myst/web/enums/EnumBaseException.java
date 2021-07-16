package cn.myst.web.enums;

import org.springframework.http.HttpStatus;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 异常 枚举
 */
public enum EnumBaseException {
    INCORRECT_REQUEST_PARAMETER("请求参数有误", "INCORRECT_REQUEST_PARAMETER", HttpStatus.BAD_REQUEST.value()),
    FILE_CANNOT_BE_EMPTY("文件不能为空", "FILE_CANNOT_BE_EMPTY", HttpStatus.INTERNAL_SERVER_ERROR.value()),

    ;

    public final String zh;
    public final String en;
    public final Integer code;

    EnumBaseException(String zh, String en, Integer code) {
        this.zh = zh;
        this.en = en;
        this.code = code;
    }
}
