package cn.myst.web.rabbitmq.api.exception;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = 8486791714138607444L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
