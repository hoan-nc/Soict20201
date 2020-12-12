package application.controller.web;

import application.data.entity.*;
import application.data.repository.IUserRepository;
import application.data.repository.IUserRoleRepository;
import application.data.service.UserService;
import application.service.FileProcess;
import application.service.Permit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);
    @Autowired
    Permit permit;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileProcess fileProcess;

    @Autowired
    UserService userService;


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

    @PostMapping("/updateUpload")
    String getUpdate(Model model) {
        return "updateAndClose";
    }

    @PostMapping("/fileProcess")
    String getUploadResult(Model model, @Valid @ModelAttribute("fileUpload") FileUpload fileUpload, final Principal principal) throws IOException {
        model.addAttribute("listPerson", fileProcess.readFile(fileUpload.getExcelName()));
        return "admin/fileProcess";
    }

    @GetMapping("/getPermit")
    String getPermit(Model model) {
        model.addAttribute("allUsers", permit.getAllUSer());
        model.addAttribute("roles", permit.getAllRole());
        model.addAttribute("userRoleUpdate", new UserRole());
        return "admin/permission";
    }

    @PostMapping("/setPermit")
    String setPermit(Model model,@Valid @ModelAttribute("userRoleUpdate") UserRole userRole) {
        System.out.println(userRole.getRoleId()+"|"+userRole.getUserId()+"|"+userRole.getStatus()+"|"+userRole.getUserRoleId()+"");
        model.addAttribute("allUsers", permit.getAllUSer());
        model.addAttribute("roles", permit.getAllRole());
        model.addAttribute("userRoleUpdate", new UserRole());
        return "redirect:/admin/getPermit";
    }
}
