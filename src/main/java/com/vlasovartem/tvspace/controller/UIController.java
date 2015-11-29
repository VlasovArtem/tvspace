package com.vlasovartem.tvspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
