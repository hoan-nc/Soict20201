package application.controller.web;

import application.data.entity.User;
import application.service.FileProcess;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileProcess fileProcess;

    @GetMapping
    String home(Model model) throws IOException {
        return "index";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        return "logout";
    }

    @GetMapping("/403")
    String error() {
        return "error/403";
    }

    @GetMapping("/home")
    String home() {
        return "admin/homepage";
    }
}

