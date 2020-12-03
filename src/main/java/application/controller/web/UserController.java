package application.controller.web;

import application.constant.StatusRegisterUserEnum;
import application.data.entity.User;
import application.data.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping(path = "/register-user")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute("user") User user,
                                  BindingResult result) {


        StatusRegisterUserEnum statusRegisterUserEnum = userService.registerNewUser(user);
        logger.info(statusRegisterUserEnum.toString());
        return "redirect:/";
    }
}
