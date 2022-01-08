package hello.upload.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Data
public class UploadFile {

    private String uploadFileName; // 업로드한 파일 이름
    private String storeFileName; // 올리는 사람은 다르다. 따라서 다른 사람이 같은 이름으로 다른 내용을 올릴 수도 있음. 안 겹치게 uuid 같은걸로 만들어야 함.







}
