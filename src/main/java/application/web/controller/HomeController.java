package application.web.controller;

import application.entity.UserEntity;
import application.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    private final ModelMapper modelMapper;
    private final FileService fileService;

    public HomeController(ModelMapper modelMapper, FileService fileService) {
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @GetMapping
    String home(Model model) {
        return "index";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("user", new UserEntity());
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

