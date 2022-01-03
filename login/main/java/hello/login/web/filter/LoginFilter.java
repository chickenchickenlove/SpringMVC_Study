package hello.login.web.filter;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@Slf4j
public class LoginFilter implements Filter {


    private final static String[] whiteList = {"/css/*", "/", "/members/add", "/login", "logout"};



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;


        try {
            log.info("인증 체크 필터 시작 : {}", requestURI);

            if (isLoginChekcPath(requestURI)) {
                HttpSession session = httpRequest.getSession(false);


                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 : {}", requestURI);
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return ;
                }

            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
        }finally {
            log.info("인증 체크 필터 종료 : {}", requestURI);
        }
    }


    private boolean isLoginChekcPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI); // 로그인 체크 확인한다 → white list면 false를 돌려줘야한다
    }
}
