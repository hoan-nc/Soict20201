
package application.web.controller;

import application.service.file.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/link/")
public class LinkController {

    private final FileStorageService storageService;

    public LinkController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        MediaType type = MediaType.ALL;

        if (filename.toLowerCase().endsWith("png")) {
            type = MediaType.IMAGE_PNG;
        } else if (filename.toLowerCase().endsWith("jpg") || filename.toLowerCase().endsWith("jpeg")) {
            type = MediaType.IMAGE_JPEG;
        } else if (filename.toLowerCase().endsWith("gif")) {
            type = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.ok()
                .contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
