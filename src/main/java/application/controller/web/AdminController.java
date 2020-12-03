package application.controller.web;

import application.data.entity.FileUpload;
import application.data.entity.Person;
import application.data.entity.User;
import application.service.FileProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileProcess fileProcess;

    @GetMapping("/upload")
    String index(Model model) {
        model.addAttribute("fileUpload", new FileUpload());
        return "admin/upload";
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }

    @GetMapping("/home")
    String getHome(Model model) {
        return "admin/homepage";
    }

    @PostMapping ("/updateUpload")
    String getUpdate(Model model){ return "updateAndClose"; }

    @PostMapping ("/fileProcess")
    String getUploadResult(Model model, @Valid @ModelAttribute("fileUpload") FileUpload fileUpload, final Principal principal) throws IOException {
        model.addAttribute("listPerson", fileProcess.readFile(fileUpload.getExcelName()));
        return  "admin/fileProcess"; }
}
