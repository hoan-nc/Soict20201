package application.web.controller;

import application.entity.UserEntity;
import application.service.UserService;
import application.utils.StatusRegisterUserEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/register-user")
    public String register(Model model) {
        model.addAttribute("user", new UserEntity());
        return "/register";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute("user") UserEntity userEntity, BindingResult result) {
        StatusRegisterUserEnum statusRegisterUserEnum = userService.registerNewUser(userEntity);
        logger.info(statusRegisterUserEnum.toString());
        return "redirect:/";
    }

    @GetMapping("/user/general-profile")
    String getGeneralProfiles(Model model) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("allMyPhysical", userService.getAllPhysicalExamByUser(username));
        return "myProfile";
    }

}
