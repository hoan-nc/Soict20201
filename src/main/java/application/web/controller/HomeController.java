package application.web.controller;

import application.entity.UserEntity;
import application.service.AdminService;
import application.service.UserService;
import application.service.impl.UserAuthenticatorServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController {
    private final UserAuthenticatorServiceImpl authenticatorService;
    private final AdminService adminService;
    private final UserService userService;

    public HomeController(UserAuthenticatorServiceImpl authenticatorService,
                          AdminService adminService,
                          UserService userService) {
        this.authenticatorService = authenticatorService;
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @GetMapping("/home")
    String home(Model model) {
        String username = authenticatorService.getUsernameLogin();
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SimpleGrantedAuthority simpleGrantedAuthority = authorities.get(0);
        if ("ROLE_ADMIN".equalsIgnoreCase(simpleGrantedAuthority.getAuthority())) {
            model.addAttribute("totalQuantityUser", adminService.getTotalQuantityUsers());
            model.addAttribute("totalQuantityPhysical", adminService.getTotalQuantityPhysicalExam());
            model.addAttribute("totalQuantityDepartment", adminService.getTotalQuantityDepartmentExam());
            model.addAttribute("totalQuantityExamination", adminService.getTotalQuantityExamination());
            return "admin/homepage";
        } else {
            model.addAttribute("totalQuantityPhysicalOfUser", userService.getTotalQuantityExaminationOfUser(username));
            model.addAttribute("physicalEntityOfUserLast", userService.getPhysicalLastOfUser(username));
            return "homeUser";
        }
    }

    @GetMapping("/403")
    String error() {
        return "error/403";
    }

}

