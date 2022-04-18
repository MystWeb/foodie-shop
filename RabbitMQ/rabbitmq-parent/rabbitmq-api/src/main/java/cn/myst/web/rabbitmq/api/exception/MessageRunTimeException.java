package cn.myst.web.rabbitmq.api.exception;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public class MessageRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -2965278412943524294L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
