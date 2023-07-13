package actiOn.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    @Getter
    private actiOn.exception.ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
