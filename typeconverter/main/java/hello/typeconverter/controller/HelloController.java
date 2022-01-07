package hello.typeconverter.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HelloController {


    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {

        String data = request.getParameter("data");
        Integer intData = Integer.valueOf(data);
        System.out.println("intData = " + intData);

        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("intData = " + data);
        return "ok";
    }


    @GetMapping("/hello-v3")
    public String helloV3(@ModelAttribute(name = "data") Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/hello-v3/{data}")
    public String helloV4(@PathVariable Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }


    // 이건 내부적으로 Converter가 움직이고 있는 것이다.





}
