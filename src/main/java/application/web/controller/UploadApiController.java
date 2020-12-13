package application.web.controller;

import application.domain.FileUploadResult;
import application.service.file.FileStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadApiController {

    Logger log = LogManager.getLogger(UploadApiController.class);

    private final FileStorageService storageService;

    public UploadApiController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public FileUploadResult uploadImage(
            @RequestParam("file") MultipartFile[] files) {
        String message = "";
        FileUploadResult result = new FileUploadResult();
        try {
            StringBuilder link = new StringBuilder();
            for (MultipartFile file : files) {
                link.append("/link/").append(storageService.store(file)).append(";");
            }
            message = "You successfully uploaded !";
            result.setMessage(message);
            result.setHttp(200);
            result.setLink(link.substring(0, link.length() - 1));
        } catch (Exception e) {
            result.setHttp(500);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }
}
