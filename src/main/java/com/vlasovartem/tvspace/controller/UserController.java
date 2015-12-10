package com.vlasovartem.tvspace.controller;


import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.service.UserService;
import com.vlasovartem.tvspace.utils.exception.UserRegistrationException;
import com.vlasovartem.tvspace.utils.validation.UserSpringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by artemvlasov on 08/12/15.
 */
@Controller
public class UserController {

    private Validator validator;
    private UserService userService;

    @Autowired
    public UserController(@Qualifier("userSpringValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/login", method = POST)
    public void login(@RequestParam String loginData,
                      @RequestParam String password,
                      @RequestParam(required = false) boolean rememberMe) {}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/series/watching", method = POST)
    public ResponseEntity markSeriesAsWatching(@RequestParam String id, @RequestParam("season") int season,
                                               @RequestParam("episode") int episode) {
        userService.markSeriesAsWatching(id, season, episode);
        return ResponseEntity.ok("Series successfully marked as watching");
    }

    @RequestMapping(value = "/series/notwatching", method = POST)
    public ResponseEntity markSeriesAsNotWatching (@RequestParam String id) {
        userService.markSeriesAsNotWatching(id);
        return ResponseEntity.ok("Series successfully marked as not watching");
    }

    @RequestMapping(value = "/logout")
    public void logout() {}

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup () {
        return new ModelAndView("signup", "user", new User());
    }

    @RequestMapping(value = "/signup", method = POST)
    public ModelAndView signup (@ModelAttribute("user")
                                    @Validated(UserSpringValidator.class) User user) {
        try {
            userService.signup(user);
        } catch (UserRegistrationException e) {
            Map<String, Object> modelMap = new HashMap<>(2);
            modelMap.put("error", e.getMessage());
            user.setPassword(null);
            modelMap.put("user", user);
            return new ModelAndView("signup", modelMap);
        }
        return new ModelAndView("redirect:/");
    }

}
