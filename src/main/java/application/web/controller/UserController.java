package application.web.controller;

import application.domain.UserChangePass;
import application.entity.UserEntity;
import application.service.UserService;
import application.service.impl.UserAuthenticatorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.TreeMap;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserAuthenticatorServiceImpl userAuthenticatorService;

    public UserController(UserService userService, UserAuthenticatorServiceImpl userAuthenticatorService) {
        this.userService = userService;
        this.userAuthenticatorService = userAuthenticatorService;
    }

    @GetMapping(path = "/register-user")
    public String register(Model model) {
        model.addAttribute("user", new UserEntity());
        return "/register";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute("user") UserEntity userEntity) {
        userService.registerNewUser(userEntity);
        return "redirect:/home";
    }

    @GetMapping("/user/general-profile")
    String getGeneralProfiles(Model model) {
        String username = userAuthenticatorService.getUsernameLogin();
        model.addAttribute("allMyPhysical", userService.getAllPhysicalExamByUser(username));
        return "physicalProfileMe";
    }

    @GetMapping("/user/detail")
    String getDetailUserInfo(Model model) {
        String username = userAuthenticatorService.getUsernameLogin();
        model.addAttribute("userDetail", userService.getByUsername(username));
        return "userDetail";
    }

    @PostMapping("/user/detail/change-pass")
    String changePasswordUser(UserChangePass userChangePass) {
        String username = userAuthenticatorService.getUsernameLogin();
        userChangePass.setUsername(username);
        userService.changePasswordUser(userChangePass);
        return "userDetail";
    }

    //START STATISTIC
    @GetMapping("/user/statistic-height-page")
    String getStaticHeight(Model model) {
        Map<String, Double> mapData = userService.getStatisticHeightOfUser(userAuthenticatorService.getUsernameLogin());
        model.addAttribute("mapStatisticHeight", mapData);
        return "/admin/statisticHeight";
    }

    @GetMapping("/user/statistic-height")
    public ResponseEntity<Map<String, Double>> getStatisticHeightPieChart() {
        Map<String, Double> mapDataHeight = userService.getStatisticHeightOfUser(userAuthenticatorService.getUsernameLogin());
        return new ResponseEntity<>(mapDataHeight, HttpStatus.OK);
    }

    @GetMapping("/user/statistic-weight-page")
    String getStaticWeight(Model model) {
        Map<String, Double> mapData = userService.getStatisticWeightOfUser(userAuthenticatorService.getUsernameLogin());
        model.addAttribute("mapStatisticWeight", mapData);
        return "admin/statisticWeight";
    }

    @GetMapping("/user/statistic-weight")
    public ResponseEntity<TreeMap<String, Double>> getStatisticWeightPieChart() {
        TreeMap<String, Double> mapData = userService.getStatisticWeightOfUser(userAuthenticatorService.getUsernameLogin());
        return new ResponseEntity<>(mapData, HttpStatus.OK);
    }

    //END STATISTIC

}
