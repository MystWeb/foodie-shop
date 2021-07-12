package cn.myst.web.enums;

import org.springframework.http.HttpStatus;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 异常 枚举
 */
public enum EnumException {
    INCORRECT_REQUEST_PARAMETER("请求参数有误", "INCORRECT_REQUEST_PARAMETER", HttpStatus.BAD_REQUEST.value()),

    ;

    public final String zh;
    public final String en;
    public final Integer code;

    EnumException(String zh, String en, Integer code) {
        this.zh = zh;
        this.en = en;
        this.code = code;
    }
}
