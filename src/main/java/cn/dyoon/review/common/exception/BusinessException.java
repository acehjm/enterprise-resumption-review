package cn.dyoon.review.common.exception;

/**
 * cn.dyoon.review.common.exception
 *
 * @author majhdk
 * @date 2020/2/6
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
