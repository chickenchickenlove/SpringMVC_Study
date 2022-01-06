package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {


    private final static ObjectMapper objectMapper = new ObjectMapper();


    //직접 API로 반환하기.
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {



        try {
            if (ex instanceof UserException) {
                log.info("UserHandlerExceptionResolver resolve 400");

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 정상으로 가는거지.
                String acceptHeader = (String) request.getHeader("accept");

                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("message", ex.getMessage());
                    errorResult.put("exception", ex.getClass());

                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json");
                    response.getWriter().write(result);

                    return new ModelAndView();

                } else {
                    // html/text
                    return new ModelAndView("error/500");
                }
            }
        }
        catch(Exception e){
            log.error("ex",e );
        }

        return null;
    }
}
