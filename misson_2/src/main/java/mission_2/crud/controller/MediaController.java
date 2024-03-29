package mission_2.crud.controller;

import mission_2.crud.model.MediaDescriptorDto;
import mission_2.crud.service.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("media")
public class MediaController {
    private static final Logger logger= LoggerFactory.getLogger(MediaController.class);
    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("upload")
    public ResponseEntity<MediaDescriptorDto> uploadMedia(
            @RequestParam("file") MultipartFile file){
        MediaDescriptorDto descriptorDto= this.mediaService.saveFile(file);
        return ResponseEntity
                .status(descriptorDto.getStatus())
                .body(descriptorDto);
    }

    @PostMapping("upload-bulk")
    public ResponseEntity<Collection<MediaDescriptorDto>> uploadMediaBulk(
            @RequestParam("file") MultipartFile[] files
    ){
        return ResponseEntity
                .status(HttpStatus.MULTI_STATUS)
                .body(this.mediaService.saveFileBulk(files));
    }

    @GetMapping("**")
    public ResponseEntity<byte[]> staticLikeEndPoint(
            HttpServletRequest request, HttpServletResponse response
    ){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(this.mediaService.getFileAsBytes(request.getRequestURI().split("media")[1]),
                headers,
                HttpStatus.OK
        );
    }



    //    @PostMapping("test")
//    public void uploadMedia(
//            @RequestParam("file")MultipartFile file
//            ){
//        String basePath="./media";
//        File directory= new File(basePath);
//        if (!directory.exists()) directory.mkdir();
//        Path newFilePath= Path.of(basePath,file.getOriginalFilename());
//        try {
//            file.transferTo(newFilePath);
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }
}
