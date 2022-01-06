package hello.exception.api;


import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@RestController
public class ApiExceptionController {


    @RequestMapping("/api/response-status-ex1") // sendError로 한다. 즉 에러가 나간다.
    public void responseStatusEx1() {
        throw new BadRequestException();
    }

    @RequestMapping("/api/response-status-ex2")
    public void responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 에러입니다아아아아!!!", new RuntimeException());
    }


    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }


    @GetMapping("/api/members/{id}")
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
