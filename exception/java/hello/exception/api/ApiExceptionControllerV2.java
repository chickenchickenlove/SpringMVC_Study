package hello.exception.api;


import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Member;


@Slf4j
@RestController
public class ApiExceptionControllerV2 {







    @GetMapping("/apiV2/members/{id}")
    public MemberDao getMember(@PathVariable String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 예외입니다. ");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력입니다. ");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 정의 에러입니다. ");

        }

        return new MemberDao(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDao {
        private String memberId;
        private String name;

    }





}
