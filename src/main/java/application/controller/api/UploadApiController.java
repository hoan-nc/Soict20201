package application.controller.api;

import application.model.FileUploadResult;
import application.service.FileStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadApiController {

    Logger log = LogManager.getLogger(UploadApiController.class);

    @Autowired
    FileStorageService storageService;

    @PostMapping("/upload")
    public FileUploadResult uploadImage(
            @RequestParam("file") MultipartFile[] files) {
        String message = "";
        FileUploadResult result = new FileUploadResult();
        try {
            String link = "";
            for (MultipartFile file : files) {
                link += "/link/" + storageService.store(file) + ";";
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
