package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;

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


    public void printInfo(HttpServletRequest request) {

        log.info("ERROR_EXCEPTION : {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_MESSAGE : {}", request.getAttribute(ERROR_MESSAGE));
    }


}


