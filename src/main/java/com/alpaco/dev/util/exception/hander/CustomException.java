package com.alpaco.dev.util.exception.hander;

import com.alpaco.dev.util.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.alpaco.dev.util.BaseResponseStatus.EMPTY_REQUEST_PARAMETER;
import static com.alpaco.dev.util.BaseResponseStatus.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
class CustomExceptionHandler {

    // 파라미터가 없는 경우
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public BaseResponse<Object> handleRequestParameter() {
        return new BaseResponse<>(EMPTY_REQUEST_PARAMETER);
    }

    // Not Valid한 경우
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResponse<Object> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        log.info("errors = {}", e.getBindingResult().getFieldError().getDefaultMessage());

        // forEach로 모든 에러 메세지를 던집니다.
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return new BaseResponse<>(INVALID_REQUEST, errors);
    }

}
