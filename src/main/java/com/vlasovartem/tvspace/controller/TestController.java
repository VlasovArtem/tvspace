package com.vlasovartem.tvspace.controller;

import com.vlasovartem.tvspace.entity.Actor;
import com.vlasovartem.tvspace.persistence.repository.ActorRepository;
import com.vlasovartem.tvspace.utils.ActorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by artemvlasov on 20/11/15.
 */

@Controller
public class TestController {
    private ActorRepository actorRepository;
    private ActorValidator actorValidator;

    @Autowired
    public TestController(ActorRepository actorRepository, ActorValidator actorValidator) {
        this.actorRepository = actorRepository;
        this.actorValidator = actorValidator;
    }

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

    @RequestMapping(path = "/form", method = GET)
    public ModelAndView formTest() {
        return new ModelAndView("form", "actor", new Actor());
    }

    @RequestMapping(path = "/addActor", method = POST)
    public String addActor(@Validated Actor actor) {
        Actor savedActor = actorRepository.save(actor);
        return "redirect:/result/" + savedActor.getId();
    }

    @RequestMapping(path = "/result/{id}")
    public String result(@PathVariable("id") String actorId, ModelMap map) {
        map.put("actor", actorRepository.findOne(actorId));
        return "result";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(true);
        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(format, false));
        binder.setValidator(actorValidator);
    }
}
