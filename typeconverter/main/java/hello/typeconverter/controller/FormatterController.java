package hello.typeconverter.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class FormatterController {


    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {

        log.info("formatterForm");

        Form form = new Form();
        form.setNumber(1000000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);

        return "formatter-form";
    }


    @PostMapping("/formatter/edit")
    public String formatterView(@ModelAttribute Form form) {

        log.info("formatterView");

        return "formatter-view";
    }


    @Data
    static class Form {

        @NumberFormat(pattern = "###,###")
        private Integer number;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;

    }





}
