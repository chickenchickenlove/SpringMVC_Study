package hello.upload.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    // 경로를 가지고 와야함.
    // application.properties에 있는 경로를 바로 가져올 수 있음.
    // 값을 주입시켜주는 거 같다.
    @Value("${file.dir}")
    private String fileDir;


    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }


    @PostMapping("/upload")
    public String saveFileV2(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts={}", parts);

        for (Part part : parts) {
            log.info("==== PART ====");
            log.info("name = {}", part.getName());

            // parts도 헤더와 Body의 값이 나눠져서 불러진다.
            // 파일을 넘기면 헤더가 2개가 넘어감 (dispostion, type)
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("header {}: {}", headerName, part.getHeader(headerName));
            }

            // 편의 메서드
            // content-disposition ; filename이 들어가있음.
            log.info("submittedFilename ={}", part.getSubmittedFileName());
            log.info("size = {}", part.getSize()); // body 사이즈 읽기

            // 데이터 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// 바이너리 값을 문자로 바꾸면, 항상 뭔가 인코딩 될 게 뭔지 알려줘야한다.
            log.info("body = {}", body); // 억지로 인코딩해서 파일의 내용을 STring으로 찍어봄


            // 파일에 저장하기
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName(); // 디렉토리 + 파일명 합쳐진 것
                log.info("파일 저장 fullPath = {}", fullPath);

                part.write(fullPath); // part는 write를 지원하고, 여기에 경로를 넣어주면 이게 저장이 된다.



            }




        }



        return "upload-form";
    }


}
