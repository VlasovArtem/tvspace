package com.vlasovartem.tvspace.controller;

import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 23/11/15.
 */
@Controller
@RequestMapping(path = "/series")
public class SeriesController {
    @Autowired
    private SeriesRepository repository;

    @RequestMapping
    public ModelAndView getAll() {
        return new ModelAndView("/series/series", "series", repository.findAll());
    }
}
