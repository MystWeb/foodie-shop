package cn.myst.web.exception;

/**
 * 文件下载异常
 *
 * @author ziming.xing
 * Create Date：2022/4/13
 */
public class FileDownloadException extends BusinessException {

    private static final long serialVersionUID = 1058120350551877242L;

    public FileDownloadException(String message) {
        super(message);
    }
}
