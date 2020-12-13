package application.web.controller;

import application.entity.FileUpload;
import application.entity.UserRoleEntity;
import application.service.PermitService;
import application.service.UserService;
import application.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PermitService permitService;

    private final ModelMapper modelMapper;

    private final FileService fileService;

    private final UserService userService;

    public AdminController(PermitService permitService, ModelMapper modelMapper, FileService fileService, UserService userService) {
        this.permitService = permitService;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.userService = userService;
    }


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
        model.addAttribute("listPerson", fileService.readFile(fileUpload.getExcelName()));
        return "admin/fileProcess";
    }

    @GetMapping("/users-permissions")
    String getPermit(Model model) {
        model.addAttribute("allUsers", permitService.getAllUSer());
        model.addAttribute("roles", permitService.getAllRole());
        model.addAttribute("userRoleUpdate", new UserRoleEntity());
        return "admin/permission";
    }

    @PostMapping("/setPermit")
    String setPermit(Model model, @Valid @ModelAttribute("userRoleUpdate") UserRoleEntity userRoleEntity) {
        System.out.println(userRoleEntity.getId().getRoleId() + "|" + userRoleEntity.getId().getUserId() + "|" + userRoleEntity.getStatus() + "|" + userRoleEntity.getId() + "");
        model.addAttribute("allUsers", permitService.getAllUSer());
        model.addAttribute("roles", permitService.getAllRole());
        model.addAttribute("userRoleUpdate", new UserRoleEntity());
        return "redirect:/admin/users-permissions";
    }

    @GetMapping("/general-profiles")
    String getGeneralProfiles(Model model) {
        model.addAttribute("allUsers", permitService.getAllUSer());
        model.addAttribute("roles", permitService.getAllRole());
        model.addAttribute("userRoleUpdate", new UserRoleEntity());
        return "admin/generalProfile";
    }

}
