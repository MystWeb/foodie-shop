package cn.myst.web.exception;

import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2021/7/4
 */
@Data
public class BusinessException extends RuntimeException {

    //自定义错误码
    private Integer code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    private static final long serialVersionUID = 5541764473705476002L;
}
