package application.web.controller;

import application.domain.DepartmentExamForm;
import application.domain.ExaminationForm;
import application.domain.PhysicalExamForm;
import application.domain.UserRoleForm;
import application.entity.*;
import application.entity.id.UserRoleId;
import application.service.AdminService;
import application.service.UserService;
import application.service.file.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
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
    String getUploadResult(Model model,
                           @Valid @ModelAttribute("fileUpload") FileUpload fileUpload,
                           final Principal principal,
                           HttpSession session) throws IOException {
        UserEntity userEntity = userService.getByUsername(principal.getName());
        fileService.readFile(fileUpload, userEntity);
        List<PhysicalExamEntity> listPerson = fileService.readFile(fileUpload, userEntity);
        model.addAttribute("listPerson", listPerson);
        session.setAttribute("listPerson", listPerson);
        return "admin/fileProcess";
    }

    @PostMapping("/saveUploadFileResult")
    String saveUploadResult(@Valid @ModelAttribute("fileUpload") FileUpload fileUpload, final Principal principal) {
        UserEntity userEntity = userService.getByUsername(principal.getName());
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        List<PhysicalExamEntity> listPerson = (List<PhysicalExamEntity>) session.getAttribute("listPerson");
        return "updateAndClose";
    }
    //END MANAGE UPLOAD FILE

    //START MANAGE PERMISSION ROLE
    @GetMapping("/users-permissions")
    String getPermit(Model model) {
        model.addAttribute("allUserRoles", adminService.getAllUserRoles());
        model.addAttribute("roles", adminService.getAllRole());
        model.addAttribute("roleIdChangeSelected", null);
        return "admin/permission";
    }

    @RequestMapping(value = "/users-permissions/new", method = RequestMethod.POST)
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
    String updateUserPermission(Long userId, @Valid @ModelAttribute("roleIdChangeSelected") Long roleIdChangeSelected) {
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(UserRoleId.builder().userId(userId).roleId(roleIdChangeSelected).build())
                .build();
        adminService.saveUpdateUserRole(userRoleEntity);
        return "redirect:/admin/users-permissions";
    }

    @RequestMapping(value = "/users-permissions/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUserPermission(Long userId) {
        adminService.deleteUserPermission(userId);
        return "redirect:/admin/users-permissions";
    }
    //END MANAGE PERMISSION ROLE

    //START MANAGE PROFILES
    @GetMapping("/general-profiles")
    String getGeneralProfiles(Model model) {
        model.addAttribute("allPhysical", adminService.getAllPhysicalExam());
        model.addAttribute("allExaminations", adminService.findAllExamination());
        model.addAttribute("allDepartmentExams", adminService.findAllDepartmentExams());
        model.addAttribute("allUsers", adminService.getAllUserRoles());
        model.addAttribute("physicalExamForm", new PhysicalExamForm());
        return "admin/generalProfile";
    }

    @RequestMapping(value = "/general-profiles/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PhysicalExamEntity findPhysicalById(@PathVariable("id") Long physicalId) {
        return adminService.findPhysicalExamById(physicalId);
    }

    @SneakyThrows
    @RequestMapping(value = "/general-profiles/new", method = RequestMethod.POST)
    String addNewPhysicalExam(PhysicalExamForm form) {
        PhysicalExamEntity physicalExamEntity = PhysicalExamEntity.builder()
                .id(null)
                .user(UserEntity.builder().id(form.getUserId()).build())
                .examination(ExaminationEntity.builder().id(form.getExaminationId()).build())
                .departmentExam(DepartmentExamEntity.builder().id(form.getDepartmentExamId()).build())
                .createdDate(form.getCreatedDate())
                .height(form.getHeight())
                .weight(form.getWeight())
                .bloodPressure(form.getBloodPressure())
                .eyes(form.getEyes())
                .insideMedical(form.getInsideMedical())
                .outsideMedical(form.getOutsideMedical())
                .earNoseThroat(form.getEarNoseThroat())
                .dentomaxillofacial(form.getDentomaxillofacial())
                .dermatology(form.getDermatology())
                .nerve(form.getNerve())
                .bloodAnalysis(form.getBloodAnalysis())
                .whiteBloodNumber(form.getWhiteBloodNumber())
                .redBloodNumber(form.getRedBloodNumber())
                .hemoglobin(form.getHemoglobin())
                .plateletNumber(form.getPlateletNumber())
                .bloodUrea(form.getBloodUrea())
                .bloodCreatinine(form.getBloodCreatinine())
                .hepatitisB(form.getHepatitisB())
                .healthType(form.getHealthType())
                .advisory(form.getAdvisory())
                .isActive(true)
                .build();

        adminService.saveOrUpdatePhysicalExam(physicalExamEntity);
        return "redirect:/admin/general-profiles";
    }

    @RequestMapping(value = "/general-profiles/update", method = RequestMethod.POST)
    String updatePhysicalExam(PhysicalExamForm input) {
        PhysicalExamEntity physicalExamEntity = PhysicalExamEntity.builder()
                .id(input.getId())
                .nameUser(input.getFullName())
                .user(UserEntity.builder().id(input.getUserId()).build())
                .examination(ExaminationEntity.builder().id(input.getExaminationId()).build())
                .departmentExam(DepartmentExamEntity.builder().id(input.getDepartmentExamId()).build())
                .createdDate(input.getCreatedDate())
                .height(input.getHeight())
                .weight(input.getWeight())
                .bloodPressure(input.getBloodPressure())
                .eyes(input.getEyes())
                .insideMedical(input.getInsideMedical())
                .outsideMedical(input.getOutsideMedical())
                .earNoseThroat(input.getEarNoseThroat())
                .dentomaxillofacial(input.getDentomaxillofacial())
                .dermatology(input.getDermatology())
                .nerve(input.getNerve())
                .bloodAnalysis(input.getBloodAnalysis())
                .whiteBloodNumber(input.getWhiteBloodNumber())
                .redBloodNumber(input.getRedBloodNumber())
                .hemoglobin(input.getHemoglobin())
                .plateletNumber(input.getPlateletNumber())
                .bloodUrea(input.getBloodUrea())
                .bloodCreatinine(input.getBloodCreatinine())
                .hepatitisB(input.getHepatitisB())
                .healthType(input.getHealthType())
                .advisory(input.getAdvisory())
                .isActive(true)
                .build();
        adminService.saveOrUpdatePhysicalExam(physicalExamEntity);

        return "redirect:/admin/general-profiles";
    }

    @RequestMapping(value = "/general-profiles/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deletePhysicalExam(Long id) {
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
    String updateExamination(ExaminationEntity examinationEntity) {
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

    @RequestMapping(value = "/manage-examination/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteExamination(Long id) {
        adminService.deleteExamination(id);
        return "redirect:/admin/manage-examination";
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

    @RequestMapping(value = "/manage-department-exam/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteDepartmentExam(Long id) {
        adminService.deleteDepartmentExam(id);
        return "redirect:/admin/manage-department-exam";
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


    @GetMapping("/statistic-healthy-type-page")
    String getStatisticHealthyType(Model model) {
        Calendar calendar = Calendar.getInstance();
        long year = calendar.get(Calendar.YEAR);
        TreeMap<String, Long> mapData = adminService.getStatisticHealthyType(year);
        model.addAttribute("mapStatisticHealthyType", mapData);

        return "admin/statisticHealthyType";
    }

    @GetMapping("/statistic-healthy-type/{year}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticHealthyTypeChart(@PathVariable("year") long year) {
        return new ResponseEntity<>(adminService.getStatisticHealthyType(year), HttpStatus.OK);
    }

    @GetMapping("/statistic-nerve-page")
    String getStatisticNerve(Model model) {
        String type = "BT";
        TreeMap<String, Long> mapData = adminService.getStatisticNerve(type);
        model.addAttribute("mapStatisticNerve", mapData);

        return "admin/statisticNerve";
    }

    @GetMapping("/statistic-nerve/{type}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticNerveChart(@PathVariable("type") String type) {
        return new ResponseEntity<>(adminService.getStatisticNerve(type), HttpStatus.OK);
    }

    @GetMapping("/statistic-dermatology-page")
    String getStatisticDermatology(Model model) {
        String type = "BT";
        TreeMap<String, Long> mapData = adminService.getStatisticDermatology(type);
        model.addAttribute("mapStatisticDermatology", mapData);

        return "admin/statisticDermatology";
    }

    @GetMapping("/statistic-dermatology/{type}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticDermatologyChart(@PathVariable("type") String type) {
        return new ResponseEntity<>(adminService.getStatisticDermatology(type), HttpStatus.OK);
    }

    @GetMapping("/statistic-inside-medical-page")
    String getStatisticInsideMedical(Model model) {
        String type = "BT";
        TreeMap<String, Long> mapData = adminService.getStatisticInsideMedical(type);
        model.addAttribute("mapStatisticInsideMedical", mapData);

        return "admin/statisticInsideMedical";
    }

    @GetMapping("/statistic-inside-medical/{type}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticInsideMedicalChart(@PathVariable("type") String type) {
        return new ResponseEntity<>(adminService.getStatisticInsideMedical(type), HttpStatus.OK);
    }

    @GetMapping("/statistic-outside-medical-page")
    String getStatisticOutsideMedical(Model model) {
        String type = "BT";
        TreeMap<String, Long> mapData = adminService.getStatisticOutsideMedical(type);
        model.addAttribute("mapStatisticOutsideMedical", mapData);

        return "admin/statisticOutsideMedical";
    }

    @GetMapping("/statistic-outside-medical/{type}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticOutsideMedicalChart(@PathVariable("type") String type) {
        return new ResponseEntity<>(adminService.getStatisticOutsideMedical(type), HttpStatus.OK);
    }

    @GetMapping("/statistic-ear-nose-throat-page")
    String getStatisticEarNoseThroat(Model model) {
        String type = "BT";
        TreeMap<String, Long> mapData = adminService.getStatisticEarNoseThroat(type);
        model.addAttribute("mapStatisticEarNoseThroat", mapData);

        return "admin/statisticEarNoseThroat";
    }

    @GetMapping("/statistic-ear-nose-throat/{type}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticEarNoseThroatChart(@PathVariable("type") String type) {
        return new ResponseEntity<>(adminService.getStatisticEarNoseThroat(type), HttpStatus.OK);
    }

    @GetMapping("/statistic-dentomaxillofacial-page")
    String getStatisticDentomaxiillofacial(Model model) {
        String type = "BT";
        TreeMap<String, Long> mapData = adminService.getStatisticDentomaxiillofacial(type);
        model.addAttribute("mapStatisticEarNoseThroat", mapData);

        return "admin/statisticDentomaxillofacial";
    }

    @GetMapping("/statistic-dentomaxillofacial/{type}")
    public ResponseEntity<TreeMap<String, Long>> getStatisticDentomaxiillofacialChart(@PathVariable("type") String type) {
        return new ResponseEntity<>(adminService.getStatisticDentomaxiillofacial(type), HttpStatus.OK);
    }

    @GetMapping("/statistic-inside-percent-now")
    public ResponseEntity<TreeMap<String, Double>> getStatisticCurrentInsideMedical() {
        return new ResponseEntity<>(adminService.getStatisticCurrentInsideMedical(), HttpStatus.OK);
    }

    @GetMapping("/statistic-outside-percent-now")
    public ResponseEntity<TreeMap<String, Double>> getStatisticCurrentOutsideMedical() {
        return new ResponseEntity<>(adminService.getStatisticCurrentOutsideMedical(), HttpStatus.OK);
    }

    @GetMapping("/statistic-ear-nose-throat-percent-now")
    public ResponseEntity<TreeMap<String, Double>> getStatisticCurrentEarNoseThroat() {
        return new ResponseEntity<>(adminService.getStatisticCurrentEarNoseThroat(), HttpStatus.OK);
    }

    @GetMapping("/statistic-dentomaxillofacial-percent-now")
    public ResponseEntity<TreeMap<String, Double>> getStatisticCurrentDentomaxilloFacial() {
        return new ResponseEntity<>(adminService.getStatisticCurrentDentomaxilloFacial(), HttpStatus.OK);
    }

    //END STATISTIC
}
