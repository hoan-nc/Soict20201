package application.web.controller;

import application.entity.UserEntity;
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

    @GetMapping
    String home(Model model) {
        return "home";
    }

    @GetMapping("/403")
    String error() {
        return "error/403";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @GetMapping("/home")
    String home() {
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SimpleGrantedAuthority simpleGrantedAuthority = authorities.get(0);
        if ("ROLE_ADMIN".equalsIgnoreCase(simpleGrantedAuthority.getAuthority())) {
            return "admin/homepage";
        }
        return "home";
    }
}

