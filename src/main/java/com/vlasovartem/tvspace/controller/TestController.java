package com.vlasovartem.tvspace.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 20/11/15.
 */

@Controller
public class TestController {

    @RequestMapping(path = "/", method = GET)
    public ModelAndView printWelcome() {
        String now = LocalDate.now().toString();
        return new ModelAndView("index", "now", now);
    }

    @RequestMapping(path = "/test", method = GET)
    public String test(ModelMap map) {
        map.put("message", "Hello from Controller");
        return "index";
    }

    @RequestMapping(path = "/select", method = GET)
    public String selectTest(ModelMap map) {
        List<String> things = Arrays.asList("thing1", "thing2", "thing3");
        map.put("things", things);
        return "spring-test";
    }
}
