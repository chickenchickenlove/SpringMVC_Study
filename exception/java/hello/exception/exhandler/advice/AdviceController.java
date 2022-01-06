package hello.exception.exhandler.advice;


import hello.exception.api.ApiExceptionControllerV2;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = ApiExceptionControllerV2.class)
public class AdviceController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult argumetnExceptionResolver(IllegalArgumentException e) {
        log.info("runtimeEx resolver!");
        return new ErrorResult("BAD", e.getMessage());
    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> userExceptionResolver(UserException e) {
        ErrorResult result = new ErrorResult("NOT_FOUND", "찾을 수 없는 조치입니다. ");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult allExceptionResolver(Exception e) {
        return new ErrorResult("EX", "서버 내부 오류입니다. 신경 쓰지 마세요! ");
    }


}
