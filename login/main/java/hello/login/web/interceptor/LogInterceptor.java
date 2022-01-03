package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
public class LogInterceptor implements HandlerInterceptor {


    private static final String LOG_ID = "logId";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID,uuid);

        if (handler instanceof RequestMappingHandlerMapping) {
            HandlerMethod hm = (HandlerMethod) handler;
        }


        log.info("REQUEST 실행 [{}][{}][{}]", uuid, requestURI, handler);
        // 이게 true면 다음으로 진행한다
        // 이게 false면 다음으로 진행하지 않는다.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Post Handle [{}][{}]", request.getAttribute(LOG_ID), modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute(LOG_ID);

        log.info("afterCompletion [{}][{}][{}]",requestURI, uuid, ex);


    }
}
