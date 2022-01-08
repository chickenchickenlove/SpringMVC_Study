package hello.upload.file;


import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;


    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }


    // 여러개 저장된다고 가정하면, 다음 코드를 뽑는다.
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
                // 위는 한 줄로 줄일 수 있다.

            }
        }
        return storeFileResult;
    }


    // 멀티 파트 파일을 받아서
    // 1. 파일 저장
    // 2. uploadFile로 반환해준다.
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        // original filename : image.png

        // 서버에 저장하는 파일명 : uuid로 하겠다.
        // uuid로 저장하는데, 확장자는 가져오고 싶다.
        // 메서드로 다 뽑아버린다.
        // 이렇게 두 줄로 하면, 코드의 가독성이 좋아졌지? 아래 위로 같으니가.
        String storeFileName = createStoreFileName(originalFilename);

        // 저장소에 저장함.
        multipartFile.transferTo(new File(getFullPath(storeFileName)));


        return new UploadFile(originalFilename, storeFileName);



    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFileName = uuid + "." + ext;
        return storeFileName;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf("."); // "."으로 찍고 가져오면, 여기의 위치를 알려준다.
        String ext = originalFilename.substring(pos + 1);// 특정 위치 기준으로 값을 뽑아준다. +1 을 하는 이유는 . 바로 뒷쪽뿌터 뽑아야 하니까.
        return ext;
    }


}
