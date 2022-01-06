package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static javax.servlet.RequestDispatcher.*;

@Controller
@Slf4j
public class ErrorPageController {


    @GetMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage404");
        printInfo(request);
        return "error-page/404";
    }

    @GetMapping("/error-page/400")
    public String errorPage400(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage400");
        printInfo(request);
        return "error-page/400";
    }


    @GetMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage500");
        printInfo(request);
        return "error-page/500";
    }


    @GetMapping(value = "/error-page/500", produces = "application/json")
    public ResponseEntity<Map<String,Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage500API");
        printInfo(request);

        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        Integer statusCode = (Integer) request.getAttribute(ERROR_STATUS_CODE);

        Map<String, Object> errors = new HashMap<>();

        errors.put("ex", ex.getClass());
        errors.put("message", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.valueOf(statusCode));
    }


    public void printInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION : {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_MESSAGE : {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_EXCEPTION_TYPE : {}",request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_STATUS_CODE : {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("ERROR_REQUEST_URI : {}",request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME : {}",request.getAttribute(ERROR_SERVLET_NAME));
    }

}


