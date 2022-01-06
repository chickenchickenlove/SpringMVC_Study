package hello.exception.resolver;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {

        log.info("MyHandlerExceptionResolver", ex);

        // 어떤 핸들러면 처리해준다고 보면 되겠네.

        try{
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(400,ex.getMessage());
                return new ModelAndView(); // 이렇게 하면 예외를 먹어버리고, 400 sendError로 보낸다.
            }
        } catch (IOException e) {
            log.error("ex",e);
        }
        return null;
    }
}
