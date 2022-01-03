package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {


    private final SessionManager sessionManager;
    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form, Model model) {
        model.addAttribute("loginForm", form);
        return "login/loginForm";
    }


//    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, Model model,
                        HttpServletResponse response) {

        if(bindingResult.hasErrors()){
            log.info("에러 발생 : {} ", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}",loginMember );


        if (loginMember == null) {
            log.info("에러 발생 : {} ", bindingResult);
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO


        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));

        // 쿠키에 시간 주지 않으면 브라우저 끝나면 쿠키는 사라진다.
        // cookie.setMaxAge(1800);
        response.addCookie(cookie);


        return "redirect:/";
    }


//    @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, Model model,
                        HttpServletResponse response) {

        if(bindingResult.hasErrors()){
            log.info("에러 발생 : {} ", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}",loginMember);


        if (loginMember == null) {
            log.info("에러 발생 : {} ", bindingResult);
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO
        sessionManager.createSession(loginMember, response);
        return "redirect:/";
    }


//    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, Model model,
                          HttpServletRequest request) {

        if(bindingResult.hasErrors()){
            log.info("에러 발생 : {} ", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}",loginMember);


        if (loginMember == null) {
            log.info("에러 발생 : {} ", bindingResult);
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO

        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성한다.
        // 서블릿이 제공하는 HTTP 세션 기술 사용.
        HttpSession session = request.getSession();
        log.info("session = {}", session.getId());


        // 세션에 로그인 정보를 저장한다. 세션은 JSESSIONID라는 값을 준다
        // 이 세션 정보에 "LOGINmEMBER"라는 KEY로 객체를 저장한다.
        // 하나의 세션에 여러 개의 값을 저장한다.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }



    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, Model model,
                          HttpServletRequest request, @RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL) {

        if(bindingResult.hasErrors()){
            log.info("에러 발생 : {} ", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}",loginMember);


        if (loginMember == null) {
            log.info("에러 발생 : {} ", bindingResult);
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO

        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성한다.
        // 서블릿이 제공하는 HTTP 세션 기술 사용.
        HttpSession session = request.getSession();
        log.info("session = {}", session.getId());


        // 세션에 로그인 정보를 저장한다. 세션은 JSESSIONID라는 값을 준다
        // 이 세션 정보에 "LOGINmEMBER"라는 KEY로 객체를 저장한다.
        // 하나의 세션에 여러 개의 값을 저장한다.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        log.info("redirect URL = {}", redirectURL);
        return "redirect:" + redirectURL;
    }







//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }




    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


}
