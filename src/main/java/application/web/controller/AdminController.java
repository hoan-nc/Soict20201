package application.web.controller;

import application.domain.DepartmentExamForm;
import application.domain.ExaminationForm;
import application.domain.UserRoleForm;
import application.entity.*;
import application.service.PermitService;
import application.service.UserService;
import application.service.file.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PermitService permitService;
    private final FileService fileService;
    private final UserService userService;

    public AdminController(PermitService permitService, FileService fileService, UserService userService) {
        this.permitService = permitService;
        this.fileService = fileService;
        this.userService = userService;
    }

    //START MANAGE UPLOAD FILE
    @GetMapping("/upload")
    String index(Model model, final Principal principal) {
        ;
        model.addAttribute("fileUpload", new FileUpload());
        model.addAttribute("allExaminations", permitService.findAllExamination());
        return "admin/upload";
    }

    @PostMapping("/updateUpload")
    String getUpdate(Model model) {
        return "updateAndClose";
    }

    @PostMapping("/fileProcess")
    String getUploadResult(Model model, @Valid @ModelAttribute("fileUpload") FileUpload fileUpload, final Principal principal) throws IOException {
        UserEntity userEntity = userService.getByUsername(principal.getName());
        model.addAttribute("listPerson", fileService.readFile(fileUpload, userEntity));
        return "admin/fileProcess";
    }
    //END MANAGE UPLOAD FILE

    //START MANAGE PERMISSION ROLE
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
    //END MANAGE PERMISSION ROLE

    //START MANAGE PROFILES
    @GetMapping("/general-profiles")
    String getGeneralProfiles(Model model) {
        model.addAttribute("allPhysical", permitService.getAllPhysicalExam());
        return "admin/generalProfile";
    }

    @RequestMapping(value = "/general-profiles/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deletePhysicalExam(@PathVariable("id") Long id) {
        PhysicalExamEntity physicalExamEntity = permitService.findPhysicalExamById(id);
        permitService.deletePhysicalExam(physicalExamEntity);
        return "redirect:/admin/general-profiles";
    }
    //END MANAGE PROFILES

    //START MANAGE EXAMINATION
    @GetMapping("/manage-examination")
    String getAllExamination(Model model) {
        model.addAttribute("allExaminations", permitService.findAllExamination());
        return "admin/examinations";
    }

    @RequestMapping(value = "/manage-examination/new", method = RequestMethod.POST)
    String addExamination(ExaminationForm examinationForm) {
        Long year = Long.valueOf(examinationForm.getCreatedDate().substring(6));
        ExaminationEntity examinationEntity = ExaminationEntity.builder()
                .year(year)
                .name(examinationForm.getName().trim())
                .createdDate(examinationForm.getCreatedDate().trim())
                .build();

        permitService.saveOrUpdateExamination(examinationEntity);
        return "redirect:/admin/manage-examination";
    }

    @RequestMapping(value = "/manage-examination/update", method = RequestMethod.POST)
    String addExamination(ExaminationEntity examinationEntity) {
        Long year = Long.valueOf(examinationEntity.getCreatedDate().substring(6));
        examinationEntity.setYear(year);

        permitService.saveOrUpdateExamination(examinationEntity);
        return "redirect:/admin/manage-examination";
    }

    @RequestMapping(value = "/manage-examination/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ExaminationEntity findExaminationById(@PathVariable("id") Long examinationId) {
        return permitService.findExaminationById(examinationId);
    }
    //END MANAGE EXAMINATION

    //START MANAGE DEPARTMENT EXAM
    @GetMapping("/manage-department-exam")
    String getAllDepartmentExams(Model model) {
        model.addAttribute("allDepartmentExams", permitService.findAllDepartmentExams());
        return "admin/departmentExams";
    }

    @RequestMapping(value = "/manage-department-exam/new", method = RequestMethod.POST)
    String addDepartmentExam(DepartmentExamForm departmentExamForm) {
        DepartmentExamEntity departmentExamEntity = DepartmentExamEntity.builder()
                .id(null)
                .name(departmentExamForm.getName().trim())
                .address(departmentExamForm.getAddress().trim())
                .phoneNumber(departmentExamForm.getPhoneNumber().trim())
                .build();

        permitService.saveOrUpdateDepartmentExam(departmentExamEntity);
        return "redirect:/admin/manage-department-exam";
    }

    @RequestMapping(value = "/manage-department-exam/update", method = RequestMethod.POST)
    String addDepartmentExam(DepartmentExamEntity departmentExamEntity) {
        permitService.saveOrUpdateDepartmentExam(departmentExamEntity);
        return "redirect:/admin/manage-department-exam";
    }

    @RequestMapping(value = "/manage-department-exam/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DepartmentExamEntity findDepartmentExamById(@PathVariable("id") Long departmentExamId) {
        return permitService.findDepartmentExamById(departmentExamId);
    }
    //END MANAGE DEPARTMENT EXAM

}
