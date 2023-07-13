package actiOn.exception.advice;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    // 요청 인자의 유효성 검증 실패 시
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    // 필드, 외래 키, db, null 제약 조건 위반할 시
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    // businessLogic 사용자 정의 에러 발생 시
    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response,
                HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }

    // 특정 URL에 HTTP 메서드 요청이 잘못된 경우 등
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        log.error("# handle exception: {}", e.getMessage());

        return response;
    }

    // 클라이언트가 잘못된(혹은 비어있는) 형식의 요청 본문을 전송한 경우
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        final ErrorResponse response = ErrorResponse
                .of(HttpStatus.BAD_REQUEST, "요청/응답 body의 형식이 잘못되었습니다.");
        log.error("# handle exception: {}", e.getMessage());

        return response;
    }

    // 파라미터 누락/형식 오류 등
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());

        return response;
    }

    // 입출력 오류(이미지 업로드 등)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleIOException(IOException e) {
        log.error("# handle IO Exception: {}", e.getMessage());

        return ErrorResponse.of(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }

    // 핸들되지 않는 나머지 예외처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnableHandlingException(Exception e) {
        log.error("# handle Exception", e);
        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }
}
