package application.web.controller;

import application.domain.UserRoleForm;
import application.entity.*;
import application.service.PermitService;
import application.service.UserService;
import application.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("allUserRoles", permitService.getAllUserRoles());
        model.addAttribute("roles", permitService.getAllRole());
        model.addAttribute("userRoleUpdate", new UserRoleEntity());
        return "admin/permission";
    }

    @RequestMapping(value = "/users-permissions/addNew", method = RequestMethod.POST)
    String addUserPermission(UserRoleForm userRoleForm) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(UserEntity.builder()
                .password(userRoleForm.getPassword())
                .className(userRoleForm.getClassName())
                .fullName(userRoleForm.getFullName().trim())
                .userName(userRoleForm.getUserName().trim().toLowerCase())
                .build());
        userRoleEntity.setRole(RoleEntity.builder()
                .id(userRoleForm.getRoleId())
                .build());

        permitService.saveNewUserRole(userRoleEntity);
        return "redirect:/admin/users-permissions";
    }


    @PostMapping(value = "/users-permissions/update")
    String updateUserPermission(Long userId, Long roleId,
                                @Valid @ModelAttribute("userRoleUpdate") UserRoleEntity userRoleEntity) {
        userRoleEntity.getId().setUserId(userId);
        permitService.saveUpdateUserRole(userRoleEntity);
        return "redirect:/admin/users-permissions";
    }

    @GetMapping("/general-profiles")
    String getGeneralProfiles(Model model) {
        model.addAttribute("allPhysical", permitService.getAllPhysicalExam());
        return "admin/generalProfile";
    }

    @GetMapping("/general-profiles/new")
    public String showAddPhysicalExamForm(PhysicalExamEntity entity) {
        return "admin/addPhysicalExam";
    }

    @PostMapping("/general-profiles/add")
    public String addStudent(@Valid PhysicalExamEntity entity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/addPhysicalExam";
        }
        permitService.savePhysicalExam(entity);
        return "redirect:/admin/general-profiles";
    }


    @GetMapping("/general-profiles/edit/{id}")
    String showUpdateForm(@PathVariable("id") Long id, Model model) {
        PhysicalExamEntity physicalExamEntity = permitService.findPhysicalExamById(id);
        model.addAttribute("physicalExam", physicalExamEntity);
        return "admin/updatePhysicalExam";
    }

    @PostMapping("/general-profiles/update/{id}")
    public String updateStudent(@PathVariable("id") Long id,
                                @Valid PhysicalExamEntity physicalExamEntity,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            physicalExamEntity.setId(id);
            return "update-physical-exam";
        }
        permitService.savePhysicalExam(physicalExamEntity);
        model.addAttribute("allPhysical", permitService.getAllPhysicalExam());
        return "redirect:/admin/general-profiles";
    }

    @RequestMapping(value = "/general-profiles/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deletePhysicalExam(@PathVariable("id") Long id) {
        PhysicalExamEntity physicalExamEntity = permitService.findPhysicalExamById(id);
        permitService.deletePhysicalExam(physicalExamEntity);
        return "redirect:/admin/general-profiles";
    }

}
