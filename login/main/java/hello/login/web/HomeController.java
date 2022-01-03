package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {


    private final SessionManager sessionManager;
    private final MemberRepository memberRepository;


//    @GetMapping("/")
    public String home() {
        return "home";
    }


//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId,
                            Model model) {

        if (memberId == null) {
            return "home";
        }

        Member findMember = memberRepository.findById(memberId);
        if (findMember == null) {
            return "home";
        }

        model.addAttribute("member", findMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Object session = sessionManager.getSession(request);


        if (session == null) {
            return "home";
        }


        Member findMember = (Member) session;


        model.addAttribute("member", findMember);
        return "loginHome";
    }


//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member findMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        model.addAttribute("member", findMember);
        return "loginHome";
    }


//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    @GetMapping("/")
    public String homeLoginV4(@Login Member loginMember, Model model) {


        log.info("loginMember = {}", loginMember);

        if (loginMember == null) {
            return "home";
        }


        model.addAttribute("member", loginMember);
        return "loginHome";
    }


}