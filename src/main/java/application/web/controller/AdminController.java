package application.web.controller;

import application.domain.DepartmentExamForm;
import application.domain.ExaminationForm;
import application.domain.PhysicalExamForm;
import application.domain.UserRoleForm;
import application.entity.*;
import application.service.AdminService;
import application.service.UserService;
import application.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final FileService fileService;
    private final AdminService adminService;
    private final UserService userService;


    @Autowired
    public AdminController(FileService fileService, AdminService adminService, UserService userService) {
        this.fileService = fileService;
        this.adminService = adminService;
        this.userService = userService;
    }


    //START MANAGE UPLOAD FILE
    @GetMapping("/upload")
    String index(Model model, final Principal principal) {
        model.addAttribute("fileUpload", new FileUpload());
        model.addAttribute("allExaminations", adminService.findAllExamination());
        model.addAttribute("allDepartmentExams", adminService.findAllDepartmentExams());
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
        model.addAttribute("allUserRoles", adminService.getAllUserRoles());
        model.addAttribute("roles", adminService.getAllRole());
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

        adminService.saveNewUserRole(userRoleEntity);
        return "redirect:/admin/users-permissions";
    }

    @PostMapping(value = "/users-permissions/update")
    String updateUserPermission(Long userId, Long roleId,
                                @Valid @ModelAttribute("userRoleUpdate") UserRoleEntity userRoleEntity) {
        userRoleEntity.getId().setUserId(userId);
        adminService.saveUpdateUserRole(userRoleEntity);
        return "redirect:/admin/users-permissions";
    }
    //END MANAGE PERMISSION ROLE

    //START MANAGE PROFILES
    @GetMapping("/general-profiles")
    String getGeneralProfiles(Model model) {
        model.addAttribute("allPhysical", adminService.getAllPhysicalExam());
        return "admin/generalProfile";
    }

    @RequestMapping(value = "/general-profiles/new", method = RequestMethod.POST)
    String addNewPhysicalExam(PhysicalExamForm physicalExamForm) {
        PhysicalExamEntity physicalExamEntity = PhysicalExamEntity.builder()
                .id(null)
                .build();

        adminService.saveOrUpdatePhysicalExam(physicalExamEntity);
        return "redirect:/admin/general-profiles";
    }

    @RequestMapping(value = "/general-profiles/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deletePhysicalExam(@PathVariable("id") Long id) {
        PhysicalExamEntity physicalExamEntity = adminService.findPhysicalExamById(id);
        adminService.deletePhysicalExam(physicalExamEntity);
        return "redirect:/admin/general-profiles";
    }
    //END MANAGE PROFILES

    //START MANAGE EXAMINATION
    @GetMapping("/manage-examination")
    String getAllExamination(Model model) {
        model.addAttribute("allExaminations", adminService.findAllExamination());
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

        adminService.saveOrUpdateExamination(examinationEntity);
        return "redirect:/admin/manage-examination";
    }

    @RequestMapping(value = "/manage-examination/update", method = RequestMethod.POST)
    String addExamination(ExaminationEntity examinationEntity) {
        Long year = Long.valueOf(examinationEntity.getCreatedDate().substring(6));
        examinationEntity.setYear(year);

        adminService.saveOrUpdateExamination(examinationEntity);
        return "redirect:/admin/manage-examination";
    }

    @RequestMapping(value = "/manage-examination/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ExaminationEntity findExaminationById(@PathVariable("id") Long examinationId) {
        return adminService.findExaminationById(examinationId);
    }
    //END MANAGE EXAMINATION

    //START MANAGE DEPARTMENT EXAM
    @GetMapping("/manage-department-exam")
    String getAllDepartmentExams(Model model) {
        model.addAttribute("allDepartmentExams", adminService.findAllDepartmentExams());
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

        adminService.saveOrUpdateDepartmentExam(departmentExamEntity);
        return "redirect:/admin/manage-department-exam";
    }

    @RequestMapping(value = "/manage-department-exam/update", method = RequestMethod.POST)
    String addDepartmentExam(DepartmentExamEntity departmentExamEntity) {
        adminService.saveOrUpdateDepartmentExam(departmentExamEntity);
        return "redirect:/admin/manage-department-exam";
    }

    @RequestMapping(value = "/manage-department-exam/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DepartmentExamEntity findDepartmentExamById(@PathVariable("id") Long departmentExamId) {
        return adminService.findDepartmentExamById(departmentExamId);
    }
    //END MANAGE DEPARTMENT EXAM

    //START STATISTIC
    @GetMapping("/statistic-height-page")
    String getStaticHeight(Model model) {
        Map<String, Double> mapData = adminService.getStatisticHeight();
        model.addAttribute("mapStatisticHeight", mapData);
        return "/admin/statisticHeight";
    }

    @GetMapping("/statistic-height")
    public ResponseEntity<Map<String, Double>> getStatisticHeightPieChart() {
        return new ResponseEntity<>(adminService.getStatisticHeight(), HttpStatus.OK);
    }

    @GetMapping("/statistic-weight-page")
    String getStaticWeight(Model model) {
        TreeMap<String, Double> mapData = adminService.getStatisticWeight();
        model.addAttribute("mapStatisticWeight", mapData);
        return "admin/statisticWeight";
    }

    @GetMapping("/statistic-weight")
    public ResponseEntity<TreeMap<String, Double>> getStatisticWeightPieChart() {
        return new ResponseEntity<>(adminService.getStatisticWeight(), HttpStatus.OK);
    }

    //END STATISTIC
}
