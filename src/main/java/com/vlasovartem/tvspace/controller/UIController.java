package com.vlasovartem.tvspace.controller;

import com.vlasovartem.tvspace.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by artemvlasov on 28/11/15.
 */
@Controller
public class UIController {

    @RequestMapping("/")
    public String mainView () {
        return "index";
    }
}
