package hello.exception.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "404 에러인데... RequestExceptionHandlerResolver 사용")
public class BadRequestException extends RuntimeException{
}
