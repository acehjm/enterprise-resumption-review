package cn.dyoon.review.config.exception;

import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.common.response.Result;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * me.solby.xboot.config.exception
 *
 * @author majhdk
 * @date 2019-07-16
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Result> BusinessExceptionHandler(BusinessException e) {
        return ResponseEntity.status(HttpStatus.OK).body(Result.failure(e.getCode(), e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(Result.failure("400",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .findFirst()
                        .orElse("参数错误"))
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    /**
     * 自定义文件大小超过限制异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Result> MaxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.OK).body(Result.failure("500", "文件大小超过限制，请重新上传"));
    }

}
