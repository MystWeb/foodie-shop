package cn.myst.web.rabbitmq.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@AllArgsConstructor
@Getter
public enum EnumMessageException {

    THIS_TOPIC_IS_NULL("空的消息主题", "This topic is empty", HttpStatus.BAD_REQUEST.value()),
    ;

    public final String zh;
    public final String en;
    public final Integer code;
}
