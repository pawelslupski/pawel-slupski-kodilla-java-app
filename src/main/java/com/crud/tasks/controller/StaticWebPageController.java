package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
public class StaticWebPageController {

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("variable", "My Thymeleaf variable");
        model.put("multiply", "*");
        model.put("adding", "+");
        model.put("subtraction", "-");
        model.put("equals", "=");
        model.put("space", " ");
        model.put("one", 1);
        model.put("two", 2);
        model.put("three", 3);
        return "index";
    }
}
