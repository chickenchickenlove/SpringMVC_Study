package hello.upload.controller;



// 컨트롤러는 도메인 다 만들고 만든다

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;


    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        // file 들어오면 서버에 저장. 그리고 uploadFile명으로 반환.
        // 여기까지는 파일이 저장이 다됨.
        // 보통 파일은 DB에 저장하는 것이 아니라 STORAGE에 저장한다.
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        // 데이터베이스에 저장
        // 데이터베이스에 저장하는 것은 파일의 경로 정도만 저장한다.
        // 실제 파일 자체를 DB에 저장하지는 않는다.
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId()); // pathVariable로 쓰려고 한 거 같다.

        return "redirect:/items/{itemId}";
    }
    // 여기까지 개발하고 파일 제출해보고 F12로 확인해보면, 오류는 나지만 Redirect 성공한 것을 확인할 수 있다.


    // item을 보여주는 기능도 구현하자
    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {

        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);

        return "item-view";
    }

    // 이미지를 내려주는 컨트롤러
    // 이 정도만 했을 때는 보안에 취약하니, 내부적으로 체크 로직을 넣는게 좋다.
    @ResponseBody
    @GetMapping("/images/{filename}") // 스프링의 Resource를 쓴다.
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        //file:/Users/../" + fileNAme (uuid).png 이렇게 찾아온다.
        // 이 경로에 있는 파일에 접근해서, 이 파일을 Stream(바이트?)로 반환을 해준다.
        // UrlResource라고 붙으면 내부 파일에 접근한다.
        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(filename));
        return urlResource;
    }


    @GetMapping("/attach/{itemId}") // 헤더에 뭔가를 추가해야하니 ResponseEntity를 사용했다.
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {



        // 아이템에 접근할 수 있는 사용자만 이 파일을 받을 수 있어! 라고 한다면 itemId를 받아야겠지?
        // 권한, 사용자 뭐 이런 것들...
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();
        // 내가 다운로드 받을 때, 실제로 업로드 했던 파일 명이 나와야 되기 때문이다.

        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
        log.info("uploadFileName={}", uploadFileName);



        // 인코딩을 안하고 내려주면, 브라우저마다 다르긴 한데 한글이 깨질 수 있음.
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        // 이거 넣어주면 다운이 된다. 규약이니 외우자.
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";
        // 헤더없이 파일만 넣어보자. 이렇게 하면, 다운로드를 클릭하면 업로드가 안되고 그 파일이 그냥 브라우저에서 바로 열린다.
        // 우리가 원하는 건 다운로드 받는 것을 원한다 --> 헤더를 넣어줘야한다.
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                .body(urlResource);
    }
}
