package com.studymavernspringboot.mybatisapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/catweb")
public class CatWebController {

    @Autowired
    private CategoryServiceImpl categoryService;
    @GetMapping("/category_list")
    public String list(Model model ) {

    }
}
